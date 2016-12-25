package com.focuschina.ehealth_lib.util;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CodeUtil {
	/**
	 * 加密/解密算法/工作模式/填充方式
	 *
	 * JAVA6 支持PKCS5PADDING填充方式
	 * Bouncy castle支持PKCS7Padding填充方式
	 * */
	public static final String CIPHER_ALGORITHM="AES/ECB/PKCS7Padding";

	public static Key k;
	public static Base64Encoder decoder = new Base64Encoder();

	static{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		/** 密钥算法
		 * java6支持56位密钥，bouncycastle支持64位
		 */
		k = new SecretKeySpec("jiankangwuyouabc".getBytes(),"AES");
	}

	// 加密
	public static String encode(String sSrc){
		//初始化密钥
        try {
			 byte[] data=sSrc.getBytes("UTF-8");
			 //还原密钥

	         Cipher cipher= Cipher.getInstance(CIPHER_ALGORITHM, "BC");
	         //初始化，设置为加密模式
	         cipher.init(Cipher.ENCRYPT_MODE, k);
	         //执行操作
	         return new Base64Encoder().encode(cipher.doFinal(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 解密
	public static String decode(String sSrc) {
        byte[] data;
		try {
			data = decoder.decode(sSrc);
	        Cipher cipher= Cipher.getInstance(CIPHER_ALGORITHM);
	        //初始化，设置为解密模式
	        cipher.init(Cipher.DECRYPT_MODE, k);
	        //执行操作
	        return new String(cipher.doFinal(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//初始化密钥
		return null;
	}

     public static void main(String[] args) throws UnsupportedEncodingException {
    	 try {
			 String str = CodeUtil.encode("{'hospitalCode':'13102'}");
	    	 System.out.println(str);
	    	 System.out.println(CodeUtil.decode(str));
		} catch (Exception e) {
			e.printStackTrace();
		}
     }


}
