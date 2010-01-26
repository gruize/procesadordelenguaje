package interprete.instruccionesMV;

import interprete.tipos.MyBoolean;
import interprete.tipos.MyBuffer;
import interprete.tipos.MyChar;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyFloat;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class ValorAbsoluto extends InstruccionMaquinaP{
	public boolean exec(Stack<StackObject> p, Memoria m) {
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return false;
		}
		StackObject o = p.pop();
		if (o instanceof MyBuffer || 
				o instanceof MyBoolean || 
				o instanceof MyChar ){ 
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operand"));
			return false;
		}
		


		/*
		 * MyBoolean is operable with MyBoolean
		 */
		if (o instanceof MyInteger){
			MyInteger i = new MyInteger();
			Integer i1 = (Integer)o.getValue();
			if (i1 < 0){
				i.setValue(-i1);
				p.push(i);
			}
			else 
				p.push(o);
			
			return true;
		}
		if (o instanceof MyNatural){
			p.push(o);
			return true;
		}
		if (o instanceof MyFloat){
			MyFloat f = new MyFloat();
			Float f1 = (Float)o.getValue();
			if (f1 < 0){
				f.setValue(-f1);
			
				p.push(f);
			}
			else 
				p.push(o);
			return true;
		}
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operand"));
		return false;	
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.VALOR_ABSOLUTO;
		return bytes;
	}
	@Override
	public InstruccionMaquinaP fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.VALOR_ABSOLUTO){
			return null;
		}
		return this;
	}
}