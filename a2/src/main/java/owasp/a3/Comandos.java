package owasp.a3;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Comandos {

	private static final List<String> PERMITIDOS = Arrays.asList("mkdir", "cd");

	public static void main(String[] args) throws Exception {
		System.out.println("== Inseguro ==");
		comandoInseguro("mkdir", "ataque");
		comandoInseguro("cd", "ataque");
		comandoInseguro("ls", "");
		comandoInseguro("rmdir", "ataque");
		comandoInseguro("rmdir", "ataque2");

		System.out.println("== Seguro ==");
		comandoSeguro("mkdir", "ataque");
		comandoSeguro("cd", "ataque");
		comandoSeguro("ls", "");
		comandoSeguro("rmdir", "ataque");
		comandoSeguro("rmdir", "ataque2");
	}

	private static void comandoInseguro(String comando, String arg) throws IOException {
		Process p = Runtime.getRuntime().exec(comando + " " + arg);
		while (p.isAlive()) {
			// esperamos a que termine
		}
		if (p.exitValue() == 0) {
			System.out.println(comando + " " + arg + " ejecutado!");
		} else {
			System.out.println(comando + " " + arg + " fallido!");
		}
	}

	private static void comandoSeguro(String comando, String arg) throws IOException {
		if (PERMITIDOS.contains(comando)) {
			Process p = Runtime.getRuntime().exec(comando + " " + arg);
			while (p.isAlive()) {
				// esperamos a que termine
			}
			if (p.exitValue() == 0) {
				System.out.println(comando + " " + arg + " ejecutado!");
			} else {
				System.out.println(comando + " " + arg + " fallido!");
			}
		} else {
			System.err.println(comando + " " + arg + " no permitido!");
		}
	}

}
