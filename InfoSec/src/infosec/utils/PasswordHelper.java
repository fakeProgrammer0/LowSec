package infosec.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordHelper {
	
	public static final String getSaltedMD5Password(String password, Object salt){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password, salt);
	}
	

	public static final String getSaltedSHAPassword(String password, Object salt){
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		return sha.encodePassword(password, salt);
	}
	
	public static final String getBCryptPassword(String password){
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.encode(password);
	} 

	public static void main(String[] args) {
				
		String pwd_plaintext = "123456";
		
		//String encoded_password = PasswordHelper.getSaltedSHAPassword(pwd_plaintext, null);
		String encoded_password = PasswordHelper.getBCryptPassword(pwd_plaintext);
		
		System.out.println(encoded_password);

	}
	
}
