package tablaSimbolos;

public class Param {
	private tModo modo;
	private PropsObjTS tipo;
	
	public Param(tModo modo, PropsObjTS tipo) {
		this.modo = modo;
		this.tipo = tipo;
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
}
