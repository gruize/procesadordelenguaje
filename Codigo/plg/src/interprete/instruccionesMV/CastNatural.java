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

public class CastNatural extends InstruccionMaquinaP{

	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		StackObject o = p.pop();
		if (o instanceof MyBuffer || 
				o instanceof MyBoolean ){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
			return -1;
		}
		
		
		if (o instanceof MyChar){
			MyNatural n = new MyNatural();
			Character c = (Character)o.getValue();
			n.setValue(Character.getNumericValue(c));
			p.push(n);
			return counter+1;
		}
		if (o instanceof MyInteger){
			MyNatural n = new MyNatural();
			Integer i1 = (Integer)o.getValue();
			if (i1 < 0)
				i1 = 0;
			n.setValue(i1);
			p.push(n);
			return counter+1;
		}
		if (o instanceof MyNatural){

			p.push(o);
			return counter+1;
		}
		if (o instanceof MyFloat){
			MyNatural n = new MyNatural();
			Float f = (Float)o.getValue();
			if (f<0)
				f = new Float(0.0);
			n.setValue(f.intValue());
			p.push(n);
			return counter+1;
		}

		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
		return -1;
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.CASTNAT;
		return bytes;
	}

	
	public static CastInteger fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.CASTNAT){
			return null;
		}
		return new CastInteger();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.CASTNAT+". (natural)";
	}

}
