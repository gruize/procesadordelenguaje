package util;

import interprete.tipos.StackObject;
import java.util.Enumeration;
import java.util.Vector;

public class Memoria {
	private Vector<StackObject> memoria;
	private Integer max;
	private Integer minHeap;
	private Integer maxStatic;
	private Vector<Integer> ocupados_heap;
	public Memoria(){
		memoria=new Vector<StackObject>();
		max = 1000;
		memoria.setSize(max);
		maxStatic = 0;
		minHeap = max;
		ocupados_heap=new Vector<Integer>();
		
	}
	
	public boolean setPosicion(Integer pos,StackObject obj){
		//memoria estática
		if (pos.intValue() < this.minHeap.intValue()){
			// la posición esta entre el máximo estático y el minimo heap, luego incrementamos el max estático
			if (pos.intValue()>this.getMaxStatic().intValue()){
				this.setMaxStatic(pos);
			}
		}
		// en este caso pos pertenece al montículo, por lo que hay que comprobar si existe en la lista de ocupados
		if ( ocupados_heap.contains(pos)== false){
			return false;// intentamos acceder a una posición no reservada
		}
		memoria.add(pos,obj);
		return true;
	}
	
	public boolean delPosicion(Integer pos, Integer tam){
		// recorremos la lista de ocupados comprobando que todas las posiciones existen
		for (int i=0; i<tam.intValue();i++){
			if (ocupados_heap.contains((Integer)pos + i) == false){
				return false;
			}			
		}
		for (int i=0; i<tam.intValue();i++){
			ocupados_heap.remove((Integer)pos + i);
			memoria.remove((Integer)pos + i);
		}// En caso de que se borre la posción minimo del heap se actualiza a la nueva.
		if ( pos.intValue() ==(Integer)this.getMinHeap().intValue()){
			this.setMinHeap((Integer)(pos.intValue()+tam.intValue()));			
		}
		return true;
	}
	
	public Integer newPosicion(Integer tam){
		boolean encontrado= false;
		int contador =0;
		Integer temporal= this.getMax();
		// Lo primero que hacemos es buscar tam posiciones seguidas que esten libres en la lista de ocupados
		for (int i= this.getMax().intValue();i>this.getMinHeap().intValue();i--){
			if (ocupados_heap.contains((Integer)i)== true){
				contador=0;
			}
			else contador ++;
			if (contador == tam.intValue()){
				encontrado=true;
				temporal= (Integer)i;
				break;
			}
		}
		// en el bucles nos ha quedado encontrado que nos indica si se han encontrado "tam" posiciones de memoria seguidas, y en cuyo caso temporal es la posición.
		if (encontrado ==true){
			for (int i =0;i<tam.intValue();i++){
				ocupados_heap.add((Integer)(temporal+i));
			}
			return temporal;
		}
		// Sino se han encontrado hay que mover el minheap ( comprobando que hay espacio) y reservar tam posiciones a partir de él
		else
		{
			if ( this.getMinHeap().intValue()-tam.intValue() > this.getMaxStatic().intValue()){
				for (int i=0; i< tam.intValue(); i++){
					ocupados_heap.add((Integer)(this.getMinHeap().intValue()-i));
				}
				temporal=this.getMinHeap();
				this.setMinHeap(temporal-tam);
				return this.getMinHeap();
			}
			else{
				return -1;
			}
		}		
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
	 * -> Al cambiarlo a vector nos da el tamaño, si en algún sitio se usa esta función va a estar mal
	 */
	
	public Integer size(){
		return memoria.size();
		// Si se refiere a la última pos de la memoria estática devolver maxStatic
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
