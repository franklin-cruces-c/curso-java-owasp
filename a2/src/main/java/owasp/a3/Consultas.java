package owasp.a3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultas {
	static Connection connection;

	public static void main(String[] args) throws Exception { // Gestión inadecuada de excepciones
		String nombre = "pepe";
		String contrasenya = "1234";
		String ataque = "algo' OR 1=1 --";

		connection = getConnection();
		prepararUsuarios();

		autenticaInseguro(nombre, contrasenya);
		autenticaInseguro(ataque, contrasenya);

		autenticaSeguro(nombre, contrasenya);
		autenticaSeguro(ataque, contrasenya);
		
		connection.close();
	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:usuarios.db");
	}

	private static void prepararUsuarios() throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		String sql = "CREATE TABLE usuarios " 
				+ "(ID INT PRIMARY KEY NOT NULL," 
				+ " usuario TEXT NOT NULL, "
				+ " contrasenya TEXT NOT NULL)";
		stmt.executeUpdate(sql);
		
		stmt.executeUpdate("INSERT INTO usuarios VALUES (1, 'pepe', '1234');");
		stmt.executeUpdate("INSERT INTO usuarios VALUES (2, 'alba', '4321');");
		stmt.executeUpdate("INSERT INTO usuarios VALUES (3, 'lola', 'abba');");
		stmt.close();
	}

	private static void autenticaInseguro(String usuario, String contrasenya) throws SQLException {
		// /!\ Riesgo de inyección
		String query = "SELECT * FROM usuarios WHERE usuario = '" + usuario + "' AND contrasenya = '" + contrasenya + "'";

		System.out.println("Query insegura: " + query);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2) + " - " + resultSet.getString(3));
		}
	}

	private static void autenticaSeguro(String usuario, String contrasenya) throws SQLException {
		String query = "SELECT * FROM usuarios WHERE usuario = ? AND contrasenya = ?";
		System.out.println("Query segura: " + query + " con " + usuario + " y " + contrasenya);

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, usuario);
		statement.setString(2, contrasenya);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2) + " - " + resultSet.getString(3));
		}
	}

}
