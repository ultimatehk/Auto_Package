package cn.gyyx.AuxiliaryTools;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DesCyptoUtils {

	public static final String DES_ALGORITHM = "DES/CBC/PKCS5Padding";

	/**
	 * DES算法加密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            密钥
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws GeneralSecurityException
	 */
	public static String encode(String data, String key) {
		try {

			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(new DESKeySpec(key
					.getBytes("utf-8")));

			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(
					new byte[8]));

			return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));

		} catch (UnsupportedEncodingException e) {
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES算法解密
	 * 
	 * @param data
	 *            待解密字符串
	 * @param key
	 *            密钥
	 * @return 解密后的字节数组
	 */
	public static String decode(String data, String key) {
		try {

			byte[] encryptionBytes = Base64.decodeBase64(data);

			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey;
			secretKey = keyFactory.generateSecret(new DESKeySpec(key
					.getBytes("utf-8")));

			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(
					new byte[8]));

			return new String(cipher.doFinal(encryptionBytes));

		} catch (UnsupportedEncodingException e) {
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}