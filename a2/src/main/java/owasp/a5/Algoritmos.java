package owasp.a5;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Algoritmos {
	public static void main(String[] args) throws Exception { // Gestión inadecuada de excepciones

		// AES
		Cipher malo1 = Cipher.getInstance("AES"); // Usa ECB por defecto
		Cipher malo2 = Cipher.getInstance("AES/ECB/NoPadding");
		Cipher bueno1 = Cipher.getInstance("AES/GCM/NoPadding"); // GCM sí es una buena opción

		// RSA
		Cipher malo3 = Cipher.getInstance("RSA/ECB/NoPadding");
		Cipher bueno3 = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");

		cifra(malo1, generaClaveAES());
		cifra(malo2, generaClaveAES());
		cifra(bueno1, generaClaveAES());
		cifra(malo3, generaClaveRSA());
		cifra(bueno3, generaClaveRSA());
	}

	private static void cifra(Cipher c, Key clave) throws Exception {
		c.init(Cipher.ENCRYPT_MODE, clave);
		byte[] cifrado = c.doFinal("holaholaholahola".getBytes());
		System.out.println("Cifrado usando " + c.getAlgorithm() + ": " + new String(Base64.getEncoder().encode(cifrado)));
	}
	
	private static Key generaClaveAES() {
		return new SecretKeySpec("blablablablabla1".getBytes(), "AES");
	}
	
	private static Key generaClaveRSA() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		return kp.getPublic();
	}

}
