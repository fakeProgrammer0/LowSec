package top.dadagum.lowsec.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.dadagum.security.model.User;

import java.util.List;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 11:28
 **/
@Mapper
@Component
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

    /**
     * 根据用户名获取用户信息
     * @param name
     * @return
     */
    User getUserByName(@Param("name") String name);
}
