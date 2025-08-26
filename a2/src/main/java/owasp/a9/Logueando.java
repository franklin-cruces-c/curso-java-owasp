package owasp.a9;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logueando {
	private static Logger logger = LogManager.getLogger(Logueando.class);

	public static void main(String[] args) {

		String s = "hola";
		int num = 10;
		boolean haz = true;

		try {
			System.out.println("metodoQueFallaSinLogs");
			metodoQueFallaSinLogs(s, num, haz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		try {
			System.out.println("metodoQueFallaConLogsPobres");
			metodoQueFallaConLogsPobres(s, num, haz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		try {
			System.out.println("metodoQueFallaConLogsDetallados");
			metodoQueFallaConLogsDetallados(s, num, haz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		try {
			System.out.println("metodoQueFallaConLogsExcesivos");
			metodoQueFallaConLogsExcesivos(s, num, haz, "secretoMuySecreto");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private static void metodoQueFallaSinLogs(String arg1, int arg2, boolean arg3) {
		if (arg3) {
			System.out.println(arg1.charAt(arg2));
		}
	}

	private static void metodoQueFallaConLogsPobres(String arg1, int arg2, boolean arg3) {
		logger.info("En metodoQueFallaConLogsPobres");
		if (arg3) {
			System.out.println(arg1.charAt(arg2));
		}
	}

	private static void metodoQueFallaConLogsDetallados(String arg1, int arg2, boolean arg3) {
		logger.info("En metodoQueFallaConLogsDetallados arg1: {} arg2: {} arg3: {}", arg1, arg2, arg3);
		if (arg3) {
			try {
				System.out.println(arg1.charAt(arg2));
			} catch (IndexOutOfBoundsException e){
				logger.error(e);
				throw e;
			}
		}
	}

	private static void metodoQueFallaConLogsExcesivos(String arg1, int arg2, boolean arg3, String secreto) {
		logger.info("En metodoQueFallaConLogsExcesivos arg1: {} arg2: {} arg3: {} secreto: {}", arg1, arg2, arg3, secreto);
		if (arg3) {
			logger.info("En metodoQueFallaConLogsExcesivos dentro del if");
			logger.debug("En metodoQueFallaConLogsExcesivos antes del sys.out: " + arg1.charAt(arg2));
			System.out.println(arg1.charAt(arg2));
			logger.info("En metodoQueFallaConLogsExcesivos saliendo del if");
		}
		logger.info("En metodoQueFallaConLogsExcesivos saliendo");
	}
}
