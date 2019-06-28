package top.dadagum.lowsec.dao;

import top.dadagum.security.model.User;

import java.util.List;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 11:28
 **/
public interface UserMapper {

    /**
     * 获取所有系统用户
     *
     * @return
     */
    public List<User> listUsers();


    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    public User getUser(String id);
}
