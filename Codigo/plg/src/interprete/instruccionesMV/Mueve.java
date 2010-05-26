package interprete.instruccionesMV;

import java.util.Stack;
import util.Memoria;
import interprete.tipos.MyExecutionError;
import interprete.tipos.MyInteger;
import interprete.tipos.MyNatural;
import interprete.tipos.StackObject;

public class Mueve extends InstruccionMaquinaP{
	
	private Integer tam;
	
	public Mueve (){
		tam=null;
	}
	
	public Mueve(int tam){
		this.tam= tam;
	}
	
	public int exec(Stack<StackObject> p, Memoria m, Integer counter) {
		if (tam == null){
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR, "Null size"));
			return -1;
		}
		StackObject orig = p.pop();
		// Si la cima de la pila es Natural
		if (orig instanceof MyNatural || orig instanceof MyInteger){
			StackObject dest = p.pop();
			// Si la subcima es Natural
			if (dest instanceof MyNatural || dest instanceof MyInteger){
				Integer pos;
				//tenemos origen, destino y tamaño. Bucle que rellena valores
				for (int i = 0; i < tam; i++){
					pos = (Integer)orig.getValue() + (Integer)i;
					StackObject tmp = (StackObject)m.getPosicion(pos).duplica();
					pos = (Integer)dest.getValue() + (Integer)i;
					m.setPosicion(pos, tmp);
				}
				return counter +1;		
			}
			else {
				p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR,"Incorrect value on subtop"));
				return -1;
			}
			}
		else{
			p.push(new MyExecutionError(MyExecutionError.OPERATION_ERROR,"Incorrect value on top"));
			return -1;
				
		}
	}
	
	public int size(){
		if (tam == null)
			return 1;
		MyNatural n = new MyNatural();
		return 1+n.size();
	}
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		int pos = 0;
		bytes[pos++] = InstruccionMaquinaP.MUEVE;
		if (tam == null)
			return bytes;
		MyNatural n = new MyNatural();
		n.setValue(tam);		
		System.arraycopy(n.toBytes(), 0,bytes, pos, n.size());
		return bytes;
	}

	public static Mueve fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.MUEVE){
			return null;
		}
		Mueve i = new Mueve();
		i.tam = (Integer)new MyNatural().fromBytes(bytes, pos).getValue();
		return i; 
	}
	
	public String toString(){
		return "Code="+InstruccionMaquinaP.MUEVE+". Mueve";
	}


}
