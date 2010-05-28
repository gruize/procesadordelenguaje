package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Del extends InstruccionMaquinaP{
	private Integer tam;
	public Del (){
		tam=null;
	}
	
	public Del(int tam){
		this.tam= tam;
	}
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		
		if (tam == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null size"));
			return -1;
		}
		
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		// La cima de la pila tiene la dirección a la que queremos acceder
		StackObject o = p.pop();
		Integer dir = (Integer) o.getValue();
		// Se mira que la dirección pertenezca al heap 
		if (m.delPosicion(dir, tam)==false){
			return-1;
		}
		else return counter+1;
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
		bytes[pos++] = InstruccionMaquinaP.DEL;
		if (tam == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(tam);		
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}

	public static Del fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DEL){
			return null;
		}
		Del i = new Del();
		i.tam = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i; 
	}
	
	public String toString(){
		return "Code="+InstruccionMaquinaP.DEL+". Del";
	}

}
