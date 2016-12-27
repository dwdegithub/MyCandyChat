
package com.candychat.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesUtil {
	
	
	public static String pass = "$Nu&a*6M";
		

	/**
	* 加密
	* @param datasource byte[]
	* @param password String
	* @return byte[]
	*/
	public static byte[] encrypt(byte[] datasource, String password) {
		try{
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			//创建密匙工厂，然后用它把DESKeySpec转换
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");	//DES/ECB/NoPadding"
	
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");		//DES/ECB/NoPadding   DES

			//用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//获取数据并加密
			//正式执行加密操作
			return cipher.doFinal(datasource);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* 解密
	* @param src byte[]
	* @param password String
	* @return byte[]
	* @throws Exception
	*/
	public static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数�?
		SecureRandom random = new SecureRandom();
		// 创建DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  //DES/ECB/NoPadding
		//将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		//Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");   //DES/ECB/NoPadding    DES

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		//解密操作
		return cipher.doFinal(src);
	}
}

