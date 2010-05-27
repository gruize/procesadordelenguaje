package tablaSimbolos;

public class Null extends PropsObjTS {
	private tipoT t;
	
	public Null(){
		t = tipoT.tNull;
	}

	@Override
	public tipoT getT() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public int getTam() {
		// TODO Auto-generated method stub
		return 0;
	}
}
