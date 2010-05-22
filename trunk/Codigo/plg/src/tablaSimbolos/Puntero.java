package tablaSimbolos;

public class Puntero extends PropsObjTS {
	private tipoT t;
	private PropsObjTS tBase;
	private Integer tam;
	
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
