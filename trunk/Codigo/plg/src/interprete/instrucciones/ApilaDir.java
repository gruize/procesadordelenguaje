package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class ApilaDir extends InstruccionMaquinaP{
	private Integer dir;
	public ApilaDir(Integer dir){
		this.dir = dir;
	}
	public boolean exec(Stack<StackObject> p, Memoria m) {
		if (m.size() > dir || m.getPosicion(dir) == null){
			p.push(new MyExecutionError(MyExecutionError.MEMORY_ERROR,"Violation Memory"));
			return false;
		}
		p.push(m.getPosicion(dir));
		return true;
	}
	@Override
	public int size(){
		MyNatural n = new MyNatural();
		return 1+n.size();
		
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		MyNatural n = new MyNatural();
		n.setValue(dir);
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.APILA;
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return null;
	}
	
}

