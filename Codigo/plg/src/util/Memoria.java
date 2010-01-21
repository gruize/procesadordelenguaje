package util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Memoria {
	private Hashtable<Integer,Object> memoria;
	
	public Memoria(){
		memoria=new Hashtable<Integer,Object>();
	}
	
	public void setPosicion(int pos,Object obj){
		memoria.put(pos,obj);
	}
	
	public Object getPosicion(int pos){
		return memoria.get(pos);
	}
	
	public String toString(){
		return memoria.toString();
	}
	
	/**
     * Esta funcion obtiene el indice del ultimo elemento
	 * almacenado en la memoria.
	 */
	
	public Integer getCima(){
		Set<Integer> set = memoria.keySet();
	    Iterator<Integer> itr = set.iterator();
	    Integer inte;
	    Integer max =0;
	    while (itr.hasNext()) {
	    	inte = itr.next();
	    	if (inte > max )
	    		max = inte;
	    }
	    return max;
	}
	
	public int posicionMax(){
		return 1000;
	}
}
