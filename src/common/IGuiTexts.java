package common;

/**
 * Interface for holding text constants.
 * 
 * @author NK
 */
public interface IGuiTexts {
	/**
	 * {@value} 
	 */
	String PROGRAM_NAME = "Scytalis";
	/**
	 * {@value} 
	 */
	String TEXT_HUMAN_READABLE_TEXT = "Open a human readable text";
	/**
	 * {@value} 
	 */
	String TEXT_OPEN_ENCRYPTED_FILE = "Open an encrypted file";
	/**
	 * {@value} 
	 */
	String TEXT_ENCRYPT = "Save as encrypted text";
	/**
	 * {@value} 
	 */
	String TEXT_NO_TEXT_TO_ENCRYPT = "There is no text to encrypt.";
	/**
	 * {@value} 
	 */
	String TEXT_SAVE_AS = "Save as...";
	/**
	 * {@value} 
	 */
	String TEXT_DECRYPT = "Decrypt";
	/**
	 * {@value} 
	 */
	String TEXT_EXIT = "Exit";
	/**
	 * {@value} 
	 */
	String TEXT_CHOOSE_FILE = "Choose a file";
	/**
	 * {@value} 
	 */
	String DIALOG_OPEN_HR = "Open a file with human readable text";
	/**
	 * {@value} 
	 */
	String DIALOG_OPEN_ENCRYPTED = "Open a file with encrypted text";
	/**
	 * {@value} 
	 */
	String INFO_FILE_LOADED = "File successfully loaded.";
	/**
	 * {@value} 
	 */
	String INFO_DECRYPTED = "Text successfully decrypted.";
	/**
	 * {@value} 
	 */
	String INFO_UNABLE_LOAD_FILE = "Unable to load the file. Text in the file is encrypted.";
	/**
	 * {@value} 
	 */
	String TEXT_FIND = "Find";
	/**
	 * {@value} 
	 */
	String TEXT_SEARCH = "Search";
	
	/* ------- Encryption / Decryption --------*/
	
	/**
	 * {@value} 
	 */
	String TEXT_UTF_8 = "UTF-8";
	/**
	 * {@value} 
	 */
	String TEXT_SHA_1 = "SHA-1";
	/**
	 * {@value} 
	 */
	String TEXT_AES = "AES";
	/**
	 * {@value} 
	 */
	String TEXT_CIPHER = "AES/ECB/PKCS5Padding";
}