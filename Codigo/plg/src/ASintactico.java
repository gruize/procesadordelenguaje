import java.util.*;

import analizadorLexico.*;
import tablaSimbolos.*;

public class ASintactico {
	
//	private ALexico scanner;
	private TS ts;
	private Token tokActual;
	private Vector<Token> tokensIn;
	private int contTokens;
	//En principio har� un Vector de instrucciones de m�quina a pila como salida
	//del analizador sint�ctico. Se puede cambiar a salida a fichero
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
		return tokDevolver;
	}
	
	public void consume(tToken tokenEsperado){
		tokActual = tokensIn.get(contTokens);
		if (tokActual.getTipoToken() == tokenEsperado) {
//Todav�a no se si poner esto alante o atras del if. Como viene en la memoria
//creo que ser� alante
//			tokActual = tokensIn.get(contTokens);
			contTokens++;
		}
		else {
			System.out.println("Error: Programa incorrecto.");
//�Esto ser�a una buena forma de abortar la ejecuci�n?
			errorProg = true;
			emit("stop");
		}
	}
	
	//Hay que hacer un m�todo que obtenga el array de tokens de entrada
	//y que inicialice el tokenActual, con el primer elemento del array
	//teniendo que cuenta que hay que incrementar el contador de tokens
	//despu�s de asignar el token actual

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
		//Declaraci�n de las variables necesarias
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
			ts.a�adeId(id_tipo.getStr1(), id_tipo.getStr2(), errorDec1_dir.getIntVal());
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
		parOut.setStr2(consume().getLexema());
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
			ts.a�adeId(id_tipo.getStr1(), id_tipo.getStr2(), errorDec1_dir1.getIntVal());
			return new ParBooleanInt(false, errorDec1_dir1.getIntVal() + 1);
		}
	}
	
	//Devolvemos el par: (error = false, dir = 0)
	public ParBooleanInt rdecs2() {
		//La tabla se crea en el constructor, sino la crear�amos aqu�
		return new ParBooleanInt(false, 0);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}