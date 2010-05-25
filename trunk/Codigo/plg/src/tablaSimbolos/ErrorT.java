package tablaSimbolos;

public class ErrorT extends PropsObjTS{
	private tipoT t;
	
	public ErrorT() {
		t = tipoT.tError;
	}
	
	public tipoT getT() {
		return t;
	}

	@Override
	public int getTam() {
		// TODO Auto-generated method stub
		return 0;
	}
}
