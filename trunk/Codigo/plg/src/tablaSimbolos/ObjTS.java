package tablaSimbolos;

import analizadorSintactico.*;

public class ObjTS {
	//Objeto que contiene los atributos de una fila de la TS
	//tSintetiz tipo;
	//Se quita el tipo de aqu� porque ir� en el objeto de las propiedades
	
	//Esta ser� la direcci�n de memoria asociada al objeto "Memoria" dentro de la 
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

	public void setClase(tClase clase) {
		this.clase = clase;
	}
	
	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	public ObjTS(tSintetiz _tipo, int _dirM) {
		// TODO Auto-generated constructor stub
		//tipo = _tipo;
		dirM = new Integer(_dirM);
	}

}