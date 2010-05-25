package analizadorSintactico;

import tablaSimbolos.*;

public class ParProps {
	
	PropsObjTS tipo1;
	PropsObjTS tipo2;
	
	public ParProps(PropsObjTS tipo1, PropsObjTS tipo2) {
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
	}
	
	public PropsObjTS getTipo1() {
		return tipo1;
	}
	public void setTipo1(PropsObjTS tipo1) {
		this.tipo1 = tipo1;
	}
	public PropsObjTS getTipo2() {
		return tipo2;
	}
	public void setTipo2(PropsObjTS tipo2) {
		this.tipo2 = tipo2;
	}
}
