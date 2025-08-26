package owasp.a7;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Contrasenyas {

	public static void main(String[] args) throws Exception {
		String usuario = "pepe";
		String contrasenya = "1234";
		System.out.println("En duro: " + usuario + " - " + contrasenya);

		InputStream is = Contrasenyas.class.getClassLoader().getResourceAsStream("secreto.properties");
		Properties properties = new Properties();
		properties.load(is);

		String usuario2 = properties.getProperty("usuario");
		String contrasenya2 = properties.getProperty("contrasenya");
		System.out.println("Desde properties: " + usuario2 + " - " + contrasenya2);
				
		String usuario3 = new String(decodificaBase64(properties.getProperty("usuario.b64")));
		byte[] contrasenya3 = decodificaBase64(properties.getProperty("contrasenya.b64"));
		System.out.println("Desde properties en base 64: " + usuario3 + " - " + new String(contrasenya3));
		
		SecretKeySpec claveSecreta = new SecretKeySpec("semilladieciseis".getBytes(), "AES");
		String usuario4 = descifra(properties.getProperty("usuario.secreto"), claveSecreta);
		String contrasenya4 = descifra(properties.getProperty("contrasenya.secreta"), claveSecreta);
		System.out.println("Desde properties cifrada: " + usuario4 + " - " + contrasenya4);

	}
    
    private static String descifra(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
        String iv = string.split(":")[0];
        String texto = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(decodificaBase64(iv)));
        return new String(pbeCipher.doFinal(decodificaBase64(texto)), "UTF-8");
    }

    private static byte[] decodificaBase64(String property) {
        return Base64.getDecoder().decode(property);
    }


}
