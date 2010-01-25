package util;

import interprete.tipos.StackObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

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
}
