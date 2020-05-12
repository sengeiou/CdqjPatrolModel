package com.cdqj.cdqjpatrolandroid.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密解密类
 *
 * @author dinghc
 * @date 2018年7月19日
 * @version v1.0.0 此代码为成都千嘉有限公司所有，未经允许，请勿传播
 */
public class AESUtils {

	/** 密匙 */
	private static final byte[] KEY = "cdqj@p0ssw0rd#*!".getBytes();
	/** 偏移 */
	private static final byte[] IV = "cdqj@p0ssw0rd#*!".getBytes();
	private static final String KEY_INSTANCE = "AES/CBC/PKCS5Padding";
	private static final String KEY_SPEC = "AES";

	/** 初始化长度 */
	//private static final int BEGININIT = 128;

	private static Cipher getCipher(int mode) throws Exception {
		Cipher cipher = Cipher.getInstance(KEY_INSTANCE);
		// 因为AES的加密块大小是128bit(16byte), 所以key是128、192、256bit无关
		// System.out.println("cipher.getBlockSize()： " + cipher.getBlockSize());
		SecretKeySpec secretKeySpec = new SecretKeySpec(KEY, KEY_SPEC);
		cipher.init(mode, secretKeySpec, new IvParameterSpec(IV));
		return cipher;
	}


	/**
	 *  数据加密
	 * @param data data
	 * @return byte
	 * @throws Exception Exception
	 */
	public static byte[] encrypt(byte[] data) throws Exception {
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
		return cipher.doFinal(data);
	}

	/**
	 * 字符串 加密
	 * @param data data
	 * @return String
	 * @throws Exception Exception
	 */
	public static String encrypt(String data) throws Exception {
		if (data == null) {
			return null;
		}
		return Base64.encodeToString(encrypt(data.getBytes()), Base64.DEFAULT);
	}

	/**
	 *   解密
	 * @return byte
	 * @throws Exception Exception
	 */
	public static byte[] decrypt(byte[] data) throws Exception {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * @param data data
	 * @return String
	 * @throws Exception Exception
	 */
	public static String decrypt(String data) throws Exception {
		if (data == null) {
			return null;
		}
		return new String(decrypt(Base64.decode(data, Base64.DEFAULT)));
	}


	public static void main(String[] args) throws Exception {
		String data = "这是一个测试！";

		String encrypt = encrypt(data);

		System.out.println(encrypt);

		String decrypt = decrypt("TQ3B+k6WV4x0XhNbYo4l+tn2a5dZGJ0HkUPWG//aI16wRXNUnPWRkwWhIzxNhezk");

		System.out.println("-----:" + decrypt);

	}

}
