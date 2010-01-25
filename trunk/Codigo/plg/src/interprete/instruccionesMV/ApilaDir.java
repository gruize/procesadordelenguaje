package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class ApilaDir extends InstruccionMaquinaP{
	private Integer dir;
	public ApilaDir(){
		this.dir = null;
	}
	public ApilaDir(Integer dir){
		this.dir = dir;
	}
	public boolean exec(Stack<StackObject> p, Memoria m) {
		if (dir == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null direction"));
			return false;
		}

		if (m.size() > dir || m.getPosicion(dir) == null){
			p.push(new MyExecutionError(MyExecutionError.MEMORY_ERROR,"Violation Memory"));
			return false;
		}
		p.push(m.getPosicion(dir));
		return true;
	}
	@Override
	public int size(){
		if (this.dir == null)
			return 1;
		MyNatural n = new MyNatural();
		return 1+n.size();
		
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.APILA;
		if (this.dir == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(dir);
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}
	@Override
	public InstruccionMaquinaP fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.APILA_DIR){
			return null;
		}
		dir = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return this;
	}

	
}

