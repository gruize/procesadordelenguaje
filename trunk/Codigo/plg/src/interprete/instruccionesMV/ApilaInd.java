package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;




public class ApilaInd extends InstruccionMaquinaP{
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		// La cima de la pila tiene la dirección a la que queremos acceder
		StackObject o = p.pop();
		Integer dir = (Integer) o.getValue();
		// Se mira que no este vacía esa posición de memoria
		if (m.getPosicion(dir) == null){
			p.push(new MyExecutionError(MyExecutionError.MEMORY_ERROR,"Violation Memory"));
			return -1;
		}
		//Se añade y se actualiza el contador de programa
		p.push(m.getPosicion(dir));
		return counter+1;
	}
	@Override
	public int size(){
		
			return 1;
		
	}
	
	
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.APILA_IND;
		return bytes;
	}
	
	public static ApilaInd fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.APILA_IND){
			return null;
		}
		return new ApilaInd();
	}
	
	@Override
	public String toString(){
		return "Code="+InstruccionMaquinaP.APILA_IND+". Apila_ind";
	}


	
}

