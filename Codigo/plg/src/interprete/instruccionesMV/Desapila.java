package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Desapila extends InstruccionMaquinaP{

	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return false;
		}
		p.pop();
		return true;
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
