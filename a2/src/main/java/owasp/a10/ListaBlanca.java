package owasp.a10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

public class ListaBlanca {
	private static final List<String> LISTA_BLANCA = Arrays.asList(new String[] {"https://www.linkedin.com", "https://www.linkedin.com/learning"});

    public static void main(String[] args) throws Exception {
    	String url1 = "https://www.linkedin.com";
    	String url2 = "https://example.com";
        String content1 = ListaBlanca.leerUrlMal(url1);
        pintarCortado(url1, content1);
        String content2 = ListaBlanca.leerUrlMal(url2);
        pintarCortado(url2, content2);

        String content3 = ListaBlanca.leerUrlBien(url1);
        pintarCortado(url1, content3);
        String content4 = ListaBlanca.leerUrlBien(url2);
        pintarCortado(url2, content4);
    }
        
    private static String leerUrlMal(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }
    
	private static String leerUrlBien(String url) throws Exception {
		if (!LISTA_BLANCA.contains(url)) {
			return "URL INSEGURA";
		}
	    URL website = new URL(url);
	    URLConnection connection = website.openConnection();
	    BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream()));
	
	    StringBuilder response = new StringBuilder();
	    String inputLine;
	
	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	
	    in.close();
	
	    return response.toString();
	}
	
	private static void pintarCortado(String url, String s) {
		System.out.println(url + ">> ");
		if (s.length() <= 100) {
			System.out.println("\t" + s);
		} else {
			System.out.println("\t" + s.substring(0, 100));
		}
	}
}
