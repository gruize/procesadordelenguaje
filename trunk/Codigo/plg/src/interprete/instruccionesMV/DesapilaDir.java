package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class DesapilaDir extends InstruccionMaquinaP{
	private Integer dir;
	public DesapilaDir(){
		dir = null;
	}
	public DesapilaDir(int dir){
		this.dir = dir;
	}
	public boolean exec(Stack<StackObject> p, Memoria m, Integer counter) {
		counter++;
		if (p.isEmpty()){
			p.push(new MyExecutionError(MyExecutionError.STACK_ERROR,"Stack is empty"));
			return false;
		}
		if (dir == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null direction"));
			return false;
		}
		StackObject o1 = m.getPosicion(dir);
		StackObject o2 = p.pop();
		if (o2.getClass().equals(o1.getClass())){
			m.setPosicion(dir, o2);
			return true;
		}
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR,"Incorrect types"));
		return false;

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
		bytes[pos++] = InstruccionMaquinaP.DESAPILA_DIR;
		if (dir == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(dir);
		
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}

	public static DesapilaDir fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.DESAPILA_DIR){
			return null;
		}
		DesapilaDir i = new DesapilaDir();
		i.dir = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i; 
	}
	public String toString(){
		return "Code="+InstruccionMaquinaP.DESAPILA_DIR+". desapilaDir";
	}


}
