package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.*;
import com.dhlk.basicmodule.service.service.ProductDevicesService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Org;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.entity.basicmodule.ProductDevicesTree;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.tb.AdditionalInfo;
import com.dhlk.entity.tb.Id;
import com.dhlk.entity.tb.TbProductDevices;
import com.dhlk.entity.tb.credentials.DeviceCredentials;
import com.dhlk.entity.tb.credentials.DeviceId;
import com.dhlk.enums.ResultEnum;
import com.dhlk.systemconst.Const;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.HttpClientResult;
import com.dhlk.utils.HttpClientUtils;
import com.dhlk.utils.ResultUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description 生产设备管理
 * @Author lpsong
 * @Date 2020/3/12
 */
@Service
public class ProductDevicesSerivceImpl implements ProductDevicesService {
    @Autowired
    private ProductDevicesDao productDevicesDao;

    @Autowired
    private DevicesClassifyDao devicesClassifyDao;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private AuthUserUtil authUserUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private ProductNetDao productNetDao;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;
    @Value("${attachment.path}")
    private String attachmentPath;

    @Override
    public Result save(ProductDevices productDevices) throws Exception {
        if(CheckUtils.isNull(productDevices.getName())){
            return  ResultUtils.error("设备名称不能为空");
        }
        productDevices.setName(productDevices.getName().trim());
        //设置租户ID
        productDevices.setTenantId(authUserUtil.tenantId());

        if(productDevicesDao.isRepeatName(productDevices)>0){
            return  ResultUtils.error("设备名称重复");
        }
        //默认不是网关设备
        //jsonDescription设备描述信息
        JSONObject jsonDescription = new JSONObject();
        jsonDescription.put("pdOrgId", productDevices.getOrgId());
        /*
         * productDevices id为空 保存 id不为空 更新
         */
        //返回结果
        Result result =null;
        if (CheckUtils.isNull(productDevices.getId())) {
            //判断是否超出租户允许添加最大设备数
            if(!CheckUtils.isNull(productDevices.getTenantId())){
                Integer proDevices=productDevicesDao.findDevicesCountByTenant(productDevices.getTenantId());
                Integer maxDeivces=productDevicesDao.findTenantById(productDevices.getTenantId());
                if(proDevices>=maxDeivces){
                    return  ResultUtils.error("已经超出允许最大设备添加数");
                }
            }
            //保存生产设备
            result = saveProductDevices(productDevices, jsonDescription);
        } else {
            //更新生产设备
            result = updateProductDevices(productDevices, jsonDescription);
        }
        return   result;
    }

