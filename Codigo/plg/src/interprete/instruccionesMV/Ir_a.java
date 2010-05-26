package interprete.instruccionesMV;

import java.util.Stack;
import util.Memoria;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

public class Ir_a extends InstruccionMaquinaP{
	
	private Integer dir;
	
	public Ir_a (){
		dir=null;
	}
	
	public Ir_a(int dir){
		this.dir= dir;
	}
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (dir == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null direction"));
			return -1;
		}
		counter = dir;
		return counter;
	}
	
	public int size(){
		if (dir == null)
			return 1;
		MyNatural n = new MyNatural();
		return 1+n.size();
	}
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.IR_A;
		if (dir == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(dir);		
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}

	public static Ir_a fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.IR_A){
			return null;
		}
		Ir_a i = new Ir_a();
		i.dir = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i; 
	}
	
	public String toString(){
		return "Code="+InstruccionMaquinaP.IR_A+". Ir_a";
	}


}
