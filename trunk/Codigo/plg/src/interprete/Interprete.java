package interprete;
/**Falta el main*/



import interprete.instruccionesMV.InstruccionMaquinaP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Stack;
import java.util.Vector;

import util.Memoria;

public class Interprete {
	
	private static Memoria mem;
	private static Stack<Object> pila;
	private static int contador;
	Vector<InstruccionMaquinaP> codigo;
	public static void main(String []args){
		if (args.length == 1){
			try {
				FileReader file = new FileReader(new File(args[0]));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
			System.err.println("Argumentos incorrectos");
		
	}
}
