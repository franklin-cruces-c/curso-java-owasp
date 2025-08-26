package owasp.a7;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GeneradorContrasenyas {
	public static void main(String[] args) throws Exception {
		System.out.println("Usuario en base64: " + codificaBase64("usuariosecreto".getBytes()));
		System.out.println("Contrasenya en base64: " + codificaBase64("contrasenyasecreta".getBytes()));
		
		
		SecretKeySpec claveSecreta = new SecretKeySpec("semilladieciseis".getBytes(), "AES");
		System.out.println("Usuario cifrado: " + cifra("usuarioSuperSecreto", claveSecreta));
		System.out.println("Contrasenya cifrada: " + cifra("contrasenyaSuperSecreta", claveSecreta));

	}

    private static String cifra(String texto, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(texto.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return codificaBase64(iv) + ":" + codificaBase64(cryptoText);
    }
    
    private static String codificaBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
