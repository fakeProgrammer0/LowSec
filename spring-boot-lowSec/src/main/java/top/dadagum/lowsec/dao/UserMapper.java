package top.dadagum.lowsec.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.dadagum.lowsec.domain.User;

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
    List<User> listUsers();


    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    User getUser(String id);
    
    /**
     * 根据用户名获取用户信息
     * @param name
     * @return
     */
    User getUserByName(@Param("name") String name);
}
