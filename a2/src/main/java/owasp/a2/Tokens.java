package owasp.a2;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class Tokens {
	private static final String EMISOR = "Emisor";
	private static final String CLAVE_SECRETA = "shhhhshhhh";

	public static void main(String[] args) {
		System.out.println("INSEGURO");
		String tokenInseguro = generaTokenInseguro();
		System.out.println("Token: " + tokenInseguro);
		validaTokenInseguro(tokenInseguro);
		System.out.println();
		
		System.out.println("SEGURO");
		String token = generaTokenSeguro();
		System.out.println("Token: " + token);
		validaTokenSeguro(token);
		System.out.println();
		
		System.out.println("Verificar token inseguro con validación segura:");
		validaTokenSeguro(tokenInseguro);
	}

	private static String generaTokenInseguro() {
		return JWT.create().withIssuer(EMISOR)
				.withClaim("clave", "valor")
				.sign(Algorithm.none());
	}

	private static void validaTokenInseguro(String token) {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.none()).withIssuer(EMISOR).build();

			DecodedJWT decodedJWT = verifier.verify(token);

			System.out.println("Verificación OK");
			System.out.println("Privilegios: " + decodedJWT.getClaims());
		} catch (JWTVerificationException ex) {
			System.out.println("Verificación KO: " + ex.getMessage());
		}
	}

	private static String generaTokenSeguro() {
		Algorithm algorithm = Algorithm.HMAC512(CLAVE_SECRETA);

		return JWT.create().withIssuer(EMISOR)
				.withClaim("clave", "valor")
				.sign(algorithm);
	}

	private static void validaTokenSeguro(String token) {
		Algorithm algorithm = Algorithm.HMAC512(CLAVE_SECRETA);

		try {
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(EMISOR).build();

			DecodedJWT decodedJWT = verifier.verify(token);

			System.out.println("Verificación OK");
			System.out.println("Privilegios: " + decodedJWT.getClaims());
		} catch (JWTVerificationException ex) {
			System.out.println("Verificación KO: " + ex.getMessage());
		}
	}
}
