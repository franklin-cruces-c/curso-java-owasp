package owasp.a8;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

public class Deserializacion {
	
	public static void main(String[] args) throws Exception { // Gestión inadecuada de excepciones
		ObjetoPermitido permitido = new ObjetoPermitido("Seguro");
		ObjetoPeligroso peligro = new ObjetoPeligroso("Peligroso");
		
		System.out.println("== Inseguro ==");
		deserializacionInsegura(permitido);
		deserializacionInsegura(peligro);
		
		System.out.println("== Seguro ==");
		deserializacionSegura(permitido);
		deserializacionSegura(peligro);
	}
	
	protected static void deserializacionInsegura(Object o) throws ClassNotFoundException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.flush();
		oos.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		System.out.println(obj);
	}
	
	protected static void deserializacionSegura(Object o) throws ClassNotFoundException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.flush();
		oos.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new SecureObjectInputStream(bais);
		Object obj = ois.readObject();
		System.out.println(obj);
		ois.close();
	}
}

class SecureObjectInputStream extends ObjectInputStream {
	public SecureObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
		// Solo deserializa instancias de ObjetoPermitido
		if (!osc.getName().equals(ObjetoPermitido.class.getName())) {
			throw new InvalidClassException("Unauthorized deserialization", osc.getName());
		}
		return super.resolveClass(osc);
	}
}

class ObjetoPermitido implements Serializable {
	private static final long serialVersionUID = 3322841261299494848L;

	private String a;
	
	public ObjetoPermitido(String a) {
		super();
		this.a = a;
	}

	@Override
	public String toString() {
		return a;
	}
}

class ObjetoPeligroso implements Serializable {
	private static final long serialVersionUID = 21339465413472562L;

	private String a;
	
	public ObjetoPeligroso(String a) {
		super();
		this.a = a;
	}

	@Override
	public String toString() {
		return a;
	}
}