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

public class Signo extends InstruccionMaquinaP{
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		StackObject o = p.pop();
		if (o instanceof MyBuffer || 
				o instanceof MyBoolean || 
				o instanceof MyNatural || 
				o instanceof MyChar ){ 
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operand"));
			return -1;
		}
		


		/*
		 * MyBoolean is operable with MyBoolean
		 */
		if (o instanceof MyInteger){
			MyInteger i = new MyInteger();
			Integer i1 = (Integer)o.getValue();
			i.setValue(-i1);
			p.push(i);
			return counter+1;
		}
		if (o instanceof MyFloat){
			MyFloat f = new MyFloat();
			Float f1 = (Float)o.getValue();
			f.setValue(-f1);
			p.push(f);
			return counter+1;
		}
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operand"));
		return -1;	
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.SIGNO;
		return bytes;
	}
	public static Signo fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.SIGNO){
			return null;
		}
		return new Signo();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.SIGNO+". cambio de signo";
	}

}

