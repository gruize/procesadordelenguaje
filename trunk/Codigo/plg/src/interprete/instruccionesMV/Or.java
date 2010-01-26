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

public class Or extends InstruccionMaquinaP {

	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
		if (p.size() < 2){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Not enough elements"));
			return false;
		}
		StackObject o1 = p.pop();
		StackObject o2 = p.pop();
		if (o1 instanceof MyBuffer || o2 instanceof MyBuffer || 
				o1 instanceof MyInteger || o2 instanceof MyInteger ||
				o1 instanceof MyNatural || o2 instanceof MyNatural ||
				o1 instanceof MyChar || o2 instanceof MyChar ||
				o1 instanceof MyFloat || o2 instanceof MyFloat ){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
			return false;
		}
		


		/*
		 * MyBoolean is operable with MyBoolean
		 */
		if (o1 instanceof MyBoolean){
			if (o2 instanceof MyBoolean){
				MyBoolean b = new MyBoolean();
				Boolean b1 = (Boolean)o1.getValue();
				Boolean b2 = (Boolean)o2.getValue();
				b.setValue(b1 || b2);
				p.push(b);
				return true;
			}
		}
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
		return false;	
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.OLOGICO;
		return bytes;
	}
	public static Or fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.OLOGICO){
			return null;
		}
		return new Or();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.OLOGICO+". or";
	}

}