    @Override
    public Result delete(String ids) throws Exception {
        if (CheckUtils.isNull(ids)) {
            return ResultUtils.error(ResultEnum.PARAM_ISNULL);
        }

        if (productNetDao.findProductIsBand(Arrays.asList(ids.split(",")))>0) {
            return ResultUtils.error("该设备已被绑定，无法删除");
        }

        Integer flag=0;
        //根据id查出对应的tb_id
        List<ProductDevices> productDevicesList = productDevicesDao.findTbIdsListbyIds(Arrays.asList(ids.split(",")));
        //删除tb中的数据
        for (ProductDevices pd : productDevicesList) {
            HttpClientResult httpClientResult = HttpClientUtils.doDeleteHeaders(tbBaseUrl + Const.TBDELETEDEVICEBYID + "/" + pd.getTbId(), restTemplateUtil.getHeaders(true));
           if (httpClientResult.getCode() == 200) {
                //删除dhlk db中的数据
                Integer res = productDevicesDao.deleteById(pd.getId());
                if (res > 0) {
                    flag=1;
                    continue;
                } else {
                    //还原tb中的数据
                    flag=-1;
                    break;
                }
            } else {
                return ResultUtils.failure();
            }
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.success(productDevicesDao.findList(name,authUserUtil.tenantId(),attachmentPath));
    }

    @Override
    public Result findTbDeviceByDhlkId(Integer id) throws Exception {
        ProductDevices productDevices = productDevicesDao.findProductDevicesById(id);
        String api = tbBaseUrl+Const.SELECTTBDEVICEBYID + "/" + productDevices.getTbId();
        HttpClientResult mapResponseEntity = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), null);
        return ResultUtils.success(mapResponseEntity.getContent());
    }

    @Override
    public Result deleteById(Integer id) {
        if (!CheckUtils.isNull(id)) {
            return ResultUtils.error(ResultEnum.PARAM_ISNULL);
        }
        Integer res = productDevicesDao.deleteById(id);
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findAttrByClassifyById(String classifyId){
        //获取dhlk设备属性
        List<LinkedHashMap<String, String>> list = devicesClassifyDao.findAttrByClassifyById(classifyId);
        return ResultUtils.success(list);
    }

    //把dhlk设备属性保存到对应的tb设备的共享属性中
    private void saveAttrToTb(ProductDevices productDevices) throws Exception {
        List<String> list=new ArrayList<String>();
        List<LinkedHashMap<String, String>> listMap = devicesClassifyDao.findAttrByClassifyById(productDevices.getClassifyId());
        for(LinkedHashMap<String, String> map:listMap) {
            list.add(map.get("code"));
        }
        JSONObject jsonSharedArrribute = new JSONObject();
        jsonSharedArrribute.put("attributeList", JSON.toJSONString(list));
        String url=tbBaseUrl+Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + productDevices.getTbId() + "/SHARED_SCOPE";
        HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(url, restTemplateUtil.getHeaders(true),jsonSharedArrribute.toJSONString());
    }
    private Result saveOrUpdateDeviceCredentialsByTbDeviceId(String tbDeviceId,String credentialId) throws Exception {
        //根据tb设备id获取tb设备凭据id
        HttpClientResult httpClientResult = HttpClientUtils.doGet(tbBaseUrl + "/api/device/" + tbDeviceId + "/credentials", restTemplateUtil.getHeaders(true), null);
        //保存设备数据到dhlk数据库
        Map map = HttpClientUtils.resultToMap(httpClientResult);
        Map<String, Object> mapId = (Map<String, Object>) map.get("id");
        String credentialsId = mapId.get("id").toString();
        com.dhlk.entity.tb.credentials.Id id=new com.dhlk.entity.tb.credentials.Id(credentialsId);
        DeviceId deviceId=new DeviceId(tbDeviceId,"DEVICE");
        DeviceCredentials deviceCredentials=new DeviceCredentials(id,deviceId,"ACCESS_TOKEN",credentialId);
        System.out.println(JSON.toJSONString(deviceCredentials));

        HttpClientResult responseHttpClientResult = HttpClientUtils.doPostStringParams(tbBaseUrl + "/api/device/credentials", restTemplateUtil.getHeaders(true), JSON.toJSONString(deviceCredentials));
        return ResultUtils.success(responseHttpClientResult.getContent());
    }



    @Override
    public Result findTreeList(){
        List<Org> orgs=orgDao.treeList(0,authUserUtil.tenantId());
        List<ProductDevicesTree> treeList = new ArrayList<>();
        //遍历机构数，查询机构下人数和绑定的生产设备
        for (Org org:orgs) {
            //查询该机构下人数
            List<User> userByOrgIds = userDao.findUserByOrgId(org.getId());
            //查询该机构及下级机构是否有生产设备
            Integer c = productDevicesDao.findProductDevicesCountByOrgId(org.getId());
            org.setStaffNum(userByOrgIds.size());
            ProductDevicesTree tree = new ProductDevicesTree();
            tree.setId(org.getId().toString());
            tree.setParentId(org.getParentId().toString());
            tree.setTitle(org.getName());
            tree.setStaffNum(org.getStaffNum());
            //当c>0说明该机构或其下级机构绑定了生产设备
            if(c>0){
                tree.setComponent("isShow");
            }
            treeList.add(tree);
            treeList=this.productDevicesToTree(treeList,tree.getId());
        }
        List<ProductDevicesTree> treeLi = new ArrayList<ProductDevicesTree>();
        for (ProductDevicesTree tree : treeList) {
            if (tree.getParentId().equals("0")) {
                tree.initChildren();
                treeBuilder(tree, treeList);
                if(!CheckUtils.isNull(tree.getComponent())&&tree.getComponent().equals("isShow")) {
                    treeLi.add(tree);
                }
            }
        }
        return ResultUtils.success(treeLi);
    }
    /**
    *  查询该机构下生产设备
     * @param trees
     * @param orgId
    * @return
    */
    private List<ProductDevicesTree> productDevicesToTree(List<ProductDevicesTree> trees,String orgId) {
        List<ProductDevices> productDevices = productDevicesDao.findProductDevicesByOrgId(orgId,attachmentPath);
        if(productDevices!=null&&productDevices.size()>0){
            for (ProductDevices pd : productDevices) {
                ProductDevicesTree tree = new ProductDevicesTree();
                tree.setComponent("isShow");
                tree.setId(pd.getTbId());
                tree.setTbId(pd.getTbId());
                tree.setParentId(orgId);
                tree.setTitle(pd.getName());
                tree.setName(pd.getName());
                tree.setClassifyId(pd.getClassifyId());
                tree.setClassifyName(pd.getClassifyName());
                tree.setClassifySet(pd.getClassifySet());
                tree.setNetDevicesList(pd.getNetDevicesList());
                tree.setTenantCode(pd.getTenantCode());
                tree.setCode(pd.getCode());
                tree.setNote(pd.getNote());
                trees.add(tree);
            }
        }
        return trees;
    }
    //递归组装树机构
    private void treeBuilder(ProductDevicesTree tree, List<ProductDevicesTree> trees) {
        for (ProductDevicesTree child : trees) {
            if (child.getChildren() == null){
                child.initChildren();
            }
            if (tree.getId().equals(child.getParentId())) {
                tree.setHasChildren(true);
                child.setHasParent(true);
                if(!CheckUtils.isNull(child.getComponent())&&child.getComponent().equals("isShow")){
                    treeBuilder(child, trees);
                    tree.getChildren().add(child);
                }
            }
        }
    }

    private String createCode() {
        String code="dhlk_tb_product_" + (int) ((Math.random() * 9 + 1) * 100000);
        if(productDevicesDao.isRepeatCode(code)>0){
            this.createCode();
        }
        return code;
    }
    //保存生产设备
    public Result saveProductDevices(ProductDevices productDevices,JSONObject jsonDescription )throws Exception {
        //设备additionalInfo属性
        AdditionalInfo additionalInfo =new AdditionalInfo(false, jsonDescription.toJSONString());
        productDevices.setCode(this.createCode());
        //保存设备信息
        TbProductDevices tbProductDevices = new TbProductDevices(productDevices.getCode(), productDevices.getClassifyId().toString(), productDevices.getName(), additionalInfo);
        HttpClientResult httpClientResult = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
        if (httpClientResult.getCode() == 200) {//保存设备数据到tb成功
            //保存设备数据到dhlk数据库
            Map map = HttpClientUtils.resultToMap(httpClientResult);
            Map<String, Object> mapId = (Map<String, Object>) map.get("id");
            String tbId = mapId.get("id").toString();
            productDevices.setTbId(tbId);

            //把dhlk设备属性保存到对应的tb设备的共享属性中
            saveAttrToTb(productDevices);

            try {
                //生成tb设备凭证
                String credentialsId= RandomStringUtils.randomAlphanumeric(20);
                //保存tb设备凭证
                saveOrUpdateDeviceCredentialsByTbDeviceId(tbId,credentialsId);
                //设置tb设备凭证
                productDevices.setCredentials(credentialsId);
                //新增
                Integer flag = productDevicesDao.insert(productDevices);
                //成功
                return ResultUtils.success();
            } catch (RuntimeException e) {
                e.printStackTrace();
                //失败 删除保存到tb中的数据
                HttpClientUtils.doDeleteHeaders(tbBaseUrl+Const.TBDELETEDEVICEBYID + "/" + productDevices.getTbId(),restTemplateUtil.getHeaders(true));
                return ResultUtils.failure();
            }
        } else {
            //保存设备数据到tb失败  返回保存失败信息
            return ResultUtils.failure();
        }
    }

    //更新生产设备
    public Result updateProductDevices(ProductDevices productDevices,JSONObject jsonDescription ) throws Exception {
        //根据设备id查出tbId
        List<ProductDevices> tbIds = productDevicesDao.findTbIdsListbyIds(Arrays.asList(productDevices.getId().toString()));
        if (!tbIds.isEmpty() && tbIds.size()>0) {
            //设备描述信息
            jsonDescription.put("pdId", productDevices.getId());
            //设备additionalInfo属性
            AdditionalInfo additionalInfo = new AdditionalInfo(false, jsonDescription.toJSONString());
            //构造tb设备更新格式
            Id id = new Id(tbIds.get(0).getTbId(), "DEVICE");
            //在更新tb之前备份数据
            ProductDevices pdBack = productDevicesDao.findProductDevicesById(productDevices.getId());
            String api = tbBaseUrl+Const.SELECTTBDEVICEBYID + "/" + pdBack.getTbId();
            HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), null);

            TbProductDevices tbProductDevices = new TbProductDevices(id, pdBack.getCode(), productDevices.getClassifyId().toString(),productDevices.getName(), additionalInfo);

            HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
            if (responseEntity.getCode()== 200) {//保存设备数据到tb成功
                //保存设备数据到dhlk数据库
                Map map = HttpClientUtils.resultToMap(responseEntity);
                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                String tbId = mapId.get("id").toString();
                productDevices.setTbId(tbId);

                //把dhlk设备属性保存到对应的tb设备的共享属性中
                //if(CheckUtils.isNull(productDevices.getClassifyId())){
                    saveAttrToTb(productDevices);
                //}

                try {
                    //更新
                    Integer flag = productDevicesDao.update(productDevices);
                    //更新tb设备凭证
                    if(CheckUtils.isNull(productDevices.getCredentials())){
                        saveOrUpdateDeviceCredentialsByTbDeviceId(tbId,productDevices.getCredentials());
                    }
                    //成功
                    return ResultUtils.success();
                } catch (RuntimeException e) {
                    //更新生产设备失败 还原tb中的数据
                    handleFailure(httpClientResult);
                    return ResultUtils.failure();
                }
            }
        }else{
            Integer flag = productDevicesDao.update(productDevices);
            return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
        }
        return ResultUtils.failure();
    }

    //更新生产设备失败 还原tb中的数据
    private  void handleFailure(HttpClientResult httpClientResult) throws Exception {
        Map device = HttpClientUtils.resultToMap(httpClientResult);
        String name=device.get("name").toString();
        String label=device.get("label").toString();
        String type=device.get("type").toString();
        String additionalInfoBackUpdate=device.get("additionalInfo").toString();
        Map<String,Object> mapIdBack=(Map<String,Object>)device.get("id");
        String idBack=mapIdBack.get("id").toString();
        Id idBackUpdate=new Id(idBack,"DEVICE");
        //设备additionalInfo属性
        AdditionalInfo additionalInfoBack = new AdditionalInfo(false, additionalInfoBackUpdate);
        TbProductDevices tbProductDevicesBack = new TbProductDevices(idBackUpdate,name, type,label, additionalInfoBack);
        while(true){
            //失败 还原tb中的数据
            HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevicesBack));
            if(responseEntityBack.getCode()==200){
                break;
            }
        }
    }
}