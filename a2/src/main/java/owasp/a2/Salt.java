package owasp.a2;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.spec.PBEKeySpec;

public class Salt {

	public static void main(String[] args) {
		String[] contrasenas = {"password123", "mimamamemima", "fjeioswewo3225$"};
		System.out.println("MAL: ");
		for (String s : contrasenas) {
			byte[] salt = saltPredecible();
			System.out.print("salt: " + new String(Base64.getEncoder().encode(salt)));
			generaClave(s, salt);
		}
		
		System.out.println("BIEN: ");
		for (String s : contrasenas) {
			byte[] salt = saltBueno();
			System.out.print("salt: " + new String(Base64.getEncoder().encode(salt)));
			generaClave(s, salt);
		}
	}

	private static byte[] saltPredecible() {
		return "holahola".getBytes();
	}

	private static byte[] saltBueno() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
	
	private static void generaClave(String pw, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt, 10, 256);
		System.out.println("\tcontraseña: " + new String(spec.getPassword()));
	}
}
