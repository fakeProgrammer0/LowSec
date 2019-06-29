package top.dadagum.lowsec.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;


public class PasswordHelper {
	
	public static final String getBCryptPassword(String password){
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.encode(password);
	}
	
	public static final String hashpw(String password)
	{
		String salt = BCrypt.gensalt();
		return BCrypt.hashpw(password, salt);
	}
	
	public static final boolean checkpw(String password, String hashed)
	{
		return BCrypt.checkpw(password, hashed);
	}
}
