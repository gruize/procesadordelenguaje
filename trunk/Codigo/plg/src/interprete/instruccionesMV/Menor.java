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

public class Menor extends InstruccionMaquinaP{





	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (p.size() < 2){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Not enough elements"));
			return -1;
		}
		StackObject o1 = p.pop();
		StackObject o2 = p.pop();
		if (o1 instanceof MyBoolean || o2 instanceof MyBoolean ||
				o1 instanceof MyBuffer || o2 instanceof MyBuffer){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
			return -1;
		}
		MyBoolean e = new MyBoolean();
		/*
		 * Char is comparable with char
		 */
		if (o1 instanceof MyChar)
			if (o2 instanceof MyChar){
				
				Character c1 = (Character)o1.getValue();
				Character c2 = (Character)o2.getValue();
				e.setValue(c1.compareTo(c2) < 0);
				p.push(e);
				return counter+1;
			}
		/*
		 * My Float is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyFloat){
			if (o2 instanceof MyFloat){
				Float v1 = (Float)o1.getValue();
				Float v2 = (Float)o2.getValue();
				e.setValue(v1.compareTo(v2) < 0);
				p.push(e);
				return counter+1;
			}
			if (o2 instanceof MyInteger){
				Float v1 = (Float)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo(new Float(v2)) < 0);
				p.push(e);

				return counter+1;
			}
			if (o2 instanceof MyNatural){
				Float v1 = (Float)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo(new Float(v2)) < 0);
				p.push(e);
				return counter+1;
			}
		}

		/*
		 * MyInteger is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyInteger){
			if (o2 instanceof MyFloat){
				Integer v1 = (Integer)o1.getValue();
				Float v2 = (Float)o2.getValue();
				e.setValue(v1.compareTo(v2.intValue()) < 0);
				p.push(e);
				return counter+1;
			}
			if (o2 instanceof MyInteger){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) < 0);
				p.push(e);
				return counter+1;
			}
			if (o2 instanceof MyNatural){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) < 0);
				p.push(e);
				return counter+1;
			}
		}
		/*
		 * MyNatural is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyNatural){
			if (o2 instanceof MyFloat){
				Integer v1 = (Integer)o1.getValue();
				Float v2 = (Float)o2.getValue();
				e.setValue(v1.compareTo(v2.intValue()) < 0);
				p.push(e);
				return counter+1;
			}
			if (o2 instanceof MyInteger){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) < 0);
				p.push(e);
				return counter+1;
			}
			if (o2 instanceof MyNatural){
				Integer v1 = (Integer)o1.getValue();
				Integer v2 = (Integer)o2.getValue();
				e.setValue(v1.compareTo((v2)) < 0);
				p.push(e);
				return counter+1;
			}
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
		bytes[0] = InstruccionMaquinaP.MENOR;
		return bytes;
	}

	public static Menor fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.MENOR){
			return null;
		}
		return new Menor();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.MENOR+". menor";
	}


}
