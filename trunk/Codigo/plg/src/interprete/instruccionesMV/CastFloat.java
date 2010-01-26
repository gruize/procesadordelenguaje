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

public class CastFloat extends InstruccionMaquinaP{

	public boolean exec(Stack<StackObject> p, Memoria m) {
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
			MyFloat f = new MyFloat();
			Character c = (Character)o.getValue();
			f.setValue(new Float(Character.getNumericValue(c)));
			p.push(f);
			return true;
		}
		if (o instanceof MyInteger){
			p.push(o);
		}
		if (o instanceof MyNatural){
			MyFloat f = new MyFloat();
			Integer i1 = (Integer)o.getValue();
			f.setValue(new Float(i1));
			p.push(f);
			return true;
		}
		if (o instanceof MyFloat){
			p.push(o);
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
		bytes[0] = InstruccionMaquinaP.CASTFLOAT;
		return bytes;
	}
	
	public static CastFloat fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.CASTFLOAT){
			return null;
		}
		return new CastFloat();
	}
	
}
