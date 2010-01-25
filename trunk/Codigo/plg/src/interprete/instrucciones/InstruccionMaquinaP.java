package interprete.instrucciones;

import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public interface InstruccionMaquinaP {
	
	public boolean exec(Stack<StackObject> p, Memoria m);
	
	
	
}
