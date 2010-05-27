package interprete.instruccionesMV;

import java.util.Stack;
import util.Memoria;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

public class Ir_ind extends InstruccionMaquinaP{
	
	
	// Salto incondicional a la dirección que haya en la cima de la pila
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}		
		StackObject o = p.pop();
		if (o instanceof MyNatural || o instanceof MyInteger){
			Integer i = (Integer)o.getValue();
			return i.intValue();
		}
		else {
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Direction is not Integer or Natural"));
			return -1;
		}
	}

	public int size(){
			
			return 1;
		
	}
	
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.IR_IND;
		return bytes;
	}
	public static Ir_ind fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.IR_IND){
			return null;
		}
		return new Ir_ind();
	}
	
	public String toString(){
		return "Code="+InstruccionMaquinaP.IR_IND+". Ir_ind";
	}


}
