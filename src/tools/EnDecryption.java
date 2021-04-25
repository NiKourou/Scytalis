package tools;

import static common.GuiBase.checkIfEncrypted;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import common.IGuiTexts;

/**
 * Class for encrypting and decrypting text.
 * 
 * @author NK
 * @since 2020-10-11
 */
public class EnDecryption implements IGuiTexts{

	private static SecretKeySpec secretKey;
	private static byte[] key;

	/**
	 * Sets the key.
	 * 
	 * @param myKey
	 *              {@link String}
	 */
	public void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes(TEXT_UTF_8);
			sha = MessageDigest.getInstance(TEXT_SHA_1);
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, TEXT_AES);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Encrypts a human readable text.
	 * 
	 * @param strToEncrypt
	 *                     {@link String}
	 * @param secret
	 *                     {@link String}
	 * @return {@link String}
	 */
	public String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance(TEXT_CIPHER);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(TEXT_UTF_8)));
		} catch (Exception e) {
			// TODO: error message to info-area
			System.err.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	/**
	 * Decrypts an already encrypted text.
	 * 
	 * @param strToDecrypt
	 *                     {@link String}
	 * @param secret
	 *                     {@link String}
	 * @return {@link String}
	 */
	public String decrypt(String strToDecrypt, String secret) {
		try {
			if(checkIfEncrypted(strToDecrypt)) {
				setKey(secret);
				Cipher cipher = Cipher.getInstance(TEXT_CIPHER);
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
				return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));				
			}
		} catch (Exception e) {
			// TODO: error message to info-area
			System.err.println("Error while decrypting." + e.toString());
		}
		return null;
	}
}