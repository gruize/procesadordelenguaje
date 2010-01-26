package analizadorSintactico;

import java.util.*;

import analizadorLexico.*;
import tablaSimbolos.*;

public class ASintactico {
	
//	private ALexico scanner;
	private TS ts;
	//Elemento de preanálisis
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
	
	public void emite(String instMP) {
		instMPOut.add(instMP);
	}
	
	/*public Token token() {
		return tokActual;
	}*/
	
	public Token consume(){
//		if (esTokenId(tokActual) || esTokenTipo(tokActual)) {
			Token tokDevolver = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
			return tokDevolver;
//		}
//		else {
//			System.out.println("Error: Se esperaba un identificador o un tipo. ");
////					" o un tipo'" + '\n' +)
////					tokenEsperado.toString() + "'.");
////¿Esto sería una buena forma de abortar la ejecución?
//			errorProg = true;
//			emite("stop");
//			return new Token();
//		}
	}
	
	public Token consumeId(){
		if (esTokenId(tokActual)) {
			Token tokDevolver = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
			return tokDevolver;
		}
		else {
			System.out.println("Error: Se esperaba un token de tipo 'identificador'.\n" +
					"Token en preanálisis: " + tokActual.getTipoToken() + "\n");
////¿Esto sería una buena forma de abortar la ejecución?
			errorProg = true;
			emite("stop");
			return new Token(tToken.tokenError, "Se esperaba un token de tipo 'identificador'.");
		}
	}
	
	public Token consumeTipo(){
		if (esTokenTipo(tokActual)) {
			Token tokDevolver = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
			return tokDevolver;
		}
		else {
			System.out.println("Error: Se esperaba un token de tipo 'tipoDeVariable'.\n" +
					"Token en preanálisis: " + tokActual.getTipoToken() + "\n");
////¿Esto sería una buena forma de abortar la ejecución?
			errorProg = true;
			emite("stop");
			return new Token(tToken.tokenError, "Se esperaba un token de tipo 'tipoDeVariable'.");
		}
	}
	
	public boolean esTokenTipo(Token t) {
		if (t.getTipoToken() == tToken.tipoVarBooleano ||
				t.getTipoToken() == tToken.tipoVarCadCaracteres ||
				t.getTipoToken() == tToken.tipoVarNatural ||
				t.getTipoToken() == tToken.tipoVarEntero ||
				t.getTipoToken() == tToken.tipoVarReal)
			return true;
		else
			return false;
	}
	
