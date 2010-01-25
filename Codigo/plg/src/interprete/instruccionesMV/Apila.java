package interprete.instruccionesMV;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Apila extends InstruccionMaquinaP{
	private StackObject element;
	public Apila(StackObject e){
		this.element = e;
	}
	public boolean exec(Stack<StackObject> p, Memoria m) {
		p.push(element);
		return true;
	}
	@Override
	public int size(){
		return 1+element.size();
		
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.APILA;
		System.arraycopy(element.toBytes(), 0,bytes, pos, element.size());
		return null;
	}
	
}
