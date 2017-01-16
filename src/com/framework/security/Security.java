package com.framework.security;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 安全类
 * 
 * @File_name Security.java
 * @Package com.framework.security
 * @author misty
 * @date 2014年2月21日 下午4:34:57
 * @version V1.00.00
 */

public final class Security {

	public Security() {
		
	}

	private static byte[] symmetricEncrypto(byte byteSource[]) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte abyte0[];
		try {
			int mode = 1;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte keyData[] = { 1, 9, 7, 9, 1, 0, 0, 7 };
			DESKeySpec keySpec = new DESKeySpec(keyData);
			java.security.Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			int blockSize = cipher.getBlockSize();
			int position = 0;
			int length = byteSource.length;
			boolean more = true;
			while (more)
				if (position + blockSize <= length) {
					baos.write(cipher.update(byteSource, position, blockSize));
					position += blockSize;
				} else {
					more = false;
				}
			if (position < length)
				baos.write(cipher.doFinal(byteSource, position, length
						- position));
			else
				baos.write(cipher.doFinal());
			abyte0 = baos.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
		return abyte0;
	}

	private static byte[] symmetricDecrypto(byte byteSource[])
			throws Exception
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte abyte0[];
			try{
				int mode = 2;
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				byte keyData[] = {
					1, 9, 7, 9, 1, 0, 0, 7
				};
				DESKeySpec keySpec = new DESKeySpec(keyData);
				java.security.Key key = keyFactory.generateSecret(keySpec);
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(mode, key);
				int blockSize = cipher.getBlockSize();
				int position = 0;
				int length = byteSource.length;
				boolean more = true;
				while (more) 
					if (position + blockSize <= length){
						baos.write(cipher.update(byteSource, position, blockSize));
						position += blockSize;
					} else{
						more = false;
					}
				if (position < length)
					baos.write(cipher.doFinal(byteSource, position, length - position));
				else
					baos.write(cipher.doFinal());
				abyte0 = baos.toByteArray();
			}catch(Exception e){
				throw e;	
			}finally{
				baos.close();
			}
			return abyte0;
		}

	public static byte[] hashMethod(byte byteSource[]) throws Exception {
		try {
			MessageDigest currentAlgorithm = MessageDigest.getInstance("SHA-1");
			currentAlgorithm.reset();
			currentAlgorithm.update(byteSource);
			return currentAlgorithm.digest();
		} catch (Exception e) {
			throw e;
		}
	}

	public static String Encrypto(String pawss) throws Exception {
		String returnStr = "";
		byte utf8[] = pawss.getBytes("UTF-8");
		returnStr = (new BASE64Encoder()).encode(symmetricEncrypto(utf8));
		return returnStr;
	}

	public static String Decrypto(String pawss) throws Exception {
		String returnStr = "";
		byte utf8[] = (new BASE64Decoder()).decodeBuffer(pawss);
		returnStr = new String(symmetricDecrypto(utf8), "UTF-8");
		return returnStr;
	}

}
