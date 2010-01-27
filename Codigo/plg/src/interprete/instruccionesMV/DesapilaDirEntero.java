package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class DesapilaDirEntero extends InstruccionMaquinaP{
	private Integer dir;
	public DesapilaDirEntero(){
		dir = null;
	}
	public DesapilaDirEntero(int dir){
		this.dir = dir;
	}
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {

		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return -1;
		}
		if (dir == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null direction"));
			return -1;
		}
		//StackObject o1 = m.getPosicion(dir);
		StackObject o = p.pop();
		
		if (o instanceof MyInteger){
			m.setPosicion(dir, o);
			return counter+1;
		}
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR,"Incorrect types"));
		return -1;

	}
	@Override
	public int size(){
		if (dir == null)
			return 1;
		MyNatural n = new MyNatural();
		return 1+n.size();
		
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.DESAPILA_DIR_INTEGER;
		if (dir == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(dir);
		
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}

	public static DesapilaDirEntero fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DESAPILA_DIR_INTEGER){
			return null;
		}
		DesapilaDirEntero i = new DesapilaDirEntero();
		i.dir = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i; 
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.DESAPILA_DIR_INTEGER+". desapilaDir";
	}


}