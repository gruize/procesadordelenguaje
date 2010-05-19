package analizadorSintactico;

import tablaSimbolos.*;

public class ParTipoEtiq {

	private tipoT tipo;
	private int etiq;
	
	public ParTipoEtiq() {
		this.tipo = tipoT.tInicial;
		this.etiq = 0;
	}
	
	public ParTipoEtiq(tipoT tipo, int etiq) {
		this.tipo = tipo;
		this.etiq = etiq;
	}
	
	public tipoT getT() {
		return tipo;
	}
	
	public void setTipo(tipoT tipo) {
		this.tipo = tipo;
	}
	
	public int getEtiq() {
		return etiq;
	}
	
	public void setEtiq(int etiq) {
		this.etiq = etiq;
	}
}
