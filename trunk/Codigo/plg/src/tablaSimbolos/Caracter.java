package tablaSimbolos;

public class Caracter extends PropsObjTS {
	private tipoT t;
	
	public Caracter(){
		t = tipoT.tChar;
	}

	@Override
	public tipoT getT() {
		// TODO Auto-generated method stub
		return t;
	}
}