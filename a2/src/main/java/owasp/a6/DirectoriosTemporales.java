package owasp.a6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoriosTemporales {

	public static void main(String[] args) throws IOException { // Gestión inadecuada de excepciones
		// Versión NO fiable
		File tempDir;
		tempDir = File.createTempFile("test_malo", "");
		tempDir.delete();
		tempDir.mkdir(); 
		System.out.println(tempDir);
		
		
		// Versión fiable
		Path tempPath = Files.createTempDirectory("test_bueno");
		File tempDir2 = tempPath.toFile();
		System.out.println(tempDir2);
		
	}

}
