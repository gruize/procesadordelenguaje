package interprete.instruccionesMV;

import java.util.Stack;
import util.Memoria;
import interprete.tipos.MyBoolean;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

public class Ir_v extends InstruccionMaquinaP{
	
	private Integer dir;
	
	public Ir_v (){
		dir=null;
	}
	
	public Ir_v(int dir){
		this.dir= dir;
	}
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (dir == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null direction"));
			return -1;
		}
		StackObject o = p.pop();
		// Si la cima de la pila es boolean
		if (o instanceof MyBoolean){
			//Si la cima de la pila es falso saltamos a la nueva dirección
			if ((Boolean)o.getValue()== true ){
				counter = dir;
				return counter;
			}
			//sino incrementamos el contador en uno
			else return counter + 1;
		}
		// error porque en la cima de la pila no hay un valor boolena
		p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR,"Incorrect types"));
		return -1;
		
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
		bytes[pos++] = InstruccionMaquinaP.IR_V;
		if (dir == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(dir);		
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}

	public static Ir_v fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.IR_V){
			return null;
		}
		Ir_v i = new Ir_v();
		i.dir = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i; 
	}
	
	public String toString(){
		return "Code="+InstruccionMaquinaP.IR_V+". Ir_v";
	}


}
