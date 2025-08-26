package owasp.a1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

public class Permisos {

	public static void main(String[] args) throws Exception { // Gestión inadecuada de excepciones
		Path tempPath = Files.createTempDirectory("a1_permisos_");
		permisosInseguros1(tempPath);
		permisosInseguros2(tempPath);
		permisosInseguros3(tempPath);
		permisosSeguros1(tempPath);
		permisosSeguros2(tempPath);
		permisosSeguros3(tempPath);
	}

	private static void permisosInseguros1(Path tempPath) throws IOException {
		File fichero = File.createTempFile("inseguro1_", ".txt", tempPath.toFile());
		System.out.println(fichero);
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        // permisos para el usuario
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        // permisos para el grupo
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        // permisos para el resto /!\ PELIGRO /!\
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);

        Files.setPosixFilePermissions(fichero.toPath(), perms);
	}

	private static void permisosInseguros2(Path tempPath) throws IOException {
		File fichero = File.createTempFile("inseguro2_", ".txt", tempPath.toFile());
		System.out.println(fichero);
        Runtime.getRuntime().exec("chmod 777 " + fichero.getAbsolutePath());        
	}

	private static void permisosInseguros3(Path tempPath) throws IOException {
		File fichero = File.createTempFile("inseguro3_", ".txt", tempPath.toFile());
		System.out.println(fichero);
        Files.setPosixFilePermissions(fichero.toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));
	}

	private static void permisosSeguros1(Path tempPath) throws IOException {
		File fichero = File.createTempFile("seguro1_", ".txt", tempPath.toFile());
		System.out.println(fichero);
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        // permisos para el usuario
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        // permisos para el grupo
        perms.add(PosixFilePermission.GROUP_READ);
        perms.remove(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        // permisos para el resto
        perms.remove(PosixFilePermission.OTHERS_READ); 
        perms.remove(PosixFilePermission.OTHERS_WRITE); 
        perms.remove(PosixFilePermission.OTHERS_EXECUTE); 

        Files.setPosixFilePermissions(fichero.toPath(), perms);
	}

	private static void permisosSeguros2(Path tempPath) throws IOException {
		File fichero = File.createTempFile("seguro2_", ".txt", tempPath.toFile());
		System.out.println(fichero);
        Runtime.getRuntime().exec("chmod 750 " + fichero.getAbsolutePath());        
	}

	private static void permisosSeguros3(Path tempPath) throws IOException {
		File fichero = File.createTempFile("seguro3_", ".txt", tempPath.toFile());
		System.out.println(fichero);
        Files.setPosixFilePermissions(fichero.toPath(), PosixFilePermissions.fromString("rwxr-x---"));
	}

}
