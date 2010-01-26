package interprete.instruccionesMV;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Desapila extends InstruccionMaquinaP{

	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
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

}
