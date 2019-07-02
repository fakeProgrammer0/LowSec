package top.dadagum.lowsec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.dadagum.lowsec.domain.Roles;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 10:42
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private X509UserDetails x509UserDetails;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private DbUserDetails dbUserDetails;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/hello").permitAll()
                .antMatchers("/users").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll().
                and().
                x509().
                subjectPrincipalRegex("O=(LowSec.*)"). // username == cert.O
                userDetailsService(x509UserDetails);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(dbUserDetails);
        auth.authenticationProvider(authProvider);
    }
}
