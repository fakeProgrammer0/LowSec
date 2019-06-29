package top.dadagum.lowsec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;
import top.dadagum.lowsec.dao.UserMapper;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 23:04
 **/
@Component
public class DbUserDetails implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        top.dadagum.lowsec.domain.User userInfo = userMapper.getUserByName(username);
    
        //noinspection deprecation
        return User.builder().passwordEncoder(NoOpPasswordEncoder.getInstance()::encode)
                .username(username)
                .password(userInfo.getPassword())
                .roles(userInfo.getRolecode())
                .credentialsExpired(!userInfo.isCredentialsNonExpired())
                .accountLocked(!userInfo.isAccountNonLocked())
                .disabled(!userInfo.isEnabled())
                .accountExpired(!userInfo.isAccountNonExpired())
                .build();
    }
}