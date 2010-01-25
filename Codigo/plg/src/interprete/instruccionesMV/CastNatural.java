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
		
		
		if (o instanceof MyChar){
			MyNatural n = new MyNatural();
			Character c = (Character)o.getValue();
			n.setValue(Character.getNumericValue(c));
			p.push(n);
			return true;
		}
		if (o instanceof MyInteger){
			MyNatural n = new MyNatural();
			Integer i1 = (Integer)o.getValue();
			if (i1 < 0)
				i1 = 0;
			n.setValue(i1);
			p.push(n);
		}
		if (o instanceof MyNatural){

			p.push(o);
			return true;
		}
		if (o instanceof MyFloat){
			MyNatural n = new MyNatural();
			Float f = (Float)o.getValue();
			if (f<0)
				f = new Float(0.0);
			n.setValue(f.intValue());
			p.push(n);
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
		bytes[0] = InstruccionMaquinaP.CASTNAT;
		return bytes;
	}

	@Override
	public InstruccionMaquinaP fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.CASTNAT){
			return null;
		}
		return this;
	}
}
