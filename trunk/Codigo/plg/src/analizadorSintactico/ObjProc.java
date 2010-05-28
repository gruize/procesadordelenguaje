package analizadorSintactico;

import tablaSimbolos.*;

public class ObjProc {
	private Procedimiento proc;
	private int dir;
	private TS tsP;
	private int etiq;
	private boolean forward;
	
	public ObjProc(Procedimiento proc, int dir, TS tsP, int etiq) {
		this.proc = proc;
		this.dir = dir;
		this.tsP = tsP;
		this.etiq = etiq;
		this.forward = false;
	}
	
	public ObjProc(Procedimiento proc, int dir, TS tsP, int etiq, boolean forward) {
		this.proc = proc;
		this.dir = dir;
		this.tsP = tsP;
		this.etiq = etiq;
		this.forward = forward;
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

	public int getEtiq() {
		return etiq;
	}

	public void setEtiq(int etiq) {
		this.etiq = etiq;
	}

	public boolean isForward() {
		return forward;
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}
	
}
