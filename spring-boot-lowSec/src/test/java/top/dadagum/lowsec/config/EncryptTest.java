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

public class EncryptTest extends AppRunnerTest
{
    @Test
    @Repeat(value = 10)
    public void testBCrypt1()
    {
        String password = "123456";
        String salt = BCrypt.gensalt();
        String encoded_password = BCrypt.hashpw(password, salt);
        System.out.println("password: " + password);
        System.out.println("    salt: " + salt);
        System.out.println("  hashed: " + encoded_password);
        Assert.assertTrue(BCrypt.checkpw(password,encoded_password));
        Assert.assertTrue(encoded_password.startsWith(salt));
    }
    
    @Test
    @Repeat(value = 10)
    public void testBCrypt2()
    {
        PasswordEncoder encoder1 = new BCryptPasswordEncoder();
        PasswordEncoder encoder2 = new BCryptPasswordEncoder();
        String password = "123456";
        String hashed = encoder1.encode(password);
        System.out.println("  hashed: " + hashed);
        Assert.assertTrue(encoder2.matches(password, hashed));
    }
    
    @Test
    public void testBCrypt3()
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw1 = "123456";
        String pw2 = "test";
        String hashed1 = encoder.encode(pw1);
        String hashed2 = encoder.encode(pw2);
        System.out.println(pw1 + " -> " + hashed1);
        System.out.println(pw2 + " -> " + hashed2);
    }
}
