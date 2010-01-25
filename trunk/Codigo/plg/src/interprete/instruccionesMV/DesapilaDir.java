package interprete.instruccionesMV;

import interprete.tipos.MyExecutionError;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

import java.util.Stack;

import util.Memoria;

public class DesapilaDir extends InstruccionMaquinaP{
	private int dir;
	public DesapilaDir(int dir){
		this.dir = dir;
	}
	public boolean exec(Stack<StackObject> p, Memoria m) {
		// TODO Auto-generated method stub
		StackObject o1 = m.getPosicion(dir);
		StackObject o2 = p.pop();
//		if (o2.getClass().getCanonicalName().equals(o1.getClass().getCanonicalName()))
		if (o2.getClass().equals(o1.getClass())){
			m.setPosicion(dir, o2);
		}
		return false;
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
		bytes[pos++] = InstruccionMaquinaP.DESAPILA_DIR;
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return null;
	}

}
