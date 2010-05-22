package tablaSimbolos;

public class Referencia extends PropsObjTS {
	private tipoT t;
	private String id;
	
	public Referencia(String tipoRef) {
		t = tipoT.referencia;
		id = tipoRef;
	}
	
	@Override
	public tipoT getT() {
		// TODO Auto-generated method stub
		return t;
	}
	
	public String getTipoRef() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int getTam() {
		// TODO Auto-generated method stub
		return 1;
	}

}
