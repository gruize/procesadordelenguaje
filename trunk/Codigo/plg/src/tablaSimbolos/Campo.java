package tablaSimbolos;

public class Campo {
	
	private String id;
	private String tipo;
	private Integer desp;
	
	public Campo(String id, String tipo, Integer desp) {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getDesp() {
		return desp;
	}

	public void setDesp(Integer desp) {
		this.desp = desp;
	}
	
	
}
