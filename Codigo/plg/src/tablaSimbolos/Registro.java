package tablaSimbolos;
import java.util.*;

public class Registro extends PropsObjTS{
	private tipoT t;
	private Vector<Campo> campos;
	private Integer tam;
	
	public tipoT getT() {
		return t;
	}
	public void setT(tipoT t) {
		this.t = t;
	}
	public Vector<Campo> getCampos() {
		return campos;
	}
	public void setCampos(Vector<Campo> campos) {
		this.campos = campos;
	}
	public Integer getTam() {
		return tam;
	}
	public void setTam(Integer tam) {
		this.tam = tam;
	}
}