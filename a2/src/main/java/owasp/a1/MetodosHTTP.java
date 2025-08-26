package owasp.a1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class MetodosHTTP {

	// /!\ PELIGRO /!\ Admite todos los métodos: GET, PUT, POST, DELETE
	@RequestMapping("/borrar_usuario_mal_1")
	public String borrarMal1(String usuario) {
		// aquí iría el código para borrar el usuario
		return "mal";
	}

	// /!\ PELIGRO /!\ Admite métodos: GET, POST
	@RequestMapping(path = "/borrar_usuario_mal_2", method = { RequestMethod.GET, RequestMethod.POST })
	public String borrarMal2(@RequestParam("id") String id) {
		// aquí iría el código para borrar el usuario
		return "mal";
	}

	// Seguro, solo admite POST
//	@RequestMapping(path = "/borrar_usuario", method = RequestMethod.POST)
	@PostMapping(path = "/borrar_usuario_bien_1")
	public String borrarBien1(String username) {
		// aquí iría el código para borrar el usuario
		return "bien";
	}

	// Seguro, solo admite POST
	@PostMapping(path = "/borrar_usuario_bien_2")
	public String borrarBien2(@RequestParam("id") String id) {
		// aquí iría el código para borrar el usuario
		return "bien";
	}
}
