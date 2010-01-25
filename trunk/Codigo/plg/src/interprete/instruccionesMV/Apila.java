package interprete.instruccionesMV;

import interprete.tipos.MyBoolean;
import interprete.tipos.MyChar;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyFloat;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Apila extends InstruccionMaquinaP{
	private StackObject element;
	public Apila(){
		element = null;
	}
	public Apila(StackObject e){
		this.element = e;
	}
	public boolean exec(Stack<StackObject> p, Memoria m) {
		if (element == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Element is null"));
			return false;
		}
		p.push(element);
		return true;
	}
	@Override
	public int size(){
		if (element == null)
			return 1+1;
		return 1+1+element.size();
		
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.APILA;
		if (element == null){
			bytes[pos] = StackObject.INDETERMINATED;
			return bytes;
		}
		bytes[pos++] = element.type();
		System.arraycopy(element.toBytes(), 0,bytes, pos, element.size());
		return bytes;
	}
	@Override
	public InstruccionMaquinaP fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.APILA){
			return null;
		}
		switch (bytes[pos++]) {
		case StackObject.MY_BOOLEAN:
			element = new MyBoolean().fromBytes(bytes, pos);
			return this;
		case StackObject.MY_BUFFER:
			// My buffer can't be apiled from this funcion
			return null;
		case StackObject.MY_CHAR:
			element = new MyChar().fromBytes(bytes, pos);
			return this;
		case StackObject.MY_EXECUTIONERROR:
			// My Execution error can't be apiled from this funcion
			return null;
		case StackObject.MY_FLOAT:
			element = new MyFloat().fromBytes(bytes, pos);
			return this;
		case StackObject.MY_INTEGER:
			element = new MyInteger().fromBytes(bytes, pos);
			return this;
		case StackObject.MY_NATURAL:
			element = new MyNatural().fromBytes(bytes, pos);
			return this;
			

		default:
			return null;
		}
		
	}

	
}
