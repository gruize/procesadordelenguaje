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
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (dir == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null direction"));
			return -1;
		}

		if (m.size() > dir || m.getPosicion(dir) == null){
			p.push(new MyExecutionError(MyExecutionError.MEMORY_ERROR,"Violation Memory"));
			return -1;
		}
		p.push(m.getPosicion(dir));
		return counter+1;
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
	public static ApilaDir fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.APILA_DIR){
			return null;
		}
		ApilaDir i = new ApilaDir();
		i.dir = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i;
	}
	@Override
	public String toString(){
		return "Code="+InstruccionMaquinaP.APILA_DIR+". Apila_dir";
	}


	
}

