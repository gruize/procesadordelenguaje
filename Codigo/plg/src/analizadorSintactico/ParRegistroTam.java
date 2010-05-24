package analizadorSintactico;

import tablaSimbolos.*;

public class ParRegistroTam {
	private Registro reg;
	private int tam;
	
	public ParRegistroTam(Registro reg, int tam) {
		this.reg = reg;
		this.tam = tam;
	}

	public Registro getReg() {
		return reg;
	}

	public void setReg(Registro reg) {
		this.reg = reg;
	}

	public int getTam() {
		return tam;
	}

	public void setTam(int tam) {
		this.tam = tam;
	}
}
