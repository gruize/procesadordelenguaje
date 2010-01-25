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
					System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo() + "\tDirección: " +
							ts.getTabla().get(id).getDirM());
				else
					System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo() + "\t\tDirección: " +
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
		if (errorDec1_dir.getBooleanVal() || ts.existeId(id_tipo.getStr1()))
			return true;
		else {
			ts.anadeId(id_tipo.getStr1(), id_tipo.getStr2(), errorDec1_dir.getIntVal());
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
		if (errorDec1_dir1.getBooleanVal() || ts.existeId(id_tipo.getStr1()))
			return new ParBooleanInt(true, -1);
		else {
			ts.anadeId(id_tipo.getStr1(), id_tipo.getStr2(), errorDec1_dir1.getIntVal());
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
		parOut.setStr1(consumeId().getLexema());
		consume(tToken.dosPuntos);
		parOut.setStr2(consumeTipo().getTipoToken().toString());
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
		tipoSint tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.salidaPantalla);
		//Ahora en el token actual tenemos el parentesis de apertura
		//si el análisis va bien, y luego el identificador correspondiente
		if (tokActual.getTipoToken() == tToken.parApertura) {
			tipo = exp();
			if (tipo == tipoSint.tError)
				vaciaCod();
			else
				emite("escribir");
			consume(tToken.parCierre);
			return (tipo == tipoSint.tError);
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
		boolean errorSent = false;
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
			}
			else {
				errorSent = true;
				errorProg = true;
				vaciaCod();
				System.out.println("Error: El parámetro de la operación 'in' debe ser un identificador, o" + "\n" +
						"el identificador no existe en la tabla de símbolos.\n");
			}	
		}
		else {
			errorSent = true;
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba paréntesis de apertura después de operación de entrada" + "\n" +
					"por teclado.\n");
		}
		consume(tToken.parCierre);
		return errorSent;
	}
	
	public boolean sasign() {
//		iden(out lexema)
//		consume (‘:=’)
//		EXP(out tipo)
//		errorSent = (tipo = tError) or existeID(ts,lexema)) or 
//			(not esCompatibleAsig?(dameTipoTS(ts, lexema)))
//		si errorSent
//		entonces
//			vaciaCod()
//		si no
//			emit(desapila_dir(damePropiedadesTS(tsh, lexema).dirProp));
//		fin si
		return true;
	}
	
	public tipoSint exp() {
		
		
		
		return tipoSint.tBool;
		
//		EXP1(out tipo1)
//		tipoH =tipo1;
//		// XXX codH = EXP1.cod}
//		si tipo1 = tError
//		entonces 
//			tipo = tError
//		si no
//			si token pertenece {; )} // fin de instrucción
//			entonces
//				REXP_2();
//				tipo = tipoH
//			si no
//				REXP_1(in tipoH, out tipo2);
//				si tipo2 = tError;
//				entonces
//					tipo = tError;
//					vaciaCod();
//				si no
//					tipo = tBool;
//				fin si
//			fin si
//		fin si

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
			parser.tokActual = parser.tokensIn.get(parser.contTokens);
			parser.parse();
		}
	}
}