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

public class CastChar extends InstruccionMaquinaP{

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
		
		/*
		 * Char is comparable with char
		 */
		if (o instanceof MyChar){
			p.push(o);
			return counter+1;
		}
		if (o instanceof MyInteger){
			MyChar c = new MyChar();
			char c1 = (char)((Integer)o.getValue()).intValue();
			c.setValue(c1);
			p.push(c);
			return counter+1;
		}
		if (o instanceof MyNatural){
			MyChar c = new MyChar();
			char c1 = (char)((Integer)o.getValue()).intValue();
			c.setValue(c1);
			p.push(c);
			return counter+1;
		}
		if (o instanceof MyFloat){
			MyChar c = new MyChar();
			char c1 = (char)((Float)o.getValue()).intValue();
			c.setValue(c1);
			p.push(c);
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
		bytes[0] = InstruccionMaquinaP.YLOGICO;
		return bytes;
	}

	public static CastChar fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.CASTCHAR){
			return null;
		}
		return new CastChar();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.CASTCHAR+". (Char)";
	}


}
