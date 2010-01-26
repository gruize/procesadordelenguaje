package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class DesplazamientoDerechas extends InstruccionMaquinaP {

	public boolean exec(Stack<StackObject> p, Memoria m) {
		if (p.size() < 2){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Not enough elements"));
			return false;
		}
		StackObject o1 = p.pop();
		StackObject o2 = p.pop();

		if (o1 instanceof MyNatural){
			if (o2 instanceof MyNatural){
				MyNatural n = new MyNatural();
				Integer n1 = (Integer)o1.getValue();
				Integer n2 = (Integer)o2.getValue();
				n.setValue(n1>>n2);
				p.push(n);
				return true;
			}
		}
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "The operation doesn't support the operands"));
		return false;	
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.DESPLAZAMIENTODERECHA;
		return bytes;
	}
	@Override
	public InstruccionMaquinaP fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DESPLAZAMIENTODERECHA){
			return null;
		}
		return this;
	}
}