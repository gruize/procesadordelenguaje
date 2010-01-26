package interprete;
/**Falta el main*/



import interprete.instruccionesMV.InstruccionMaquinaP;
import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

import util.Memoria;

public class Interprete {
	private static boolean debug = true;
	private static boolean traza = false;
	private static boolean fast = true;
	private static Memoria mem;
	private static Stack<StackObject> pila;

	private static final int MAX_READ_BUFFER_SIZE = 1000; 
	private static Vector<InstruccionMaquinaP> codigo;
	public static void main(String []args){
		Integer contador;
		if (fast){
			args = new String[2];
			args[0] = "programa.bin";
			args[1] = "true";
				
		}
		codigo = new Vector<InstruccionMaquinaP>();
		if (args.length > 1){
			try {
				int bufferPos = 0;
				boolean readError = false;
				FileInputStream fileInput = new FileInputStream(args[0]);
				if (args.length > 2)
					if (args[2].equals("trazar"))
						traza = true;
				BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
				byte[] buffer = new byte[MAX_READ_BUFFER_SIZE];
				int leidos;
				while((leidos = bufferedInput.read(buffer,bufferPos,MAX_READ_BUFFER_SIZE-bufferPos)) != -1){
					bufferPos = 0;
					while((bufferPos) < leidos){
						InstruccionMaquinaP ins = InstruccionMaquinaP.fromBytes(buffer, bufferPos);
						if (ins == null){
							if (readError){
								System.err.println("El fichero "+args[0]+" esta corrupto");
								System.exit(-1);
							}
	
							readError = true;
							break;
						}
						else
							readError = false;
						bufferPos += ins.size();
						codigo.add(ins);
					}
					// parada por un primer error de lectura segundo intento metiendo mas chars al buffer
					if (bufferPos < leidos && (leidos - bufferPos) < InstruccionMaquinaP.MAX_SIZE ){
						System.arraycopy(buffer, bufferPos, buffer, 0, leidos - bufferPos);
						bufferPos = leidos - bufferPos;

					}else
						bufferPos = 0;
				}
				// leyendo el resto
				if (readError){
					System.err.println("File is not correct");
					System.exit(-1);
				}
					

				bufferedInput.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (debug)
				System.out.println("Programa cargado en memoria");
			mem = new Memoria();
			pila = new Stack<StackObject>();
			contador = 0;
			while (true){
				if (contador >= codigo.size() || contador < 0){
					System.err.println("Error en el puntero de programa");
					System.exit(-1);
				}
				InstruccionMaquinaP i = codigo.get(contador); 
				contador = i.exec(pila, mem, contador);
				if (contador != -1){
					if (traza){
						
						Enumeration<StackObject> elements =pila.elements();
						if (elements != null){
							System.out.println("Pila:");
							int pos = 0;
							while (elements.hasMoreElements()){
								System.out.println(pos+": "+elements.toString());
								pos++;
							}
							
						}
						elements =mem.elements();
						if (elements != null){
							System.out.println("Memoria:");
							int pos = 0;
							while (elements.hasMoreElements()){
								System.out.println(pos+": "+elements.toString());
								pos++;
							}
							
						}
						System.out.println("Instruccion ejecutada: ");
						System.out.println(i);
					}
					
				}else{
					System.err.println("Error en tiempo de ejecucion");
					if (!pila.isEmpty()){
						StackObject err = pila.pop();
						if (err instanceof MyExecutionError){
							System.err.println(err);
						}
						System.exit(-1);
					}
				}
			}
		}
		else
			System.err.println("Argumentos incorrectos");
		
	}
}
