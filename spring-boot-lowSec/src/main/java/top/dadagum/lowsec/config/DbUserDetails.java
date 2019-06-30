package top.dadagum.lowsec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;
import top.dadagum.lowsec.dao.UserMapper;
import top.dadagum.lowsec.domain.CustomUser;

import java.util.ArrayList;
import java.util.Collection;

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
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userInfo.getRolecode()));

        return new CustomUser(
                username,
                userInfo.getPassword(),
                userInfo.isEnabled(),
                userInfo.isAccountNonExpired(),
                userInfo.isCredentialsNonExpired(),
                userInfo.isAccountNonLocked(),
                authorities,
                userInfo.getId()
        );
    }
}