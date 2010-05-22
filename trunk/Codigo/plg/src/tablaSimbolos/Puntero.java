package tablaSimbolos;

public class Puntero extends PropsObjTS {
	private tipoT t;
	private PropsObjTS tBase;
	private Integer tam;
	//Debe haber tamaño en los procedimientos??
	public Puntero(PropsObjTS tBase) {
		t = tipoT.puntero;
		this.tBase = tBase;
		this.tam = new Integer(1);
	}
	
	public tipoT getT() {
		return t;
	}
	public void setT(tipoT t) {
		this.t = t;
	}
	public PropsObjTS getTBase() {
		return tBase;
	}
	public void setTBase(PropsObjTS base) {
		tBase = base;
	}
	public int getTam() {
		return tam.intValue();
	}
	public void setTam(Integer tam) {
		this.tam = tam;
	}
}
