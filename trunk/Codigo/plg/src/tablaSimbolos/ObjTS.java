package tablaSimbolos;

//import analizadorSintactico.*;

public class ObjTS {
	//Objeto que contiene los atributos de una fila de la TS
	//tSintetiz tipo;
	//Se quita el tipo de aquí porque irá en el objeto de las propiedades
	
	//Se deja uno auxiliar para probar sentencias de control
	////////////////////////////////////////////////////////
	tipoT tipo;
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	public tipoT getTipo() {
		return tipo;
	}

	public void setTipo(tipoT tipo) {
		this.tipo = tipo;
	}
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	
	//Esta será la dirección de memoria asociada al objeto "Memoria" dentro de la 
	//estructura de util
	Integer dirM;
	tClase clase;
	Integer nivel;
	
	//Objeto de las propiedades
	PropsObjTS propiedadesTipo;
	
	
	public ObjTS() {
		// TODO Auto-generated constructor stub
		//tipo = tSintetiz.tInicial;
		dirM = new Integer(0);
	}
	//Constructor 1º Cuat
	public ObjTS(tipoT _tipo, int _dirM) {
		// TODO Auto-generated constructor stub
		tipo = _tipo;
		dirM = new Integer(_dirM);
	}
	//Constructor 2º Cuat
	public ObjTS(PropsObjTS _tipo, int _dirM, tClase clase, int nivel) {
		// TODO Auto-generated constructor stub
		propiedadesTipo = _tipo;
		dirM = new Integer(_dirM);
		this.clase = clase;
		this.nivel = new Integer(nivel);
	}
	
	//Será el método que sustituya al antiguo getTipo()
	public tipoT getT() {
		return propiedadesTipo.getT();
	}
	
	/*public tSintetiz getTipo() {
		return tipo;
	}

	public void setTipo(tSintetiz tipo) {
		this.tipo = tipo;
	}*/

	public Integer getDirM() {
		return dirM;
	}

	public void setDirM(Integer dirM) {
		this.dirM = dirM;
	}
	
	public tClase getClase() {
		return clase;
	}

	public PropsObjTS getPropiedadesTipo() {
		return propiedadesTipo;
	}

	public void setPropiedadesTipo(PropsObjTS propiedadesTipo) {
		this.propiedadesTipo = propiedadesTipo;
	}

	public void setClase(tClase clase) {
		this.clase = clase;
	}
	
	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

}
