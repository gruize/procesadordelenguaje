package interprete.instruccionesMV;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Stop extends InstruccionMaquinaP{

	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
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
	public static Stop fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DESAPILA){
			return null;
		}
		return new Stop();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.STOP+". stop";
	}


}
