package tablaSimbolos;

public class Booleano extends PropsObjTS {
	private tipoT t;
	
	public Booleano(){
		t = tipoT.tBool;
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
