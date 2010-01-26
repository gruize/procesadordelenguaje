package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Escribir extends InstruccionMaquinaP {


	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR, "Empty stack"));
			return -1;
		}
		Object o = p.pop();
		System.out.println(o);
		return counter+1;
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
	
	public static Escribir fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.ESCRIBIR){
			return null;
		}
		return new Escribir();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.ESCRIBIR+". escribir";
	}


}
