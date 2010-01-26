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

public class CastInteger extends InstruccionMaquinaP{

	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return false;
		}
		StackObject o = p.pop();
		if (o instanceof MyBuffer || 
				o instanceof MyBoolean ){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
			return false;
		}
		
		/*
		 * Char is comparable with char
		 */
		if (o instanceof MyChar){
			MyInteger i = new MyInteger();
			Character c = (Character)o.getValue();
			i.setValue(Character.getNumericValue(c));
			p.push(i);
			return true;
		}
		if (o instanceof MyInteger){
			p.push(o);
		}
		if (o instanceof MyNatural){
			MyInteger i = new MyInteger();
			Integer i1 = (Integer)o.getValue();
			i.setValue(i1);
			p.push(i);
			return true;
		}
		if (o instanceof MyFloat){
			MyInteger i = new MyInteger();
			Float f = (Float)o.getValue();
			i.setValue(f.intValue());
			p.push(i);
			return true;
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
		bytes[0] = InstruccionMaquinaP.CASTINT;
		return bytes;
	}
	public static CastInteger fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.CASTFLOAT){
			return null;
		}
		return new CastInteger();
	}
}

