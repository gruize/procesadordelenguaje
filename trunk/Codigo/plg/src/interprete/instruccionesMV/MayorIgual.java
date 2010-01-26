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

public class MayorIgual extends InstruccionMaquinaP{

	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
		if (p.size() < 2){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Not enough elements"));
			return false;
		}
		StackObject o1 = p.pop();
		StackObject o2 = p.pop();
		if (o1 instanceof MyBoolean || o2 instanceof MyBoolean ||
				o1 instanceof MyBuffer || o2 instanceof MyBuffer){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
			return false;
		}
		
		MyBoolean e = new MyBoolean();
		/*
		 * Char is comparable with char
		 */
		if (o1 instanceof MyChar)
			if (o2 instanceof MyChar){
				
				Character c1 = (Character)o1.getValue();
				Character c2 = (Character)o2.getValue();
				e.setValue(c1.compareTo(c2) >= 0);
				p.push(e);
				return true;
			}
		/*
		 * My Float is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyFloat){
			if (o2 instanceof MyFloat){
				Float v1 = (Float)o1.getValue();
				Float v2 = (Float)o2.getValue();
				e.setValue(v1.compareTo(v2) >= 0);
				p.push(e);
				return true;
			}
			if (o2 instanceof MyInteger){
				Float v1 = (Float)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo(new Float(v2)) >= 0);
				p.push(e);

				return true;
			}
			if (o2 instanceof MyNatural){
				Float v1 = (Float)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo(new Float(v2)) >= 0);
				p.push(e);
				return true;
			}
		}

		/*
		 * MyInteger is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyInteger){
			if (o2 instanceof MyFloat){
				Integer v1 = (Integer)o1.getValue();
				Float v2 = (Float)o2.getValue();
				e.setValue(v1.compareTo(v2.intValue()) >= 0);
				p.push(e);

				return true;
			}
			if (o2 instanceof MyInteger){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) >= 0);
				p.push(e);
				return true;
			}
			if (o2 instanceof MyNatural){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) >= 0);
				p.push(e);
				return true;
			}
		}
		/*
		 * MyNatural is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyNatural){
			if (o2 instanceof MyFloat){
				Integer v1 = (Integer)o1.getValue();
				Float v2 = (Float)o2.getValue();
				e.setValue(v1.compareTo(v2.intValue()) > 0);
				p.push(e);
				return true;
			}
			if (o2 instanceof MyInteger){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) > 0);
				p.push(e);
				return true;
			}
			if (o2 instanceof MyNatural){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) > 0);
				p.push(e);
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
		bytes[0] = InstruccionMaquinaP.MAYORIGUAL;
		return bytes;
	}
	public static MayorIgual fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.MAYORIGUAL){
			return null;
		}
		return new MayorIgual();
	}

}

