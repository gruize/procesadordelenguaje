package interprete.instruccionesMV;

import java.util.Stack;
import util.Memoria;
import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

public class Copia extends InstruccionMaquinaP{
	
	
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		StackObject cop = p.pop().duplica();
		p.push(cop);
		StackObject cop2 = cop.duplica();
		p.push(cop2);
		return counter +1;
	}
	public int size(){
		return 1;
		
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.COPIA;
		return bytes;
	}
	
	public static Copia fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.COPIA){
			return null;
		}
		return new Copia();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.COPIA+". copia";
	}


}
