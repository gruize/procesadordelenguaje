/**Falta el main*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.Vector;

import util.*;

public class Interprete {
	
	Memoria mem;
	Stack<Object> pila;
	OperacionesMaquinaP operacion;
	Object argumento;
	int contador;
	Vector<InstruccionesMaquinaP> codigo;
	
	public Interprete(Vector<InstruccionesMaquinaP> codigo) {
		this.codigo = codigo;
		this.mem = new Memoria();
	}

	public Memoria getMem() {
		return mem;
	}

	public void setMem(Memoria mem) {
		this.mem = mem;
	}

	public Stack<Object> getPila() {
		return pila;
	}

	public void setPila(Stack<Object> pila) {
		this.pila = pila;
	}

	public OperacionesMaquinaP getOperacion() {
		return operacion;
	}

	public void setOperacion(OperacionesMaquinaP operacion) {
		this.operacion = operacion;
	}

	public Object getArgumento() {
		return argumento;
	}

	public void setArgumento(Object argumento) {
		this.argumento = argumento;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public Vector<InstruccionesMaquinaP> getCodigo() {
		return codigo;
	}

	public void setCodigo(Vector<InstruccionesMaquinaP> codigo) {
		this.codigo = codigo;
	}
	
	public boolean siguienteInstruccion(){
		boolean obtenida = false;
		if(this.contador < this.codigo.size()){
			obtenida = true;
			this.operacion = this.codigo.get(this.contador).getOperacion();
			this.argumento = this.codigo.get(this.contador).getArgumento();	
		}		
		return obtenida;
	}
	
	public void ejecuta(){
		/**Creacion de la pila vacia*/
		this.pila = new Stack<Object>();
		/**Inicializacion del contador de programa*/
		this.contador = 0;
						
		/**Lectura por consola y modo de traza correcto
		InputStreamReader inputreader = new InputStreamReader(System.in);
		BufferedReader bufferreader = new BufferedReader(inputreader);
		*/
		
		while(siguienteInstruccion()){
			switch (this.getOperacion()){
			case APILA:		apila();
							break;
			case MAYOR:		mayor();
							break;
			case ESCRIBIR:	escribir();
							break;
			}
			
		}
		
		/** POR TERMINAR */
		
	}
	
	private void mayor(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;				
		
		//Comprobacion de tipos.SubCima es el primer operador  y es un real
		if( (String.valueOf((String)sub_cima).indexOf(".") > 0) ||  (String.valueOf((String)sub_cima).indexOf("E") > 0) ||  (String.valueOf((String)sub_cima).indexOf("e") > 0))
			//Comprobacion de tipos. Cima es el segundo operador y es un real -> Ambos reales
			if( (String.valueOf((String)cima).indexOf(".") > 0) ||  (String.valueOf((String)cima).indexOf("E") > 0) ||  (String.valueOf((String)cima).indexOf("e") > 0))
				this.pila.push((Float.valueOf((String)sub_cima) > Float.valueOf((String)cima)));
			else{
				//Comprobacion de tipos. Cima es el segundo operador y es un entero --> Real con entero
				if( (String.valueOf((String)cima).indexOf("+") > 0) || (String.valueOf((String)cima).indexOf("-") > 0) ){
					if( (String.valueOf((String)cima).indexOf("+") > 0) ){						
						this.pila.push((Float.valueOf((String)sub_cima) > (Float.valueOf(String.valueOf((String)cima).substring(String.valueOf((String) sub_cima).indexOf("+"))))));
					}	
					else
						this.pila.push((Float.valueOf((String)sub_cima) > Float.valueOf((String)cima)));
				}else{
					//Comprobacion de tipos. Cima es el segundo operador y es un natural --> Real con natural
					if( Integer.valueOf((String)cima) >= 0 ){					
						this.pila.push((Float.valueOf((String)sub_cima) > Float.valueOf((String)cima)));
					}else{
						//Comprobacion de tipos. Error de caracter
						//TODO: Por hacer la gestion del error						
					}
				}
			}				
		else {
			//Comprobacion de tipos. SubCima es el primer operador y es un entero
			if( (String.valueOf((String) sub_cima).indexOf("+") > 0) || (String.valueOf((String) sub_cima).indexOf("-") > 0) ){
				//Comprobacion de tipos. Cima es el segundo operador y es un real --> Entero con real
				if( (String.valueOf((String)cima).indexOf(".") > 0) ||  (String.valueOf((String)cima).indexOf("E") > 0) ||  (String.valueOf((String)cima).indexOf("e") > 0)){
					//TODO: Quizas hay que cambiar mas cosas. No lo se. Substring
					this.pila.push(Integer.valueOf((String)sub_cima) > Integer.valueOf((String)cima)); 
				}else{
					//Comprobacion de tipos. Cima es el segundo operador y es un entero --> Ambos enteros
					if( ( String.valueOf( (String) cima).indexOf("+") > 0 ) || ( String.valueOf((String)cima).indexOf("-") > 0 ))
						this.pila.push(Integer.valueOf((String)sub_cima) > Integer.valueOf((String)cima));
					else
						//Comprobacion de tipos. Cima es el segundo operador y es un natural --> Entero con natural
						if( Integer.valueOf((String)cima) >= 0 )
							this.pila.push(Integer.valueOf((String)sub_cima) > Integer.valueOf((String)cima));
						else{
							//Comprobacion de tipos. Error de caracter
							//TODO: Por hacer la gestion del error
						}							
				}
			}else{
				//Comprobacion de tipos. SubCima es el primer operador y es un natural
				if( Integer.valueOf((String) sub_cima) >= 0 ){
					//Comprobacion de tipos. Cima es el segundo operador y es un real --> Natural con real
					if( (String.valueOf((String)cima).indexOf(".") > 0) ||  (String.valueOf((String)cima).indexOf("E") > 0) ||  (String.valueOf((String)cima).indexOf("e") > 0))
						//TODO: Quizas hay que cambiar mas cosas. No lo se. Substring
						this.pila.push(Integer.valueOf((String)sub_cima) > Integer.valueOf((String)cima));
					else
						//Comprobacion de tipos. Cima es el segundo operador y es un entero --> Natural con entero
						if( ( String.valueOf( (String) cima).indexOf("+") > 0 ) || ( String.valueOf((String)cima).indexOf("-") > 0 ))
							this.pila.push(Integer.valueOf((String)sub_cima) > Integer.valueOf((String)cima));
						else
							//Comprobacion de tipos. Cima es el segundo operador y es un natural --> Ambos naturales
							if( Integer.valueOf((String)cima) >= 0 )
								this.pila.push(Integer.valueOf((String)sub_cima) > Integer.valueOf((String)cima));
							else{
								//Comprobacion de tipos. Error de caracter
								//TODO: Por hacer la gestion del error								
							}														
				}else{
					//Comprobacion de tipos. SubCima es el primer operador y es un caracter
					//LOS CARACTERES SE DECLARAN COMO ' caracter '
					if(Character.isDefined((Character)sub_cima)){						
						//Comprobacion de tipos. Cima es el segundo operador y es un caracter --> Ambos caracteres
						if(Character.isDefined((Character)cima))
							this.pila.push(Character.getNumericValue((Character)sub_cima) > Character.getNumericValue((Character)cima));  
						else{
							//Comprobacion de tipos. Error de caracter
							//TODO: Por hacer la gestion del error
						}
					}else{
						//Comprobacion de tipos.SubCima es el primer operador y es un booleano
						if((String.valueOf((String) sub_cima).equals(true) || (String.valueOf((String) sub_cima).equals(false)))){
							//Comprobacion de tipos. Cima es el segundo operador y es un booleano --> Ambos booleanos
							if(String.valueOf((String) cima).equals(true) || String.valueOf((String)cima).equals(false)){
								int cimaNum, sub_cimaNum;
								if(String.valueOf((String) sub_cima).equals(true))
									sub_cimaNum = 1;
								else
									sub_cimaNum = 0;
								if(String.valueOf((String) cima).equals(true))
									cimaNum = 1;
								else
									cimaNum = 0;
								this.pila.push(sub_cimaNum > cimaNum);
							}else{
								//Comprobacion de tipos. Error de caracter
								//TODO: Por hacer la gestion del error
							}								
						}else{
							//Comprobacion de tipos. Error
							//TODO:Por hacer la gestion de error
						}
					}
				}
			}
		}			
		/**POR TERMINAR*/		
	}
	
	private void menor(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/			
	}
	
	private void menor_igual(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/				
	}
	
	private void mayor_igual(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void distinto(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void suma(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void resta(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}
	
	private void multiplicacion(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void division(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void modulo(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void ylogico(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}
	
	private void ologico(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void nologico(){
		Object cima = this.pila.pop();
		Object sub_cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void signo(){
		Object cima = this.pila.pop();
		this.contador++;		
		/**POR TERMINAR*/
	}
	
	private void desplazamientoizquierda(){
		Object cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}
	
	private void desplazamientoderecha(){
		Object cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}

	private void castnat(){
		Object cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}
	
	private void castint(){
		Object cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}
	
	private void castchar(){
		Object cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}
	
	private void castfloat(){
		Object cima = this.pila.pop();
		this.contador++;
		/**POR TERMINAR*/
	}
	
	private void apila(){
		this.pila.push(String.valueOf(this.argumento));
		this.contador++;
	}
	
	private void apila_dir(){
		this.pila.push(String.valueOf(this.mem.getPosicion((Integer)this.argumento)));
		this.contador++;
	}
	
	private void desapila(){
		this.pila.pop();
		this.contador++;
	}
	
	private void desapila_dir(){
		  this.mem.setPosicion((Integer)this.argumento,this.pila.pop());
		  this.contador++;
	}
	
	private void leer(){
		
	}
	
	private void escribir(){
		System.out.println(String.valueOf((String)this.pila.pop()));
	}
	
	private void imprimirContenidoMemoria(){
		int cima = this.mem.getCima();
		for(int i = 0; i <= cima ; i++){
			if(this.mem.getPosicion(i) != null){
				System.out.println(i + ":\t" + this.mem.getPosicion(i));
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		Vector<InstruccionesMaquinaP> codigo = new Vector<InstruccionesMaquinaP>();
		codigo.add(new InstruccionesMaquinaP(OperacionesMaquinaP.APILA,true));
		codigo.add(new InstruccionesMaquinaP(OperacionesMaquinaP.APILA,true));
		codigo.add(new InstruccionesMaquinaP(OperacionesMaquinaP.MAYOR));
		codigo.add(new InstruccionesMaquinaP(OperacionesMaquinaP.ESCRIBIR));
		
		Interprete interprete = new Interprete(codigo);
		interprete.ejecuta();
	}

}