	public boolean esTokenId(Token t) {
		if (t.getTipoToken() == tToken.identificador)
			return true;
		else
			return false;
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
			System.out.println("Error: Se esperaba token de tipo '" + 
					tokenEsperado.toString() + "'." + "\n");
//¿Esto sería una buena forma de abortar la ejecución?
			errorProg = true;
			emite("stop");
		}
	}
	
	public void vaciaCod() {
		instMPOut.clear();
	}
	
	public tSintetiz dametSintetiz(tToken tipo) {
		switch (tipo) {
		case tipoVarBooleano:
			return tSintetiz.tBool;
		case tipoVarCadCaracteres:
			return tSintetiz.tChar;
		case tipoVarNatural:
			return tSintetiz.tNat;
		case tipoVarEntero:
			return tSintetiz.tInt;
		case tipoVarReal:
			return tSintetiz.tFloat;
		default:
			return tSintetiz.tError;
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
		
		System.out.println("***********************************************************************");
		System.out.println("*                        ANÁLISIS SINTÁCTICO                          *");
		System.out.println("***********************************************************************");
		System.out.println();
		
		programa();
		
		System.out.println();
		System.out.println();
		
		if (errorProg) {
			System.out.println("Análisis fallido.");
		}
		else {
			System.out.println("Tabla de Símbolos");
			System.out.println("-----------------");
			System.out.println();
			//Mostramos la información de la tabla de símbolos		
			e = ts.getTabla().keys();
			while (e.hasMoreElements()) {
				id = e.nextElement();
				if (ts.getTabla().get(id).getTipo().equals("tipoVarCadCaracteres"))
					System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo().toString() + "\tDirección: " +
							ts.getTabla().get(id).getDirM());
				else
					System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo().toString() + "\t\tDirección: " +
							ts.getTabla().get(id).getDirM());
			}
			System.out.println();
			System.out.println();
			System.out.println("El análisis ha sido satisfactorio.");
			System.out.println();
			System.out.println("Instrucciones para la máquina a pila generadas");
			System.out.println("----------------------------------------------");
			for (int i = 0; i < instMPOut.size(); i++)
				System.out.println(instMPOut.get(i));
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
		//La linea de abajo habría que cambiarla por esta:
		//errorProg = errorDec || errorSent;
		errorProg = errorProg || errorDec || errorSent;
		
		emite("stop");
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
		if (errorDec1_dir.getBooleanVal() || ts.existeId(id_tipo.getIden()))
			return true;
		else {
			ts.anadeId(id_tipo.getIden(), id_tipo.getTipo(), errorDec1_dir.getIntVal());
			return false;
		}
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
		if (errorDec1_dir1.getBooleanVal() || ts.existeId(id_tipo.getIden()))
			return new ParBooleanInt(true, -1);
		else {
			ts.anadeId(id_tipo.getIden(), id_tipo.getTipo(), errorDec1_dir1.getIntVal());
			return new ParBooleanInt(false, errorDec1_dir1.getIntVal() + 1);
		}
	}
	
	//Devolvemos el par: (error = false, dir = 0)
	public ParBooleanInt rdecs2() {
		//La tabla se crea en el constructor, sino la crearíamos aquí
		if (errorProg)
			return new ParBooleanInt(true, -1);
		else
			return new ParBooleanInt(false, 0);
	}
	
	public ParString dec() {
		ParString parOut = new ParString();
		parOut.setIden(consumeId().getLexema());
		consume(tToken.dosPuntos);
		parOut.setTipo(dametSintetiz(consumeTipo().getTipoToken()));
		return parOut;
	}
	
	public boolean sents() {
		//Declaración de las variables necesarias
		boolean errorSent1, errorSent2 = false;
		//Cuerpo asociado a la funcionalidad de los no terminales
		errorSent1 = sent();
		if (tokActual.getTipoToken() == tToken.puntoyComa)
			errorSent2 = rsents1();
		else
			errorSent2 = rsents2();
		return errorSent1 || errorSent2;
	}
	
	public boolean rsents1() {
		//Declaración de las variables necesarias
		boolean errorSent1, errorSent2 = false;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.puntoyComa);
		errorSent1 = sent();
		if (tokActual.getTipoToken() == tToken.puntoyComa)
			errorSent2 = rsents1();
		else
			errorSent2 = rsents2();
		return errorSent1 || errorSent2;
	}
	
	public boolean rsents2() {
		//Declaración de las variables necesarias
		
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (errorProg)
			return true;
		else
			return false;
	}
	
	public boolean sent() {
		//Declaración de las variables necesarias
		
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.entradaTeclado) {
			return sread();
		}
		if (tokActual.getTipoToken() == tToken.salidaPantalla) {
			return swrite();
		}
		if (tokActual.getTipoToken() == tToken.asignacion) {
			return sasign();
		}
		//Añadimos control de errores
		else {
			errorProg = true;
			System.out.println("Error: Se esperaba una de las siguientes instrucciones:\n" +
					"	- Asignación			=>  ':='\n" +
					"	- Entrada por teclado		=>  'in()'\n" +
					"	- Salida por pantalla 'out()'	=>  'out()'\n\n" +
					"Token en preanálisis: " + tokActual.getTipoToken() + "\n");
			return true;
		}
