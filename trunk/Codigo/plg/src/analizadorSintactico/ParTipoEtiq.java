package analizadorSintactico;

import tablaSimbolos.*;

public class ParTipoEtiq {

	//private tipoT tipo;
	private PropsObjTS tipo;
	private int etiq;
	
	public ParTipoEtiq() {
		this.tipo = new ErrorT();
		this.etiq = 0;
	}
	
	public ParTipoEtiq(PropsObjTS tipo, int etiq) {
		this.tipo = tipo;
		this.etiq = etiq;
	}
	
	public PropsObjTS getTipo() {
		return tipo;
	}
	
	public void setTipo(PropsObjTS tipo) {
		this.tipo = tipo;
	}
	
	public int getEtiq() {
		return etiq;
	}
	
	public void setEtiq(int etiq) {
		this.etiq = etiq;
	}
}
