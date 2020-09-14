package com.dhlk.basicmodule.service.dhlk_basic_module_service;

import com.dhlk.basicmodule.service.service.MenuService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Menu;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class MenuServiceImplTest {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;
    @Test
    public void save() {
        //新增
        Menu menu=new Menu();
        menu.setName("testwww");
        menu.setCode("testwww");
        menu.setParentId(0);
        menu.setStatus(0);
        menu.setUrl("/dhlk/testwww");
        Result result = menuService.save(menu);
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }
    @Test
    public void update() {
        //新增
        Menu menu=new Menu();
        menu.setName("test12");
        menu.setCode("test2233");
        menu.setParentId(1);
        menu.setStatus(0);
        menu.setUrl("/dhlk/test2233");
        Result result = menuService.save(menu);
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }
 @Test
    public void delete() {
        //删除
        Result result = menuService.delete("9,11,15");
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }

    @Test
    public void findList() {
        //分页查询
        Result result = menuService.findPageList(1,2,2);
        PageInfo<Menu> pageInfo=(PageInfo<Menu>)result.getData();
        List<Menu> list = pageInfo.getList();
        list.forEach(e-> System.out.println(e.toString()));
        Assert.assertTrue(list.size()>0);
    }

    @Test
    public void isEnable() {
        Result result = menuService.isEnable(1, 2);
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }

    @Test
    public void findTreeList() {
        Result result = menuService.findTreeList();
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }

    @Test
    public void findTreeByUserId() {
        Result result = menuService.findTreeByUserId(14);
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }
    @Test
    public void selectMenuById() throws Exception {
        Menu menu = menuService.selectMenuById(14);
        System.out.println(menu.toString());
    }
    @Test
    public void testAll() {

        String url="http://192.168.2.59:9050/api/tenant/devices?limit=10";
        String token="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5d3hmZGo5OTk5QGxlYW5zaXRlLmNuIiwic2NvcGVzIjpbIlRFTkFOVF9BRE1JTiJdLCJ1c2VySWQiOiJhMTY1NWU2MC0yMGExLTExZWEtOGY2Ni0yNTQzMjcxOTY2OWQiLCJlbmFibGVkIjp0cnVlLCJpc1B1YmxpYyI6ZmFsc2UsInRlbmFudElkIjoiYTE0OGQ1YjAtMjBhMS0xMWVhLThmNjYtMjU0MzI3MTk2NjlkIiwiY3VzdG9tZXJJZCI6IjEzODE0MDAwLTFkZDItMTFiMi04MDgwLTgwODA4MDgwODA4MCIsImlzcyI6InRoaW5nc2JvYXJkLmlvIiwiaWF0IjoxNTg0NjkwOTYzLCJleHAiOjE1ODQ2OTk5NjN9._5836k0TaGgzRO6BzoNj9_8soXT2vHIBItMNDqXzyCWhtTskfmzl3hnZdckawQ_dUi0BQat12IexQ6o6kZe2rw";

        ResponseEntity<String> responseEntity = restTemplateUtil.restTemplateExchange(url, HttpMethod.GET, null, String.class,true);
        System.out.println(responseEntity);

    }
    @Test
    public void test111(){
        Result menuCheckedList = menuService.getMenuCheckedListByRoleId("1");
        System.out.println(menuCheckedList.getData().toString());
    }


    }