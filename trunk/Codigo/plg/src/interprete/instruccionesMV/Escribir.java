package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Escribir extends InstruccionMaquinaP {


	public boolean exec(Stack<StackObject> p, Memoria m) {
		// TODO Auto-generated method stub
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR, "Empty stack"));
			return false;
		}
		Object o = p.pop();
		System.out.println(o);
		return true;
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.ESCRIBIR;
		return bytes;
	}
	@Override
	public InstruccionMaquinaP fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.ESCRIBIR){
			return null;
		}
		return this;
	}

}
