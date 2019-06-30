package top.dadagum.lowsec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.dadagum.lowsec.dao.UserMapper;

@Component
public class X509UserDetails implements UserDetailsService
{
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if(username.matches("client\\d"))
            return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
        throw new UsernameNotFoundException("User not found!");
    }
}
