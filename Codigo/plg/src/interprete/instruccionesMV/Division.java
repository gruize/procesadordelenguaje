package interprete.instruccionesMV;

import interprete.tipos.MyBoolean;
import interprete.tipos.MyBuffer;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyFloat;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Division extends InstruccionMaquinaP{

	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {

		if (p.size() < 2){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Not enough elements"));
			return -1;
		}
		StackObject o1 = p.pop();
		StackObject o2 = p.pop();
		if (o1 instanceof MyBuffer || o2 instanceof MyBuffer || o1 instanceof MyBoolean || o2 instanceof MyBoolean){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
			return -1;
		}
		

		/*
		 * My Float is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyFloat){
			if (o2 instanceof MyFloat){
				MyFloat f = new MyFloat();
				Float f1 = (Float)o1.getValue();
				Float f2 = (Float)o2.getValue();
				f.setValue(f1/f2);
				p.push(f);
				return counter+1;
			}
			if (o2 instanceof MyInteger){
				MyFloat f = new MyFloat();
				Float f1 = (Float)o1.getValue();
				Integer i2 = (Integer)o2.getValue();
				f.setValue(f1/i2);
				p.push(f);
				return counter+1;
			}
			if (o2 instanceof MyNatural){
				MyFloat f = new MyFloat();
				Float f1 = (Float)o1.getValue();
				Integer n2 = (Integer)o2.getValue();
				f.setValue(f1/n2);
				p.push(f);
				return counter+1;
			}
		}

		/*
		 * MyInteger is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyInteger){
			if (o2 instanceof MyFloat){
				MyFloat f = new MyFloat();
				Integer i1 = (Integer)o1.getValue();
				Float f2 = (Float)o2.getValue();
				f.setValue(i1/f2);
				p.push(f);
				return counter+1;
			}
			if (o2 instanceof MyInteger){
				MyInteger i = new MyInteger();
				Integer i1 = (Integer)o1.getValue();
				Integer i2 = (Integer)o2.getValue();
				i.setValue(i1/i2);
				p.push(i);
				return counter+1;
			}
			if (o2 instanceof MyNatural){
				MyInteger i = new MyInteger();
				Integer i1 = (Integer)o1.getValue();
				Integer n2 = (Integer)o2.getValue();
				i.setValue(i1/n2);
				p.push(i);
				return counter+1;
			}
		}
		/*
		 * MyNatural is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyNatural){
			if (o2 instanceof MyFloat){
				MyFloat f = new MyFloat();
				Integer n1 = (Integer)o1.getValue();
				Float f2 = (Float)o2.getValue();
				f.setValue(n1/f2);
				p.push(f);
				return counter+1;
			}
			if (o2 instanceof MyInteger){
				MyInteger i = new MyInteger();
				Integer n1 = (Integer)o1.getValue();
				Integer i2 = (Integer)o2.getValue();
				i.setValue(n1/i2);
				p.push(i);
				return counter+1;
			}
			if (o2 instanceof MyNatural){
				MyNatural n = new MyNatural();
				Integer n1 = (Integer)o1.getValue();
				Integer n2 = (Integer)o2.getValue();
				n.setValue(n1/n2);
				p.push(n);
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
		bytes[0] = InstruccionMaquinaP.DIVISION;
		return bytes;
	}
	
	public static Division fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DIVISION){
			return null;
		}
		return new Division();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.DIVISION+". division";
	}

}
