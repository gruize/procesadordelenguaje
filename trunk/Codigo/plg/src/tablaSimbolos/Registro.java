package tablaSimbolos;
import java.util.*;

public class Registro extends PropsObjTS{
	private tipoT t;
	private Vector<Campo> campos;
	private Integer tam;
	
	//Se crea el registro vac�o y luego se van insertando los campos
	public Registro() {
		t = tipoT.registro;
		campos = new Vector<Campo>();
		tam = new Integer(0);
	}
	
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
	public int getTam() {
		return tam.intValue();
	}
	public void setTam(Integer tam) {
		this.tam = tam;
	}
	
	//A�adir un campo al registro
	public void a�adeCampo(Campo campo) {
		campos.add(campo);
		//Esto es as�??
		tam = tam + campo.getDesp();
	}
	
	//A�adir un campo al registro
	public void a�adeCampo(String id, PropsObjTS tipo, int desp) {
		campos.add(new Campo(id, tipo, new Integer(desp)));
		//Esto es as�??
		tam = tam + desp;
	}
	
	//M�todos de acceso a los campos del registro
	public int existeCampo(String id) {
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && (i < campos.size())) {
			if (campos.get(i).getId().equals(id))
				encontrado = true;
			else
				i++;
		}
		if (i==campos.size())
			return -1;
		else
			return i;
	}
	
	public Campo getCampo(String id) {
		int campoI = existeCampo(id);
		if (campoI != -1)
			return campos.get(campoI);
		else {
			System.out.print("Error: El campo '" + id + "' no existe en su registro.");
			return null;	
		}
	}
	
	public Campo getCampoI(int i) {
		if ((0 <= i) && (i < campos.size()))
			return campos.get(i);
		else {
			System.out.print("Error: El campo de �ndice " + i + " supera el m�ximo de la lista asociada su registro.");
			return null;	
		}
	}
}