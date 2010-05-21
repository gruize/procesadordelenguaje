package tablaSimbolos;

public class Array extends PropsObjTS {
	private tipoT t;
	private Integer nElems;
	//private PropsObjTS tBase;
	private tipoT tBase;
	
	public Array(int nElems, tipoT tBase) {
		t = tipoT.array;
		nElems = new Integer(nElems);
		this.tBase = tBase;
	}
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
	public tipoT getTBase() {
		return tBase;
	}
	public void setTBase(tipoT base) {
		tBase = base;
	}
//	public PropsObjTS getTBase() {
//		return tBase;
//	}
//	public void setTBase(PropsObjTS base) {
//		tBase = base;
//	}
}
