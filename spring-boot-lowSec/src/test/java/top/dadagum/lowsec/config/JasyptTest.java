package top.dadagum.lowsec.config;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Repeat;
import top.dadagum.lowsec.AppRunnerTest;

public class JasyptTest extends AppRunnerTest
{
    @Autowired
    private StringEncryptor encryptor;
    
    @Value("${jasypt.encryptor.password}")
    private String jasypt_key;
    
    @Test
    public void generateKeys()
    {
        String ENC_url = encryptor.encrypt("${mysql_url}");
        String ENC_username = encryptor.encrypt("${mysql_username}");
        String ENC_password = encryptor.encrypt("${mysql_password}");
        System.out.printf("url: ENC(%s)\n", ENC_url);
        System.out.printf("username: ENC(%s)\n", ENC_username);
        System.out.printf("password: ENC(%s)\n", ENC_password);
        Assert.assertTrue(ENC_username.length() > 0);
        Assert.assertTrue(ENC_password.length() > 0);
    }
    
}
