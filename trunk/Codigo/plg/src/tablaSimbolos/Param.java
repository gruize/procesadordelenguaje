package tablaSimbolos;

public class Param {
	private tModo modo;
	private PropsObjTS tipo;
	private String id;
	private Integer tam;
	
	public Param() {
		//this.modo = modo;
		//this.tipo = tipo;
		tam = new Integer(0);
	}
	
	public Param(tModo modo, PropsObjTS tipo, String id, int tam) {
		this.modo = modo;
		this.tipo = tipo;
		this.id = id;
		this.tam = new Integer(tam);
	}
	
	public tModo getModo() {
		return modo;
	}
	public void setModo(tModo modo) {
		this.modo = modo;
	}
	public PropsObjTS getTipo() {
		return tipo;
	}
	public void setTipo(PropsObjTS tipo) {
		this.tipo = tipo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Integer getTam() {
		return tam;
	}

	public void setTam(int tam) {
		this.tam = new Integer(tam);
	}
	
}
