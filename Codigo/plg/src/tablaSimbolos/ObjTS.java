package tablaSimbolos;

import analizadorSintactico.*;

public class ObjTS {
	//Objeto que contiene los atributos de una fila de la TS
	
	tSintetiz tipo;
	
	//Esta será la dirección de memoria asociada al objeto "Memoria" dentro de la 
	//estructura de util
	Integer dirM;
	
	
	public ObjTS() {
		// TODO Auto-generated constructor stub
		tipo = tSintetiz.tInicial;
		dirM = new Integer(0);
	}
	
	public tSintetiz getTipo() {
		return tipo;
	}

	public void setTipo(tSintetiz tipo) {
		this.tipo = tipo;
	}

	public Integer getDirM() {
		return dirM;
	}

	public void setDirM(Integer dirM) {
		this.dirM = dirM;
	}

	public ObjTS(tSintetiz _tipo, int _dirM) {
		// TODO Auto-generated constructor stub
		tipo = _tipo;
		dirM = new Integer(_dirM);
	}

}
