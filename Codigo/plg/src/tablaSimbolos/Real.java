package tablaSimbolos;

public class Real extends PropsObjTS {
	private tipoT t;
	
	public Real(){
		t = tipoT.tFloat;
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