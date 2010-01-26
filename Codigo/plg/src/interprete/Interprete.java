package interprete;
/**Falta el main*/



import interprete.instruccionesMV.InstruccionMaquinaP;
import interprete.tipos.MyExecutionError;
import interprete.tipos.StackObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

import util.Memoria;

public class Interprete {
	private static boolean debug = true;
	private static Memoria mem;
	private static Stack<StackObject> pila;
	private static Integer contador;
	private static final int MAX_READ_BUFFER_SIZE = 1000; 
	private static Vector<InstruccionMaquinaP> codigo;
	public static void main(String []args){
		if (args.length == 1){
			try {
				int bufferPos = 0;
				boolean readError = false;
				FileInputStream fileInput = new FileInputStream(args[0]);
				BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
				byte[] buffer = new byte[MAX_READ_BUFFER_SIZE];
				int leidos;
				while((leidos = bufferedInput.read(buffer,bufferPos,bufferPos-MAX_READ_BUFFER_SIZE)) != -1){
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
				while (bufferPos < leidos){
					
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
				if (!codigo.get(contador).exec(pila, mem, contador)){
					System.err.println("Error en tiempo de ejecucion");
					if (!pila.isEmpty()){

						StackObject err = pila.pop();
						if (err instanceof MyExecutionError){
							System.err.println(err);
						}
						
					}
					System.exit(-1);
				}
			}
		}
		else
			System.err.println("Argumentos incorrectos");
		
	}
}
