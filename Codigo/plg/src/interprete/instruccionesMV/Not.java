package interprete.instruccionesMV;

import interprete.tipos.MyBoolean;
import interprete.tipos.MyBuffer;
import interprete.tipos.MyChar;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyFloat;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Not extends InstruccionMaquinaP {

	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return false;
		}
		StackObject o = p.pop();
		if (o instanceof MyBuffer || 
				o instanceof MyInteger || 
				o instanceof MyNatural || 
				o instanceof MyChar ||
				o instanceof MyFloat){ 
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operand"));
			return false;
		}
		


		/*
		 * MyBoolean is operable with MyBoolean
		 */
		if (o instanceof MyBoolean){
			MyBoolean b = new MyBoolean();
			Boolean b1 = (Boolean)o.getValue();
			b.setValue(!b1);
			p.push(b);
			return true;
		}
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operand"));
		return false;	
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.NOLOGICO;
		return bytes;
	}
	public static Not fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.NOLOGICO){
			return null;
		}
		return new Not();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.NOLOGICO+". not";
	}

}