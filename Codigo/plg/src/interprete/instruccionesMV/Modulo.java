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

public class Modulo extends InstruccionMaquinaP {

	public boolean exec(Stack<StackObject> p, Memoria m) {
		if (p.size() < 2){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Not enough elements"));
			return false;
		}
		StackObject o1 = p.pop();
		StackObject o2 = p.pop();
		if (o1 instanceof MyBuffer || o2 instanceof MyBuffer || 
				o1 instanceof MyBoolean || o2 instanceof MyBoolean ||
				o1 instanceof MyChar || o2 instanceof MyChar||
				o1 instanceof MyFloat || o2 instanceof MyFloat ){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
			return false;
		}
		


		/*
		 * MyInteger is operable with MyInteger and MyNatural
		 */
		if (o1 instanceof MyInteger){
			if (o2 instanceof MyInteger){
				MyNatural n = new MyNatural();
				Integer i1 = (Integer)o1.getValue();
				Integer i2 = (Integer)o2.getValue();
				n.setValue(i1%i2);
				p.push(n);
				return true;
			}
			if (o2 instanceof MyNatural){
				MyNatural n = new MyNatural();
				Integer i1 = (Integer)o1.getValue();
				Integer n2 = (Integer)o2.getValue();
				n.setValue(i1%n2);
				p.push(n);


				return true;
			}
		}
		/*
		 * MyNatural is comparable with MyInteger MyFloat and MyNatural
		 */
		if (o1 instanceof MyNatural){
			if (o2 instanceof MyInteger){
				MyNatural n = new MyNatural();
				Integer n1 = (Integer)o1.getValue();
				Integer i2 = (Integer)o2.getValue();
				n.setValue(n1%i2);
				p.push(n);
				return true;
			}
			if (o2 instanceof MyNatural){
				MyNatural n = new MyNatural();
				Integer n1 = (Integer)o1.getValue();
				Integer n2 = (Integer)o2.getValue();
				n.setValue(n1%n2);
				p.push(n);
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
		bytes[0] = InstruccionMaquinaP.MODULO;
		return bytes;
	}
	public static Modulo fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.MODULO){
			return null;
		}
		return new Modulo();
	}
}



