package analizadorSintactico;

import tablaSimbolos.*;

public class ParString {
	
	private String iden;
	private tipoT tipo;

	public ParString() {
		// TODO Auto-generated constructor stub
		iden = new String();
		tipo = tipoT.tInicial;
	}
	
	public ParString(String _iden, tipoT _tipo) {
		// TODO Auto-generated constructor stub
		iden = new String(_iden);
		tipo = _tipo;
	}
	
	public String getIden() {
		return iden;
	}

	public void setIden(String _iden) {
		iden = new String(_iden);
	}

	public tipoT getTipo() {
		return tipo;
	}

	public void setTipo(tipoT _tipo) {
		tipo = _tipo;
	}

}
