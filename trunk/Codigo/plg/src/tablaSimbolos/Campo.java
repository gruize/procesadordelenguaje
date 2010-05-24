package tablaSimbolos;

public class Campo {
	
	private String id;
	private PropsObjTS tipo;
	private Integer desp;
	
	public Campo() {
		//this.id = id;
		//this.tipo = tipo;
		this.desp = new Integer(0);
	}
	
	public Campo(String id, PropsObjTS tipo, Integer desp) {
		this.id = id;
		this.tipo = tipo;
		this.desp = desp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PropsObjTS getTipo() {
		return tipo;
	}

	public void setTipo(PropsObjTS tipo) {
		this.tipo = tipo;
	}

	public Integer getDesp() {
		return desp;
	}

	public void setDesp(Integer desp) {
		this.desp = desp;
	}
	
	
}
