package lu.nidhogg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class KeyStoreUtil {

	private static final String KEYSTORE_FILE = "nidhogg.ks";
	
	private static final char[] PASSWORD = "changeit".toCharArray();
	
	private static KeyStore instance;
	
	public static KeyStore getAppKeystore() throws Exception {
		if(instance == null) {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			File keystore = new File(KEYSTORE_FILE);
			if (!keystore.exists() || !keystore.isFile()) {
				ks.load(null, PASSWORD);
				initAppKeystore(ks);
				ks.store(new FileOutputStream(keystore), PASSWORD);
			} else {
				ks.load(new FileInputStream(keystore), PASSWORD);
			}
			instance = ks;
		}
		return instance;
	}

	private static void initAppKeystore(KeyStore ks) throws Exception {
		SecretKey authSecretKey = Keys.secretKeyFor(isLimitedCryptoPolicy() ? SignatureAlgorithm.HS256 : SignatureAlgorithm.HS512);
		ks.setEntry("authserver", new SecretKeyEntry(authSecretKey), new PasswordProtection(PASSWORD));
	}

	private static boolean isLimitedCryptoPolicy() {
		try {
			int maxKeySize = Cipher.getMaxAllowedKeyLength("AES");
			return maxKeySize <= 256;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Key getAuthenticationKey() throws Exception {
		return getAppKeystore().getKey("authserver", PASSWORD);
	}
	
	/*
	 * public static X509Certificate selfSign(SecretKey authSecretKey, String
	 * subjectDN) throws OperatorCreationException, CertificateException,
	 * IOException { Provider bcProvider = new BouncyCastleProvider();
	 * Security.addProvider(bcProvider); long now = System.currentTimeMillis(); Date
	 * startDate = new Date(now); X500Name dnName = new X500Name(subjectDN);
	 * BigInteger certSerialNumber = new BigInteger(Long.toString(now)); Calendar
	 * calendar = Calendar.getInstance(); calendar.setTime(startDate);
	 * calendar.add(Calendar.YEAR, 1); // <-- 1 Yr validity Date endDate =
	 * calendar.getTime(); String signatureAlgorithm = isLimitedCryptoPolicy() ?
	 * SignatureAlgorithm.HS256.getJcaName() :
	 * SignatureAlgorithm.HS512.getJcaName(); ContentSigner contentSigner = new
	 * JcaContentSignerBuilder(signatureAlgorithm).b JcaX509v3CertificateBuilder
	 * certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber,
	 * startDate, endDate, dnName, authSecretKey.getPublic()); BasicConstraints
	 * basicConstraints = new BasicConstraints(true); certBuilder.addExtension(new
	 * ASN1ObjectIdentifier("2.5.29.19"), true, basicConstraints); return new
	 * JcaX509CertificateConverter().setProvider(bcProvider).getCertificate(
	 * certBuilder.build(contentSigner)); }
	 */

}
