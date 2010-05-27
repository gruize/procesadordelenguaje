package interprete.instruccionesMV;

import interprete.tipos.MyChar;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class DesapilaIndChar extends InstruccionMaquinaP{
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {

		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		StackObject v = p.pop();
		if (v instanceof MyChar){
			if (p.isEmpty()){
				p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack only have one argument"));
				return -1;
			}
			else{
				StackObject d = p.pop();
				if (d instanceof MyInteger || d instanceof MyNatural){
					m.setPosicion((Integer)d.getValue(), v);
					return counter+1;
				}
				else {
					p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Incorrect types"));
					return -1;
				}
			}
		}
		p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Incorrect types"));
		return -1;
		
	}
	@Override
	public int size(){
		return 1;
	}
		
	@Override
	public byte[] toBytes() {byte[] bytes = new byte[size()];
	int pos = 0;
	bytes[pos++] = InstruccionMaquinaP.DESAPILA_IND_CHAR;
	return bytes;
	}

	public static DesapilaIndChar fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DESAPILA_IND_CHAR){
			return null;
		}
		return new DesapilaIndChar();
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.DESAPILA_IND_CHAR+". desapila_ind";
	}


}
