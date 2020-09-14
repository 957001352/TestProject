package com.dhlk.basicmodule.service.dhlk_basic_module_service;

import com.dhlk.basicmodule.service.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class RoleServiceImplTest {
    @Autowired
    private RoleService roleService;
    @Test
    public void save() {
        //新增
       Role role=new Role();
        role.setName("test3");
        role.setNote("test3");
        Result result = roleService.save(role);
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }
    @Test
    public void update() {
        //修改
        Role role=new Role();
        role.setId(1);
        role.setName("超级管理员");
        role.setNote("拥有最大权限");
        Result result = roleService.save(role);
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }
    @Test
    public void delete() {
        //删除
        Result result = roleService.delete("6,8,10");
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }

    @Test
    public void findList() {
        //分页查询
        Result result = roleService.findPageList(1,2);
        PageInfo<Role> pageInfo=(PageInfo<Role>)result.getData();
        List<Role> list = pageInfo.getList();
        list.forEach(e-> System.out.println(e.toString()));
        Assert.assertTrue(list.size()>0);
    }

    @Test
    public void selectRoleByName() throws Exception {
        Result result = roleService.selectRoleByName("管理员");
        System.out.println(result.getCode());
        Assert.assertTrue(result.getCode()==200);
    }

    @Test
    public void findAllList() {
        Result result = roleService.findAllList();
        List<Role> list=(List<Role>)result.getData();
        list.forEach(e-> System.out.println(e.toString()));
        Assert.assertTrue(list.size()>0);
    }
    /**
     *  test
     */
    @Test
    public void test() {
        /*String str="3,6,8,1";
        String[] string = str.split(",");
        Stream<String> stream = Arrays.stream(string);
        List<Integer> collect = stream.map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(collect);*/
      /*  String str="3,6,8,1";
        final List<String> strings = Arrays.asList(str);
        System.out.println(strings);*/
    }
}