package interprete.instrucciones;

import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class Escribir implements InstruccionMaquinaP {


	public boolean exec(Stack<StackObject> p, Memoria m) {
		// TODO Auto-generated method stub
		if (check(p, m)){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR, "Empty stack"));
			return false;
		}
		Object o = p.pop();
		System.out.println(o);
		return true;
	}

}
