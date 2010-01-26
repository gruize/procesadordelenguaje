package util;

import interprete.tipos.StackObject;

import java.util.Enumeration;
import java.util.Hashtable;

public class Memoria {
	private Hashtable<Integer,StackObject> memoria;
	
	public Memoria(){
		memoria=new Hashtable<Integer,StackObject>();
	}
	
	public void setPosicion(Integer pos,StackObject obj){
		memoria.put(pos,obj);
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
	 */
	
	public Integer size(){
		return memoria.size();
	}

	public Enumeration<StackObject> elements() {
		return memoria.elements();
	}
}
