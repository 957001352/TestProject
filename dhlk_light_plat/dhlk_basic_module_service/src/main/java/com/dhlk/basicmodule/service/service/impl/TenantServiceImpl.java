package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.dao.*;
import com.dhlk.basicmodule.service.service.TenantService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Org;
import com.dhlk.entity.basicmodule.Role;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.tb.*;
import com.dhlk.enums.ResultEnum;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private ProductDevicesDao productDevicesDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Value("${attachment.path}")
    private String attachmentPath;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;

    @Value("${tb.isAdd}")
    private boolean isAdd;



    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Override
    public Result save(Tenant tenant) {
        Integer flag = 0;

        //验证租户名称是否已经存在
        if(tenantDao.findTenantRepeat(tenant.getName(),tenant.getId())>0){
            return ResultUtils.error(ResultEnum.TENANT_NAME);
        }
        if (CheckUtils.isNull(tenant.getId())) {
            tenant.setCode(this.createCode());
            tenant.setTbLoginname(tenant.getCode() + "@dhlk-tech.com");
            tenant.setTbPassword(tenant.getCode());
            String detailAddress = tenant.getDetailAddress();
            //前台传过来的详细地址和地址拼接起来
            tenant.setAdress(tenant.getAdress() + "@" + detailAddress);
            //给tb插入租户信息
            String tbId=null;
            if(isAdd){
                 tbId = this.insertTenant(tenant.getCode(), tenant.getName(), tenant.getTbLoginname(), tenant.getTbPassword());
            }
            try {
                if (!CheckUtils.isNull(tbId)) {
                    tenant.setTbId(tbId);
                }
                //如果租户信息添加成功，则给Org添加一条数据
                flag = tenantDao.insert(tenant);
                if (flag > 0) {
                    Org org = new Org();
                    org.setName(tenant.getName());
                    org.setParentId(0);
                    org.setTenantId(tenant.getId());
                    flag = orgDao.insert(org);
                }
            } catch (Exception e) {
                //操作失败，数据回滚，同时删除tb租户
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                this.deleteTenant(tbId);
                e.printStackTrace();
            }
        } else {
            try {
                //修改租户信息
                //根据租户ID先查询租户信息是否存在，如果为空则返回错误
                Tenant tenantInfo = tenantDao.selectTenantById(tenant.getId());
                if(tenantInfo == null){
                    return ResultUtils.failure();
                }
                String detailAddress = tenant.getDetailAddress();
                tenant.setAdress(tenant.getAdress() + "@" + detailAddress);
                flag = tenantDao.update(tenant);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    /**
     * 新增tb租户,并创建tb租户管理员
     *
     * @param code
     * @param name
     * @param loginName
     * @param password
     * @return
     */
    private String insertTenant(String code, String name, String loginName, String password) {
        String tbId = null;
        try {
            AdditionalInfo additionalInfo = new AdditionalInfo(true, name);
            //保存租户信息
            TbTenant tbTenant = new TbTenant(code, additionalInfo);
            HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVETENANT, restTemplateUtil.getAddTenantHeaders(), JSON.toJSONString(tbTenant));
            if (responseEntity.getCode() == 200) {//保存设备数据到tb成功
                Map map = HttpClientUtils.resultToMap(responseEntity);
                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                if (!CheckUtils.isNull(mapId.get("id"))) {
                    tbId = mapId.get("id").toString();
                    //激活tb管理员，并设置密码
                    if (this.insertTbUser(tbId, loginName, password) == 200) {
                        return tbId;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //更新tb租户
    public Integer updateTenant(String tbId, String code, String name) throws Exception {
        Id id = new Id(tbId, "TENANT");
        AdditionalInfo additionalInfo = new AdditionalInfo(true, name);
        TbTenant tbTenant = new TbTenant(id, code, additionalInfo);
        HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVETENANT, restTemplateUtil.getAddTenantHeaders(), JSON.toJSONString(tbTenant));
        return responseEntity.getCode();
    }

    //删除tb租户
    public Integer deleteTenant(String tbId) {
        try {
            HttpClientResult responseEntity = HttpClientUtils.doDeleteHeaders(tbBaseUrl + Const.TBSAVETENANT + "/" + tbId, restTemplateUtil.getAddTenantHeaders());
            return responseEntity.getCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //新增租户管理员
    private Integer insertTbUser(String tenantId, String loginName, String password) {
        try {
            Id tenant = new Id(tenantId, "TENANT");
            //保存设备信息
            TbTenantUser tbTenant = new TbTenantUser(tenant, loginName, "TENANT_ADMIN");
            HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVUSER + "/?sendActivationMail=false", restTemplateUtil.getAddTenantHeaders(), JSON.toJSONString(tbTenant));
            if (responseEntity.getCode() == 200) {//保存设备数据到tb成功
                //保存设备数据到dhlk数据库
                Map map = HttpClientUtils.resultToMap(responseEntity);
                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                //激活租户管理员并设置密码
                if (!CheckUtils.isNull(mapId.get("id"))) {
                    return this.activateTenant(mapId.get("id").toString(), password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //激活租户管理员并设置密码
    private Integer activateTenant(String tbUserId, String password) {
        try {
            //得到激活连接和激活token
            String activateUrl = tbBaseUrl + Const.TBSAVUSER + "/" + tbUserId + "/activationLink";
            HttpClientResult httpClientResult = HttpClientUtils.doGet(activateUrl, restTemplateUtil.getAddTenantHeaders(), null);
            if (httpClientResult.getCode() == 200) {//保存设备数据到tb成功
                String content = httpClientResult.getContent();
                //得到激活token
                String activateToken = content.substring(content.indexOf("=") + 1);
                TbPassword pwd = new TbPassword(password, activateToken);
                //设置租户管理员密码
                HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + "/api/noauth/activate", restTemplateUtil.getHeaders(true), JSON.toJSONString(pwd));
                return responseEntity.getCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    private void updateTbUser(String tbId,String tenantUserId,String loginName,String firstName,String lastName)  {
//        try{
//            Id tenantId = new Id(tenantUserId, "TENANT");
//            Id id = new Id(tbId, "USER");
//            //保存设备信息
//            TbTenantUser tbTenant = new TbTenantUser(id,tenantId,loginName,firstName,lastName,"TENANT_ADMIN");
//            HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVUSER, restTemplateUtil.getHeaders(true),JSON.toJSONString(tbTenant));
//            if (responseEntity.getCode() == 200) {//保存设备数据到tb成功
//                //保存设备数据到dhlk数据库
//                Map map = HttpClientUtils.resultToMap(responseEntity);
//                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
//                System.out.println("tbId------------->" + mapId.get("id").toString());
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public Result findList(String name, String startTime, String endTime, String expire, Integer pageNum, Integer pageSize) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Tenant> tenants = tenantDao.findList(name, startTime, endTime, expire, attachmentPath);
        if (tenants != null && tenants.size() > 0) {
            for (Tenant tenant : tenants) {
                String address = tenant.getAdress();
                if (address != null && address.contains("@")) {
                    String[] str = address.split("@");
                    if(str.length == 2) {
                        tenant.setAdress(str[0]);
                        tenant.setDetailAddress(str[1]);
                    }

                }
            }
        }
        PageInfo<Tenant> tenantInfo = new PageInfo<>(tenants);
        return ResultUtils.success(tenantInfo);
    }

    @Override
    public Result delete(Integer id) {
        if (!CheckUtils.checkId(id)) {
            return ResultUtils.error("id输入错误");
        }

        Tenant tenantInfo = tenantDao.selectTenantById(id);
        try {
            if (tenantDao.updateTenantStaus(id) > 0) {
                //删除,根据租户Id获取租户管理员,如果存在就修改租户管理员的状态为1
                List<User> lists = userDao.findListByTenantId(String.valueOf(id));
                if(!CollectionUtils.isEmpty(lists)){
                    userDao.updataStatusByTenantId(id);
                }
                //删除,修改组织架构状态为2
                List<Org> orgs = orgDao.findByTenantId(id);
                if(!CollectionUtils.isEmpty(orgs)){
                    orgDao.updataStatusByTenantId(id);
                }
                //根据租户Id物理删除角色表数据,暂时不删
               /* List<Role> roles = roleDao.findListByTenantId(id);
                if(!CollectionUtils.isEmpty(roles)){
                    roleDao.deleteByTenantId(id);
                }*/
                //删除tb租户信息
                if (!CheckUtils.isNull(tenantInfo.getTbId())) {
                    this.deleteTenant(tenantInfo.getTbId());
                }
                return ResultUtils.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result exportExcel(String expire, String startTime, String endTime, String name) {
        List<LinkedHashMap<String, Object>> list = tenantDao.exportExcel(expire, startTime, endTime, name);
        return ResultUtils.success(list);
    }

    @Override
    public Result findTenantAdminList(Integer tenantId, Integer pageNum, Integer pageSize) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.findTenantAdminList(tenantId);
        PageInfo<User> userInfo = new PageInfo<>(users);
        return ResultUtils.success(userInfo);
    }

    @Override
    public Result findTenantRepeat(String name,Integer id) {
        if (tenantDao.findTenantRepeat(name,id)>0) {
            return ResultUtils.error(ResultEnum.TENANT_NAME);
        }
        return ResultUtils.success();
    }

    @Override
    public Result findTenantByCode(String code) {
        Tenant tenant = tenantDao.findTenantByCode(code);
        return ResultUtils.success(tenant);
    }


    private String createCode() {
        String code = "dhlk_tenant_" + (int) ((Math.random() * 9 + 1) * 100000);
        if (productDevicesDao.isRepeatCode(code) > 0) {
            this.createCode();
        }
        return code;
    }
}
