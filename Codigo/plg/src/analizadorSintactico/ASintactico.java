package analizadorSintactico;

import java.util.*;

import analizadorLexico.*;
import tablaSimbolos.*;

public class ASintactico {
	
//	private ALexico scanner;
	private TS ts;
	//Elemento de prean�lisis
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
////�Esto ser�a una buena forma de abortar la ejecuci�n?
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
					"Token en prean�lisis: " + tokActual.getTipoToken() + "\n");
////�Esto ser�a una buena forma de abortar la ejecuci�n?
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
					"Token en prean�lisis: " + tokActual.getTipoToken() + "\n");
////�Esto ser�a una buena forma de abortar la ejecuci�n?
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
//Todav�a no se si poner esto alante o atras del if. Como viene en la memoria
//creo que ser� alante
//			tokActual = tokensIn.get(contTokens);
			contTokens++;
			tokActual = tokensIn.get(contTokens);
		}
		else {
			System.out.println("Error: Se esperaba token de tipo '" + 
					tokenEsperado.toString() + "'." + "\n");
//�Esto ser�a una buena forma de abortar la ejecuci�n?
			errorProg = true;
			emite("stop");
		}
	}
	
	public void vaciaCod() {
		instMPOut.clear();
	}
	
	public tSintetiz dameTSintetiz(tToken tipo) {
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
	
	//Hay que hacer un m�todo que obtenga el array de tokens de entrada
	//y que inicialice el tokenActual, con el primer elemento del array
	//teniendo que cuenta que hay que incrementar el contador de tokens
	//despu�s de asignar el token actual
	public void parse() {
		//Variables para recorrer la tabla de s�mbolos
		String id = new String();
		Enumeration<String> e;
//		tokActual = tokensIn.firstElement();
//		contTokens++;
		
		System.out.println("***********************************************************************");
		System.out.println("*                        AN�LISIS SINT�CTICO                          *");
		System.out.println("***********************************************************************");
		System.out.println();
		
		programa();
		
		System.out.println();
		System.out.println();
		
		if (errorProg) {
			System.out.println("An�lisis fallido.");
		}
		else {
			System.out.println("Tabla de S�mbolos");
			System.out.println("-----------------");
			System.out.println();
			//Mostramos la informaci�n de la tabla de s�mbolos		
			e = ts.getTabla().keys();
			while (e.hasMoreElements()) {
				id = e.nextElement();
				if (ts.getTabla().get(id).getTipo().equals("tipoVarCadCaracteres"))
					System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo().toString() + "\tDirecci�n: " +
							ts.getTabla().get(id).getDirM());
				else
					System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo().toString() + "\t\tDirecci�n: " +
							ts.getTabla().get(id).getDirM());
			}
			System.out.println();
			System.out.println();
			System.out.println("El an�lisis ha sido satisfactorio.");
			System.out.println();
			System.out.println("Instrucciones para la m�quina a pila generadas");
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
		//La linea de abajo habr�a que cambiarla por esta:
		//errorProg = errorDec || errorSent;
		errorProg = errorProg || errorDec || errorSent;
		
		emite("stop");
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
		//La tabla se crea en el constructor, sino la crear�amos aqu�
		if (errorProg)
			return new ParBooleanInt(true, -1);
		else
			return new ParBooleanInt(false, 0);
	}
	
	public ParString dec() {
		ParString parOut = new ParString();
		parOut.setIden(consumeId().getLexema());
		consume(tToken.dosPuntos);
		parOut.setTipo(dameTSintetiz(consumeTipo().getTipoToken()));
		return parOut;
	}
	
	public boolean sents() {
		//Declaraci�n de las variables necesarias
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
		//Declaraci�n de las variables necesarias
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
		//Declaraci�n de las variables necesarias
		
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (errorProg)
			return true;
		else
			return false;
	}
	
	public boolean sent() {
		//Declaraci�n de las variables necesarias
		
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
		//A�adimos control de errores
		else {
			errorProg = true;
			System.out.println("Error: Se esperaba una de las siguientes instrucciones:\n" +
					"	- Asignaci�n			=>  ':='\n" +
					"	- Entrada por teclado		=>  'in()'\n" +
					"	- Salida por pantalla 'out()'	=>  'out()'\n\n" +
					"Token en prean�lisis: " + tokActual.getTipoToken() + "\n");
			return true;
		}
//		consume(tToken.puntoyComa);
	}
	
	public boolean swrite() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.salidaPantalla);
		//Ahora en el token actual tenemos el parentesis de apertura
		//si el an�lisis va bien, y luego la expresi�n correspondiente
		if (tokActual.getTipoToken() == tToken.parApertura) {
			//Consumimos el parentesis de apertura y vamos con la expresi�n
			consume(tToken.parApertura);
			tipo = exp();
			///////////////////////////////////////////////////////////////
			if (tipo == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la expresi�n de la instrucci�n de salida por pantalla." + "\n");
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
			System.out.println("Error: Se esperaba par�ntesis de apertura despu�s de operaci�n de entrada" + "\n" +
					"por teclado.\n");
			return true;
		}
	}
	
	public boolean sread() {
		//Declaraci�n de las variables necesarias
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.entradaTeclado);
		//Ahora en el token actual tenemos el parentesis de apertura
		//si el an�lisis va bien, y luego el identificador correspondiente
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
				System.out.println("Error: El par�metro de la operaci�n 'in' debe ser un identificador, o" + "\n" +
						"el identificador no existe en la tabla de s�mbolos.\n");
				return true;
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba par�ntesis de apertura despu�s de operaci�n de entrada" + "\n" +
					"por teclado.\n");
			return true;
		}
	}
	
	public boolean sasign() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo;
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema())) {
			lexIden = tokActual.getLexema();
			consume(tToken.identificador);
			//Una vez parseado el identificador vamos con el simbolo de la asignaci�n
			consume(tToken.asignacion);
			//LLamada a epx()
			tipo = exp();
			/////////////////////////////////////////////////////////////////////////
			if (tipo == tSintetiz.tError || esCompatibleAsig(ts.getTabla().get(lexIden).getTipo(), tipo)) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la asignaci�n: La expresi�n es err�nea, o los tipos de la" + "\n" +
						"asignaci�n son incompatibles." + "\n");
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
	
	public boolean esTipoNatInt(tSintetiz tipo) {
		if (tipo == tSintetiz.tNat || tipo == tSintetiz.tInt)
			return true;
		else
			return false;
	}
	
	public tSintetiz exp() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx1()
		tipo1 = exp1();
		tipoH = tipo1;
		if (tipo1 == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (EXP1).\n");
			return tSintetiz.tError;
		}
		else {
			if (tokActual.getTipoToken() == tToken.puntoyComa) {// Hemos llegado al fin de la instrucci�n
//				rexp2();
				return tipoH;
			}
			else {
				tipo2 = rexp1(tipoH);
				if (tipo2 == tSintetiz.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP_1).\n");
					return tSintetiz.tError;
				}
				else
					//No ser�a 'return tipo2;' ??
					//return tipo2;
					return tSintetiz.tBool;
			}
		}
	}
	
	public tSintetiz rexp1(tSintetiz tipoH) {
		//Declaraci�n de las variables necesarias
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
			System.out.println("Error de tipos en operaci�n de igualdad: '"+ op.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			emite(op.toString());
			return tipo;
		}	
	}
	
	public tSintetiz exp1() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx2()
		tipo1 = exp2();
		tipoH = tipo1;
		if (tokActual.getTipoToken() == tToken.puntoyComa) {// Hemos llegado al fin de la instrucci�n
//			rexp12();
			return tipo1;
		}
		else {
			tipo2 = rexp11(tipoH);
			if (tipo1 == tSintetiz.tError || tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP1_1).\n");
				return tSintetiz.tError;
			}
			else
				return tipo2;
		}
	}
	
	public tSintetiz rexp11(tSintetiz tipoH) {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op1();
		tipo1 = exp2();
		tipoH1 = dameTipo(tipoH,tipo1,op);
		if (tokActual.getTipoToken() == tToken.puntoyComa) {// Hemos llegado al fin de la instrucci�n
//			rexp12();
			return tipoH1;
		}
		else {
			tipo2 = rexp11(tipoH1);
//			tipo = tipo2;
			if (tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
				return tSintetiz.tError;
			}
			else {
				emite(op.toString());
				return tipo2;
			}
		}
	}
	
	public tSintetiz exp2() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx3()
		tipo1 = exp3();
		tipoH = tipo1;
		if (tokActual.getTipoToken() == tToken.puntoyComa) {// Hemos llegado al fin de la instrucci�n
//			rexp22();
			return tipo1;
		}
		else {
			tipo2 = rexp21(tipoH);
			if (tipo1 == tSintetiz.tError || tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP2_1).\n");
				return tSintetiz.tError;
			}
			else
				return tipo2;
		}
	}
	
	public tSintetiz rexp21(tSintetiz tipoH) {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op2();
		tipo1 = exp3();
		tipoH1 = dameTipo(tipoH,tipo1,op);
		if (tokActual.getTipoToken() == tToken.puntoyComa) {// Hemos llegado al fin de la instrucci�n
//			rexp22();
			return tipoH1;
		}
		else {
			tipo2 = rexp21(tipoH1);
//			tipo = tipo2;
			if (tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
				return tSintetiz.tError;
			}
			else {
				emite(op.toString());
				return tipo2;
			}
		}
	}
	
	public tSintetiz exp3() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.opVAbs)
			tipo1 = exp42();
		if (esOp41(tokActual.getTipoToken()))
			tipo1 = exp43();
		else
			tipo1 = exp41();
		tipoH = tipo1;
		if (tipoH == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (EXP4_1), (EXP4_2) o (EXP4_3).\n");
			return tSintetiz.tError;
		}
		else {
			//Aqu� es donde ven�a esta signaci�n => 'tipoH = tipo1;' Se opta por poner arriba
			if (tokActual.getTipoToken() == tToken.puntoyComa) {// Hemos llegado al fin de la instrucci�n
//				rexp32();
				return tipo1;
			}
			else {
				tipo2 = rexp31(tipoH);
				if (tipo2 == tSintetiz.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en expresi�n. Categor�a sint�ctica afectada: (REXP3_1).\n");
					return tSintetiz.tError;
				}
				else
					//No ser�a 'return tipo2;' ??
					//return tipo2;
					return tSintetiz.tNat;
			}
		}
	}
	
	public tSintetiz rexp31(tSintetiz tipoH) {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo, tipo1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		op = op3();
		tipo1 = exp3();
		tipo = dameTipo(tipo1,tipoH,op);
		if (tipo == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			emite(op.toString());
			return tipo;
		}
	}
	
	public tSintetiz exp41() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo, tipo1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		op = op41();
		tipo1 = term();
		tipo = dameTipo(tipo1,op);
		if (tipo == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operaci�n: '"+ op.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			emite(op.toString());
			return tipo;
		}
	}
	
	public tSintetiz exp42() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo, tipo1;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.opVAbs);
		tipo1 = term();
		tipo = dameTipo(tipo1,tOp.opVAbs);
		if (tipo == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operaci�n: '"+ tOp.opVAbs.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			emite(tToken.opVAbs.toString());
			consume(tToken.opVAbs);
			return tipo;
		}
	}
	
	public tSintetiz exp43() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1;
		//Cuerpo asociado a la funcionalidad de los no terminales
		tipo1 = term();
		return tipo1;
	}
	
	public tSintetiz term() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1;
		tipo1 = tSintetiz.tError;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.booleanoCierto)
			tipo1 = term1True();
		if (tokActual.getTipoToken() == tToken.booleanoFalso)
			tipo1 = term1False();
		if (tokActual.getTipoToken() == tToken.cadCaracteres)
			tipo1 = term2();
		if (tokActual.getTipoToken() == tToken.natural)
			tipo1 = term3();
		if (tokActual.getTipoToken() == tToken.entero)
			tipo1 = term4();
		if (tokActual.getTipoToken() == tToken.real)
			tipo1 = term5();
		if (tokActual.getTipoToken() == tToken.identificador)
			tipo1 = term6();
		if (tokActual.getTipoToken() == tToken.parApertura)
			tipo1 = term7();
		return tipo1;
	}
	
	public tSintetiz term1True() {
		emite("apila(" + true + ")");
		consume(tToken.booleanoCierto);
		return tSintetiz.tBool;
	}
	
	public tSintetiz term1False() {
		emite("apila(" + false + ")");
		consume(tToken.booleanoFalso);
		return tSintetiz.tBool;
	}
	
	public tSintetiz term2() {
		emite("apila(" + tokActual.getLexema() + ")");
		consume(tToken.cadCaracteres);
		return tSintetiz.tChar;
	}
	
	public tSintetiz term3() {
		emite("apila(" + tokActual.getLexema() + ")");
		consume(tToken.natural);
		return tSintetiz.tNat;
	}
	
	public tSintetiz term4() {
		emite("apila(" + tokActual.getLexema() + ")");
		consume(tToken.entero);
		return tSintetiz.tInt;
	}
	
	public tSintetiz term5() {
		emite("apila(" + tokActual.getLexema() + ")");
		consume(tToken.real);
		return tSintetiz.tFloat;
	}
	
	public tSintetiz term6() {
		//Declaraci�n de las variables necesarias
		String lexIden = new String();
		tSintetiz tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema())) {
			lexIden = tokActual.getLexema();
			//Ya tenemos todo lo necesario acerca del token, pues lo consumimos
			consume(tToken.identificador);
			//OBTENEMOS EL TIPO DEL IDENTIFICADOR DE LA TS//
			tipo = ts.getTabla().get(lexIden).getTipo();
			emite("apila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
			return tipo;
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba identificador, o si lo es no fue declarado previamente." + "\n");
			return tSintetiz.tError;
		}
	}
	
	public tSintetiz term7() {
		//Declaraci�n de las variables necesarias
		tSintetiz tipo1;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.parApertura);
		tipo1 = exp();
		consume(tToken.parCierre);
		return tipo1;
	}
	
	public tSintetiz dameTipo(tSintetiz tipoEXPIzq, tSintetiz tipoEXPDer, tOp op) {
		if (esOp0(op)) {
			if (tipoEXPIzq == tipoEXPDer || (esTipoNum(tipoEXPIzq) && esTipoNum(tipoEXPDer)))
				return tSintetiz.tBool;
			else
				return tSintetiz.tError;
		}
		if (esOp1(op)) {
			switch (op) {
			case oLogica:
				if (tipoEXPIzq == tSintetiz.tBool && tipoEXPIzq == tipoEXPDer)
					return tSintetiz.tBool;
				else
					return tSintetiz.tError;
			default:
				if (esTipoNum(tipoEXPIzq) && esTipoNum(tipoEXPDer))
					return dameTipoDom(tipoEXPIzq, tipoEXPDer);
			}
		
		}
		
		if (esOp2(op)) {
			switch (op) {
			case yLogica:
				if (tipoEXPIzq == tSintetiz.tBool && tipoEXPIzq == tipoEXPDer)
					return tSintetiz.tBool;
				else
					return tSintetiz.tError;
			default:
				if (esTipoNum(tipoEXPIzq) && esTipoNum(tipoEXPDer))
					return dameTipoDom(tipoEXPIzq, tipoEXPDer);
			}
		
		}
		
		return tSintetiz.tError;
		
//		switch (op) {
//		case menor:
//			if (tipoEXPIzq == tipoEXPDer)
//			return tSintetiz.tBool;
//		case menorIgual:
//			return tSintetiz.tChar;
//		case mayor:
//			return tSintetiz.tNat;
//		case mayorIgual:
//			return tSintetiz.tInt;
//		case igual:
//			return tSintetiz.tFloat;
//		case distinto:
//			return tSintetiz.tFloat;
//		default:
//			return tSintetiz.tError;
//		}
	}
	
	public tSintetiz dameTipo(tSintetiz tipoEXP, tOp op4) {
		switch (op4) {
		case negArit:
			if (esTipoNum(tipoEXP))
				return tipoEXP;
			else
				return tSintetiz.tError;
		case negLogica:
			if (tipoEXP == tSintetiz.tBool)
				return tipoEXP;
			else
				return tSintetiz.tError;
		case opVAbs:
			if (esTipoNum(tipoEXP) && !(tipoEXP == tSintetiz.tInt))
				return tipoEXP;
			if (tipoEXP == tSintetiz.tInt)
				return tSintetiz.tNat;
			return tSintetiz.tError;
		case castChar:
			if (tipoEXP == tSintetiz.tNat || tipoEXP == tSintetiz.tChar)
				return tSintetiz.tChar;
			return tSintetiz.tError;
		case castNat:
			if (tipoEXP == tSintetiz.tNat || tipoEXP == tSintetiz.tChar)
				return tSintetiz.tNat;
			return tSintetiz.tError;
		case castInt:
			if (esTipoNum(tipoEXP) || tipoEXP == tSintetiz.tChar)
				return tSintetiz.tInt;
			return tSintetiz.tError;
		case castFloat:
			if (esTipoNum(tipoEXP) || tipoEXP == tSintetiz.tChar)
				return tSintetiz.tFloat;
			return tSintetiz.tError;
		default:
			return tSintetiz.tError;
		}
	}
	
	public tSintetiz dameTipoDom(tSintetiz tipo1, tSintetiz tipo2) {
		if (tipo1 == tSintetiz.tFloat && esTipoNum(tipo2) || tipo2 == tSintetiz.tFloat && esTipoNum(tipo1))
			return tSintetiz.tFloat;
		if (tipo1 == tSintetiz.tInt && esTipoNatInt(tipo2) || tipo2 == tSintetiz.tInt && esTipoNatInt(tipo1))
			return tSintetiz.tInt;
		return tSintetiz.tNat;
	}
	
	public boolean esOp0(tOp op) {
		if (op == tOp.menor || op == tOp.menorIgual || op == tOp.mayor ||
				op == tOp.mayorIgual || op == tOp.igual || op == tOp.distinto)
			return true;
		else
			return false;
	}
	
	public boolean esOp1(tOp op) {
		if (op == tOp.suma || op == tOp.resta || op == tOp.oLogica)
			return true;
		else
			return false;
	}
	
	public boolean esOp2(tOp op) {
		if (op == tOp.multiplicacion || op == tOp.division || op == tOp.resto ||
				op == tOp.yLogica)
			return true;
		else
			return false;
	}
	
	public boolean esOp41(tToken tokOp) {
		if (tokOp == tToken.negArit || tokOp == tToken.negLogica || tokOp == tToken.castChar ||
				tokOp == tToken.castFloat || tokOp == tToken.castInt || tokOp == tToken.castNat)
			return true;
		else
			return false;
	}
	
	public tOp op0() {
		
		return tOp.igual;
	}
	
	public tOp op1() {
		
		return tOp.igual;
	}
	
	public tOp op2() {
		
		return tOp.igual;
	}
	public tOp op3() {
		
		return tOp.igual;
	}
	
	public tOp op41() {
		
		return tOp.igual;
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