package analizadorSintactico;

import tablaSimbolos.*;

public class ObjProc {
	private Procedimiento proc;
	private int dir;
	private TS tsP;
	
	public ObjProc(Procedimiento proc, int dir, TS tsP) {
		this.proc = proc;
		this.dir = dir;
		this.tsP = tsP;
	}

	public Procedimiento getProc() {
		return proc;
	}

	public void setProc(Procedimiento proc) {
		this.proc = proc;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public TS getTsP() {
		return tsP;
	}

	public void setTsP(TS tsP) {
		this.tsP = tsP;
	}
	
	
}
