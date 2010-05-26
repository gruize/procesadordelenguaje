package util;

import interprete.tipos.StackObject;

import java.util.Enumeration;
import java.util.Vector;

public class Memoria {
	private Vector<StackObject> memoria;
	private Integer max;
	private Integer minHeap;
	private Integer maxStatic;
	public Memoria(){
		memoria=new Vector<StackObject>();
		max = 1000;
		maxStatic = 0;
		minHeap = max;
	}
	
	public void setPosicion(Integer pos,StackObject obj){
		memoria.add(pos,obj);
	}
	
	public StackObject getPosicion(Integer pos){
		return memoria.get(pos);
	}
	
	public String toString(){
		return memoria.toString();
	}
	
	/**
     * Esta funcion obtiene el indice del ultimo elemento
	 * almacenado en la memoria.
	 * 
	 * -> Al cambiarlo a vector nos da el tama�o, si en alg�n sitio se usa esta funci�n va a estar mal
	 */
	
	public Integer size(){
		return memoria.size();
		// Si se refiere a la �ltima pos de la memoria est�tica devolver maxStatic
	}

	public Enumeration<StackObject> elements() {
		return memoria.elements();
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMax() {
		return max;
	}

	public void setMinHeap(Integer minHeap) {
		this.minHeap = minHeap;
	}

	public Integer getMinHeap() {
		return minHeap;
	}

	public void setMaxStatic(Integer maxStatic) {
		this.maxStatic = maxStatic;
	}

	public Integer getMaxStatic() {
		return maxStatic;
	}
}
