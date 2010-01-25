package interprete.instrucciones;

import interprete.tipos.MyBuffer;
import interprete.tipos.StackObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

import util.Memoria;

public class Leer implements InstruccionMaquinaP{


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

}
