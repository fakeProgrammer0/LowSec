package top.dadagum.lowsec.config;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import top.dadagum.lowsec.AppRunnerTest;

public class EncryptTest extends AppRunnerTest
{
    @Autowired
    private StringEncryptor encryptor;
    
    @Value("${jasypt.encryptor.password}")
    private String jasypt_key;
    
    @Test
    public void generateKeys()
    {
//        System.out.println("jasypt_key: " + jasypt_key);
        
        String ENC_url = encryptor.encrypt("jdbc:mysql://139.196.162.127:3306/infosec?useUnicode=true&characterEncoding=utf-8&requireSSL=true&useSSL=true&verifyServerCertificate=false");
        String ENC_username = encryptor.encrypt("infosec-dev");
        String ENC_password = encryptor.encrypt("InfoSec@2019");
        System.out.printf("url: ENC(%s)\n", ENC_url);
        System.out.printf("username: ENC(%s)\n", ENC_username);
        System.out.printf("password: ENC(%s)\n", ENC_password);
        Assert.assertTrue(ENC_username.length() > 0);
        Assert.assertTrue(ENC_password.length() > 0);
    }
}
