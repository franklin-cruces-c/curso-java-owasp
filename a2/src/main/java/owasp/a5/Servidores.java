package owasp.a5;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Servidores {

	public static void main(String[] args) throws Exception {
		urlInsegura();
		urlSegura();
		
		emailInseguro();
		emailSeguro();
		
		javaMailInseguro();
		javaMailSeguro();

	}

	private static void urlInsegura() throws IOException {
		URL url = new URL("https://example.com/");
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		urlConnection.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String requestedHost, SSLSession remoteServerSession) {
				return false; // Noncompliant
			}
		});
		System.out.println(urlConnection.getHostnameVerifier());
//		urlConnection.getHostnameVerifier().verify("https://example.com/", urlConnection.)
		System.out.println(urlConnection.getHeaderFields());
		urlConnection.connect();
		System.out.println(urlConnection.getHeaderFields());
	}

	private static void urlSegura() throws IOException {
		URL url = new URL("https://example.com/");
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		// Compliant; Use the default HostnameVerifier
		System.out.println(urlConnection.getHostnameVerifier());
		InputStream in = urlConnection.getInputStream();
		System.out.println(urlConnection.getHeaderFields());
	}

	private static void emailInseguro() throws EmailException {
		Email email = new SimpleEmail();
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true); // Noncompliant; setSSLCheckServerIdentity(true) should also be called before
										// sending the email
		email.send();
	}

	private static void emailSeguro() throws EmailException {
		Email email = new SimpleEmail();
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setSSLCheckServerIdentity(true); // Compliant
		email.send();
	}

	private static void javaMailInseguro() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Noncompliant; Session is
																						// created without having
																						// "mail.smtp.ssl.checkserveridentity"
																						// set to true
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("username@gmail.com", "password");
			}
		});
	}

	private static void javaMailSeguro() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.ssl.checkserveridentity", true); // Compliant
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("username@gmail.com", "password");
			}
		});
	}
}
