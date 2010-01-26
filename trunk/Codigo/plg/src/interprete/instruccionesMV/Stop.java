package interprete.instruccionesMV;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Stop extends InstruccionMaquinaP{

	public boolean exec(Stack<StackObject> p, Memoria m) {
		System.exit(0);
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
	@Override
	public InstruccionMaquinaP fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DESAPILA){
			return null;
		}
		return this;
	}

}
