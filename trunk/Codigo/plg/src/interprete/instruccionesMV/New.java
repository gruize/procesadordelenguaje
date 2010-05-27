package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class New extends InstruccionMaquinaP{
	private Integer tam;
	public New (){
		tam=null;
	}
	
	public New(int tam){
		this.tam= tam;
	}
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (tam == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null size"));
			return -1;
		}
		//recalculamos el nuevo límite del heap
		Integer pos = m.getMinHeap();
		pos = pos - this.tam;
		//Actualizamos el nuevo limite del heap, en el caso que no se solapen el heap y el stack
		//Y también procedemos a realizar el comportamiento correcto
		if( pos.intValue()>= m.getMaxStatic().intValue()){
			m.setMinHeap(pos);
			//Nos creamos el objeto que se mete en la pila, que nos da la dirección dónde empieza
			StackObject orig = new MyInteger();
			orig.setValue(pos);
			p.push(orig);
			return counter+1;		
		}
		else {
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Stack overflow"));
			return -1;
		}
	}
	
	public int size(){
		if (tam == null)
			return 1;
		MyNatural n = new MyNatural();
		return 1+n.size();
	}
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.NEW;
		if (tam == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(tam);		
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}

	public static New fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.NEW){
			return null;
		}
		New i = new New();
		i.tam = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i; 
	}
	
	public String toString(){
		return "Code="+InstruccionMaquinaP.NEW+". NEW";
	}

}
