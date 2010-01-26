package interprete.instruccionesMV;

import interprete.tipos.MyBuffer;
import interprete.tipos.StackObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

import util.Memoria;

public class Leer extends InstruccionMaquinaP{


	public boolean exec(Stack<StackObject> p, Memoria m) {
		try{
			// TODO el cast hay que hacerlo?
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			MyBuffer linea = new MyBuffer();
			linea.setValue(br.readLine());
			p.push(linea);

		}
		catch(Exception e){ 
			e.printStackTrace();
		}
		return true;
	}
	@Override
	public int size(){
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		bytes[0] = InstruccionMaquinaP.LEER;
		return bytes;
	}

	public static Leer fromBytes(byte[] bytes, int pos){
		if (bytes[pos++]!= InstruccionMaquinaP.LEER){
			return null;
		}
		return new Leer();
	}

}
