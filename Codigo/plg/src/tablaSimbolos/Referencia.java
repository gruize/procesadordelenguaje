package tablaSimbolos;

public class Referencia extends PropsObjTS {
	private tipoT t;
	private String id;
	private Integer tam;
	
	public Referencia(String tipoRef, int tam) {
		t = tipoT.referencia;
		id = tipoRef;
		this.tam = new Integer(tam);
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
		return tam;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setT(tipoT t) {
		this.t = t;
	}

	public void setTam(Integer tam) {
		this.tam = tam;
	}

}
