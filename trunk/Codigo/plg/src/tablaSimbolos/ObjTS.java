package tablaSimbolos;

public class ObjTS {
	//Objeto que contiene los atributos de una fila de la TS
	
	String tipo;
	
	//Esta será la dirección de memoria asociada al objeto "Memoria" dentro de la 
	//estructura de util
	Integer dirM;
	
	
	public ObjTS() {
		// TODO Auto-generated constructor stub
		tipo = new String();
		dirM = new Integer(0);
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getDirM() {
		return dirM;
	}

	public void setDirM(Integer dirM) {
		this.dirM = dirM;
	}

	public ObjTS(String _tipo, int _dirM) {
		// TODO Auto-generated constructor stub
		tipo = new String(_tipo);
		dirM = new Integer(_dirM);
	}

}
