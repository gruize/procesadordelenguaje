package tablaSimbolos;

public class Natural extends PropsObjTS {
	private tipoT t;
	
	public Natural(){
		t = tipoT.tNat;
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