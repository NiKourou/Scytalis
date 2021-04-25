package src_test;

import static common.GuiBase.checkIfEncrypted;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class scytalis_test {

	private static final String plainText = "www.abc.com: my password";
	private static final String encryptedText = "3j8FMQCncSbLVMS0DEHoee0T1zw+rcPp9nEQ0A4vhVjXtxgqOKyef2buQzqSv8zD71isVFm4Xb9sCautNSNvAPRAjHS6Jh82t9NtWp02Itd1p6Er3N6fhOn3Qfp1U1EKcWXi3EAaALxxH+TWG+e5M3rc5oUnE9NRBcbO7rgOiDvs4ZWzRRBwdwDQ/Hq1DjsU6mAfWf8tFd5yfZvfmPNfxSDvcYiDlalt+pjOWuG2NlIQd2F1SvDcp8N73W7dEVuX";
	
	/**
	 * Testing method: checkIfEncrypted.
	 */
	@Test
	void testCheckIfEncrypted() {
		assertEquals(false, checkIfEncrypted(plainText));
		assertEquals(true, checkIfEncrypted(encryptedText));
	}
}
