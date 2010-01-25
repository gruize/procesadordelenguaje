import java.util.*;

import analizadorLexico.*;
import tablaSimbolos.*;

public class ASintactico {
	
//	private ALexico scanner;
	private TS ts;
	private Token tokActual;
	private Vector<Token> tokensIn;
	private int contTokens;
	//En principio haré un Vector de instrucciones de máquina a pila como salida
	//del analizador sintáctico. Se puede cambiar a salida a fichero
	private Vector<String> instMPOut;
	private boolean errorProg;
	
	public ASintactico() {
		// TODO Auto-generated constructor stub
//		scanner = new ALexico();
		ts = new TS();
		tokActual = new Token();
		tokensIn = new Vector<Token>();
		contTokens = 0;
		instMPOut = new Vector<String>();
		errorProg = false;
	}
	
	public void emit(String instMP) {
		instMPOut.add(instMP);
	}
	
	/*public Token token() {
		return tokActual;
	}*/
	
	public Token consume(){
		Token tokDevolver = tokensIn.get(contTokens);
		contTokens++;
		tokActual = tokensIn.get(contTokens);
		return tokDevolver;
	}
	
	public void consume(tToken tokenEsperado){
		Token tokConsumido = tokensIn.get(contTokens);
		if (tokConsumido.getTipoToken() == tokenEsperado) {
//Todavía no se si poner esto alante o atras del if. Como viene en la memoria
//creo que será alante
//			tokActual = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
		}
		else {
			System.out.println("Error: Programa incorrecto.");
//¿Esto sería una buena forma de abortar la ejecución?
			errorProg = true;
			emit("stop");
		}
	}
	
	//Hay que hacer un método que obtenga el array de tokens de entrada
	//y que inicialice el tokenActual, con el primer elemento del array
	//teniendo que cuenta que hay que incrementar el contador de tokens
	//después de asignar el token actual
	public void parse() {
		//Variables para recorrer la tabla de símbolos
		String id = new String();
		Enumeration<String> e;
//		tokActual = tokensIn.firstElement();
//		contTokens++;
		
		programa();
		
		System.out.println("***********************************************************************");
		System.out.println("*                        ANÁLISIS SINTÁCTICO                          *");
		System.out.println("***********************************************************************");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Tabla de Símbolos");
		System.out.println("-----------------");
		System.out.println();
		//Mostramos la información de la tabla de símbolos		
		e = ts.getTabla().keys();
		while (e.hasMoreElements()) {
			id = e.nextElement();
			if (ts.getTabla().get(id).getTipo().equals("tipoVarCadCaracteres"))
				System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo() + "\tDirección: " +
						ts.getTabla().get(id).getDirM());
			else
				System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo() + "\t\tDirección: " +
						ts.getTabla().get(id).getDirM());
		}
	}

	/*public void programaFin() { //PROGRAMA_Fin ::= PROGRAMA finDeFichero
		programa();
		consume(tToken.finDeFichero);
		System.out.println("Programa correcto.");
	}*/
	
	public void programa(){ //PROGRAMA ::= DECS & SENTS
		boolean errorDec, errorSent;
		errorProg = errorDec = errorSent = false;
		
		errorDec = decs();
		consume(tToken.separador);
		errorSent = sents();
		errorProg = errorDec || errorSent;
		emit("stop");
	}
	
	public boolean decs(){
		//Declaración de las variables necesarias
		ParBooleanInt errorDec1_dir = new ParBooleanInt();
		ParString id_tipo = new ParString();
		//Cuerpo asociado a la funcionalidad de los no terminales
		id_tipo = dec();
		if (tokActual.getTipoToken() == tToken.puntoyComa)
			errorDec1_dir = rdecs1();
		else
			errorDec1_dir = rdecs2();
		if (errorDec1_dir.getBooleanVal() || ts.existeId(id_tipo.getStr1()))
			return true;
		else {
			ts.añadeId(id_tipo.getStr1(), id_tipo.getStr2(), errorDec1_dir.getIntVal());
			return false;
		}
	}
	
	public boolean sents() {
		
		
		
		return false;
	}
	
	public ParString dec() {
		ParString parOut = new ParString();
		parOut.setStr1(consume().getLexema());
		consume(tToken.dosPuntos);
		parOut.setStr2(consume().getTipoToken().toString());
		return parOut;
	}
	
	public ParBooleanInt rdecs1() {
		ParString id_tipo = new ParString();
		ParBooleanInt errorDec1_dir1 = new ParBooleanInt();
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.puntoyComa);
		id_tipo = dec();
		if (tokActual.getTipoToken() == tToken.puntoyComa)
			errorDec1_dir1 = rdecs1();
		else
			errorDec1_dir1 = rdecs2();
		if (errorDec1_dir1.getBooleanVal() || ts.existeId(id_tipo.getStr1()))
			return new ParBooleanInt(true, 0);
		else {
			ts.añadeId(id_tipo.getStr1(), id_tipo.getStr2(), errorDec1_dir1.getIntVal());
			return new ParBooleanInt(false, errorDec1_dir1.getIntVal() + 1);
		}
	}
	
	//Devolvemos el par: (error = false, dir = 0)
	public ParBooleanInt rdecs2() {
		//La tabla se crea en el constructor, sino la crearíamos aquí
		return new ParBooleanInt(false, 0);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombreFichero = "programa3.txt";
		
		ALexico scanner = new ALexico();
		ASintactico parser = new ASintactico();
		
		if (scanner.scanFichero(nombreFichero)) {
			parser.tokensIn = scanner.dameTokens();
			parser.parse();
		}
	}
}