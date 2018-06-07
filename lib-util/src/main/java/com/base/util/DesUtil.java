package com.base.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.util.Base64;

/**
 * DES加密工具类
 */
public class DesUtil {

	private static final byte[] DESkey = { 39, 35, 96, 46, 10, 81, 111, 47 };// 设置密钥
	private static final byte[] DESIV = { 15, 12, 107, 29, 11, 21, 114, 36 };// 设置向量

	static AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实例
	private static Key key = null;

	/**
	 * 加密
	 * 
	 * 属性 data
	 * @return
	 * 异常 Exception
	 */
	public static String encode(String data) throws Exception {
		init(DESkey, DESIV);
		Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
		enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
		byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
		return Base64.encodeToString(pasByte, Base64.NO_WRAP);
	}

	private static void init(byte[] DESkey, byte[] DESIV) {
		DESKeySpec keySpec;
		try {
			keySpec = new DESKeySpec(DESkey);// 设置密钥参数
			iv = new IvParameterSpec(DESIV);// 设置向量
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
			key = keyFactory.generateSecret(keySpec);// 得到密钥对象
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解密
	 * 
	 * 属性 data
	 * @return
	 * 异常 Exception
	 */
	public static String decode(String data) throws Exception {
		init(DESkey, DESIV);
		Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] pasByte = deCipher.doFinal(Base64.decode(data, Base64.DEFAULT));
		return new String(pasByte, "UTF-8");
	}
}
