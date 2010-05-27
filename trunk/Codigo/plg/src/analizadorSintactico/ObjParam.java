package analizadorSintactico;

import tablaSimbolos.*;

public class ObjParam {
	private Param param;
	private int dir;
	
	public ObjParam(Param param, int dir) {
		this.param = param;
		this.dir = dir;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
}
