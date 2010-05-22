package tablaSimbolos;

public class Array extends PropsObjTS {
	private tipoT t;
	private Integer nElems;
	private PropsObjTS tBase;
	//private tipoT tBase;
	private Integer tam;
	
	
	public Array(int nElems, PropsObjTS tBase) {
		t = tipoT.array;
		this.nElems = new Integer(nElems);
		this.tBase = tBase;
		this.tam = nElems * tBase.getTam();
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
	public PropsObjTS getTBase() {
		return tBase;
	}
	public void setTBase(PropsObjTS base) {
		tBase = base;
	}
	public void setTam(Integer tam) {
		this.tam = tam;
	}
	public int getTam() {
		return tam.intValue();
	}
}
