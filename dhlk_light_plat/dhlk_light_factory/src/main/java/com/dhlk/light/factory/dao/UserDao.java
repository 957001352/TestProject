package com.dhlk.light.factory.dao;

import com.dhlk.entity.basicmodule.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/15
 * <p>
 * 用户Dao
 */
@Repository
public interface UserDao {

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    Integer insert(User user);

    /**
     * 删除用户,物理删除
     *
     * @return
     */
    Integer delete();

    /**
     * 批量插入
     *
     * @param user
     * @return
     */
    Integer insertBatch(List<User> user);

    /**
     * 根据用户姓名查询用户
     *
     * @param userName
     * @return
     */
    User findUserByLoginName(String userName);

    /**
     * 检查用户表里是否有信息
     */
    Integer checkUserExist();

    /**
     * 登录校验
     */
    User loginCheck(@Param("loginName")String loginName,@Param("password")String password);

    /**
     * 查询所有user
     * @return
     */
    List<User> findAll();


    int update(User user);

    /**
     * 批量删除
     * @return
     */
    int deleteByIds(@Param("ids") String[] ids);
}
