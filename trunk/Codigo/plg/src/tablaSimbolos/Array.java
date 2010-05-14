package tablaSimbolos;

public class Array extends PropsObjTS {
	private tipoT t;
	private Integer nElems;
	private PropsObjTS tBase;
	
	public tipoT getT() {
		return t;
	}
	public void setT(tipoT t) {
		this.t = t;
	}
	public Integer getNElems() {
		return nElems;
	}
	public void setNElems(Integer elems) {
		nElems = elems;
	}
	public PropsObjTS getTBase() {
		return tBase;
	}
	public void setTBase(PropsObjTS base) {
		tBase = base;
	}
}
