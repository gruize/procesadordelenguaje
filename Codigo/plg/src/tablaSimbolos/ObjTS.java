package tablaSimbolos;

public class ObjTS {
	//Objeto que contiene los atributos de una fila de la TS
	
	String tipo;
	
	//Esta ser� la direcci�n de memoria asociada al objeto "Memoria" dentro de la 
	//estructura de util
	Integer dirM;
	
	
	public ObjTS() {
		// TODO Auto-generated constructor stub
		tipo = new String();
		dirM = new Integer(0);
	}
	
	public ObjTS(String _tipo, int _dirM) {
		// TODO Auto-generated constructor stub
		tipo = new String(_tipo);
		dirM = new Integer(_dirM);
	}

}