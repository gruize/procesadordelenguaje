package tablaSimbolos;

import java.util.*;

public class Procedimiento extends PropsObjTS {
	private tipoT t;
	private Vector<Param> lParams;
	//private tipoT tBase;
	private Integer tam;
	//Debe haber tama�o en los procedimientos??
	public Procedimiento() {
		t = tipoT.procedimiento;
		lParams = new Vector<Param>();
		tam = new Integer(0);
	}
	
	@Override
	public tipoT getT() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public int getTam() {
		// TODO Auto-generated method stub
		return tam.intValue();
	}

	public Vector<Param> getLParams() {
		return lParams;
	}

	public void setLParams(Vector<Param> params) {
		lParams = params;
	}

	public void a�adeParam(tModo modo, PropsObjTS tipo, String id, int tam) {
		lParams.add(new Param(modo, tipo, id, tam));
		//Esto es as�??
		this.tam =+ tam;
	}
	
	public void a�adeParam(Param param) {
		lParams.add(param);
		//Esto es as�??
		this.tam =+ param.getTam();
	}
}
