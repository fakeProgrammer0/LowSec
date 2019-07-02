package top.dadagum.lowsec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.dadagum.lowsec.dao.UserMapper;
import top.dadagum.lowsec.domain.CustomUser;
import top.dadagum.lowsec.domain.Roles;

@Component
public class X509UserDetails implements UserDetailsService
{
    @Autowired
    private UserMapper userMapper;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        /*
        cert info
        CN = client2
        OU = LowSec Client
        O = LowSec
        L = GZ
        S = GD
        C = CN
        */
        logger.info("x509 username: " + username);
        top.dadagum.lowsec.domain.User dbUser = userMapper.getUserByName(username);
        if(dbUser == null)
            throw new UsernameNotFoundException("User not found!");
        
        User user = new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList(dbUser.getRolecode()));
        return new CustomUser(user, dbUser.getId());
    }
}
