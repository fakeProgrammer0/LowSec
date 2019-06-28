package top.dadagum.lowsec.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.dadagum.lowsec.AppRunnerTest;
import top.dadagum.security.model.User;

import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest extends AppRunnerTest
{
    @Autowired
    private UserMapper userMapper;
    
    @Test
    public void testListUsers()
    {
        List<User> users = userMapper.listUsers();
        for(User user: users)
            System.out.println(user.getShowname());
    }
}