//		consume(tToken.puntoyComa);
	}
	
	public boolean swrite() {
		//Declaración de las variables necesarias
		tSintetiz tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.salidaPantalla);
		//Ahora en el token actual tenemos el parentesis de apertura
		//si el análisis va bien, y luego la expresión correspondiente
		if (tokActual.getTipoToken() == tToken.parApertura) {
			//Consumimos el parentesis de apertura y vamos con la expresión
			consume(tToken.parApertura);
			tipo = exp();
			///////////////////////////////////////////////////////////////
			if (tipo == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la expresión de la instrucción de salida por pantalla." + "\n");
				return true;
			}
			else {
				emite("escribir");
				consume(tToken.parCierre);
				return false;
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba paréntesis de apertura después de operación de entrada" + "\n" +
					"por teclado.\n");
			return true;
		}
	}
	
	public boolean sread() {
		//Declaración de las variables necesarias
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.entradaTeclado);
		//Ahora en el token actual tenemos el parentesis de apertura
		//si el análisis va bien, y luego el identificador correspondiente
		if (tokActual.getTipoToken() == tToken.parApertura) {
			consume(tToken.parApertura);
			if (tokActual.getTipoToken() == tToken.identificador &&
					ts.existeId(tokActual.getLexema())) {
				lexIden = tokActual.getLexema();
				emite("leer");
				emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				consume(tToken.identificador);
				consume(tToken.parCierre);
				return false;
			}
			else {
				errorProg = true;
				vaciaCod();
				System.out.println("Error: El parámetro de la operación 'in' debe ser un identificador, o" + "\n" +
						"el identificador no existe en la tabla de símbolos.\n");
				return true;
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba paréntesis de apertura después de operación de entrada" + "\n" +
					"por teclado.\n");
			return true;
		}
	}
	
	public boolean sasign() {
		//Declaración de las variables necesarias
		tSintetiz tipo;
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema())) {
			lexIden = tokActual.getLexema();
			consume(tToken.identificador);
			//Una vez parseado el identificador vamos con el simbolo de la asignación
			consume(tToken.asignacion);
			//LLamada a epx()
			tipo = exp();
			/////////////////////////////////////////////////////////////////////////
			if (tipo == tSintetiz.tError || esCompatibleAsig(ts.getTabla().get(lexIden).getTipo(), tipo)) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la asignación: La expresión es errónea, o los tipos de la" + "\n" +
						"asignación son incompatibles." + "\n");
				return true;
			}
			else {
				emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				return false;
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba identificador, o si lo es no fue declarado previamente." + "\n");
			return true;
		}
	}
	
	public boolean esCompatibleAsig(tSintetiz tipoId, tSintetiz tipoExp) {
		if (tipoId == tipoExp)
			return true;
		if (tipoId == tSintetiz.tFloat && esTipoNum(tipoExp))
			return true;
		if (tipoId == tSintetiz.tInt && tipoExp == tSintetiz.tNat)
			return true;
		return false;
	}
	
	public boolean esTipoNum(tSintetiz tipo) {
		if (tipo == tSintetiz.tNat || tipo == tSintetiz.tInt ||
				tipo == tSintetiz.tFloat)
			return true;
		else
			return false;
	}
	
	public tSintetiz exp() {
		//Declaración de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx1()
		tipo1 = exp1();
		tipoH = tipo1;
		if (tipo1 == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en expresión. Categoría sintáctica afectada: (EXP1).\n");
			return tSintetiz.tError;
		}
		else {
			if (tokActual.getTipoToken() == tToken.puntoyComa) {// Hemos llegado al fin de la instrucción
//				rexp2();
				return tipoH;
			}
			else {
				tipo2 = rexp1(tipoH);
				if (tipo2 == tSintetiz.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en expresión. Categoría sintáctica afectada: (REXP_1).\n");
					return tSintetiz.tError;
				}
				else
					//No sería 'return tipo2;' ??
					return tSintetiz.tBool;
			}
		}
	}
	
	public tSintetiz rexp1(tSintetiz tipoH) {
		//Declaración de las variables necesarias
		tSintetiz tipo, tipo1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op0();
		tipo1 = exp1();
		tipo = dameTipo(tipoH,tipo1,op);
		if (tipo == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error de tipos en operación de igualdad.\n");
			return tSintetiz.tError;
		}
		else {
			emite(op.toString());
			return tipo;
		}
	
//		OP0(out op)
//		EXP1(out tipo1)
//		tipo = dameTipo(tipoH,tipo,op)
//		si tipo = tError
//		entonces
//			vaciaCod()
//		si no
//			emit(op)
//		fin si

	}
	
	public tSintetiz dameTipo(tSintetiz tipoEXPIzq, tSintetiz tipoEXPDer, tOp op) {
		
		return tSintetiz.tError;
	}
	
	public tOp op0() {
		
		return tOp.igual;
	}
	
	public tSintetiz exp1() {
		
		return tSintetiz.tBool;
		
		
		
//		EXP2  (out tipo1)
//		si token pertenece {; )} // fin de instrucción
//		entoces
//			REXP1_2()
//			tipo = tipo1
//		si no
//			REXP1_1(in tipoH, out tipo2)
//			si tipo1 = tError or tipo2 = tError
//			entonces
//				tipo = tError
//				vaciaCod()
//			si no
//				tipo = tipo2
//			fin si
//		fin si
		
	}
	
//	public void rexp2() {
//		
//	}
	
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
			parser.tokActual = parser.tokensIn.get(parser.contTokens);
			parser.parse();
		}
	}
}