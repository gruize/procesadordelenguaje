package tablaSimbolos;

public class Entero extends PropsObjTS {
	private tipoT t;
	
	public Entero(){
		t = tipoT.tInt;
	}

	@Override
	public tipoT getT() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public int getTam() {
		// TODO Auto-generated method stub
		return 1;
	}
}