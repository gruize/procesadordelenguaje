package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Desapila extends InstruccionMaquinaP{

	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {

		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		p.pop();
		return counter+1;
	}
	@Override
	public int size(){
		return 1;
		
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.DESAPILA;
		return bytes;
	}
	
	public static Desapila fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DESAPILA){
			return null;
		}
		return new Desapila();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.DESAPILA+". desapila";
	}


}
