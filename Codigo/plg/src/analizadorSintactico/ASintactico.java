package analizadorSintactico;

import java.util.*;

import analizadorLexico.*;
import tablaSimbolos.*;

public class ASintactico {
	
//	private ALexico scanner;
	private TS ts;
	//Elemento de preanálisis
	private Token tokActual;
	private Emit emit;
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
		emit = new Emit();
	}
	
	
	
	/*public Token token() {
		return tokActual;
	}*/
	
	public void emite(String inst) {
		instMPOut.add(inst);
	}
	
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
			emit.emit(Emit.STOP);

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
			emit.emit(Emit.STOP);

			return new Token(tToken.tokenError, "Se esperaba un token de tipo 'tipoDeVariable'.");
		}
	}
	
	public boolean esTokenTipo(Token t) {
		if (t.getTipoToken() == tToken.tipoVarBooleano ||
				t.getTipoToken() == tToken.tipoVarCaracter ||
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
			emit.emit(Emit.STOP);

		}
	}
	
	public void vaciaCod() {
		instMPOut.clear();
	}
	
	public tSintetiz dameTSintetiz(tToken tipo) {
		switch (tipo) {
		case tipoVarBooleano:
			return tSintetiz.tBool;
		case tipoVarCaracter:
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
			System.out.println();
			System.out.println();
			System.out.println("***********************************************************************");
			System.out.println("///////////////////////////////INTERPRETE//////////////////////////////");
			System.out.println("***********************************************************************");
			System.out.println();
			System.out.println();
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
		emit.emit(Emit.STOP);

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
			// faltan dos emits
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
		parOut.setTipo(dameTSintetiz(consumeTipo().getTipoToken()));
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
		if (errorProg || tokActual.getTipoToken() != tToken.finDeFichero)
			return true;
		else
			return false;
	}
	
	public ParBooleanInt sent(int etiqIn) {
		//Declaración de las variables necesarias
		
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.entradaTeclado) {
			return sread(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.salidaPantalla) {
			return swrite(etiqIn);
		}
		if (tokActual.getTipoToken() == tToken.identificador) {
			return sasign(etiqIn);
		}
		//Añadimos control de errores
		else {
			errorProg = true;
			System.out.println("Error: Se esperaba una de las siguientes instrucciones:\n" +
					"	- Asignación			=>  ':='\n" +
					"	- Entrada por teclado		=>  'in()'\n" +
					"	- Salida por pantalla 'out()'	=>  'out()'\n\n" +
					"Token en preanálisis: " + tokActual.getTipoToken() + "\n");
			return new ParBooleanInt(true, 0);
		}
//		consume(tToken.puntoyComa);
	}
	
	public ParBooleanInt swrite(int etiqIn) {
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
				return new ParBooleanInt(true, 0);
			}
			else {
				emite("escribir");
				emit.emit(Emit.ESCRIBIR);

				consume(tToken.parCierre);
				return false;
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se esperaba paréntesis de apertura después de operación de entrada" + "\n" +
					"por teclado.\n");
			return new ParBooleanInt(true, 0);
		}
	}
	
	public ParBooleanInt sread(int etiqIn) {
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
				emit.emit(Emit.LEER);
				emit.emit(emit.desapilaCode(ts.getTabla().get(lexIden).getTipo()), 
						new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));


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
	
	public ParBooleanInt sasign(int etiqIn) {
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
			if (tipo == tSintetiz.tError || !esCompatibleAsig(ts.getTabla().get(lexIden).getTipo(), tipo)) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la asignación: La expresión es errónea, o los tipos de la" + "\n" +
						"asignación son incompatibles." + "\n");
				return true;
			}
			else {
				emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				emit.emit(emit.desapilaCode(ts.getTabla().get(lexIden).getTipo()), 
						new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));
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
	
	public ParBooleanInt sif(int etiqIn) {
		//Declaración de las variables necesarias
		//tSintetiz tipo; //Ahora necesitamos guardar tipo y etiqueta
		ParTipoEtiq tipoEtiq = new ParTipoEtiq();
		ParBooleanInt errorEtiq1 = new ParBooleanInt();
		ParBooleanInt errorEtiq2 = new ParBooleanInt();
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token del while
		consume(tToken.ifC);
		//LLamada a la epx()
		tipoEtiq = exp(etiqIn);
		if (tipoEtiq.getTipo() != tSintetiz.tBool) { 
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la instrucción 'if': El tipo de la expresión a evaluar no es 'boolean'." +"\n");
			return new ParBooleanInt(true, 0);
		}
		else {
			//Consumimos el token del 'then'
			consume(tToken.thenC);
			//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
			//Instrucción a parchear
			emite("ir-f(?)");
			//Llamada a la sentencia que conforma el cuerpo del if
			errorEtiq1 = sent(tipoEtiq.getEtiq() + 1);
			if (errorEtiq1.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucción 'if'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
				emite("ir-a(?)");
				//Como ya tenemos la etiqueta de salida de 'sent()', 
				//podemos parchear el anterior 'ir-f()' del código a pila
				parchea(tipoEtiq.getEtiq(), errorEtiq1.getIntVal() + 1);
				//Llamada a pelse
				errorEtiq2 = pelse(errorEtiq1.getIntVal() + 1);
				if (errorEtiq2.getBooleanVal()) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en el cuerpo de la instrucción 'else'." + "\n");
					return new ParBooleanInt(true, 0);
				}
				else {
					//Como ya tenemos la etiqueta de salida de 'else()', 
					//podemos parchear el anterior 'ir-a()' del código a pila
					parchea(errorEtiq1.getIntVal(), errorEtiq2.getIntVal());
					return new ParBooleanInt(false, errorEtiq2.getIntVal());
				}
			}
		}
	}
	
	public ParBooleanInt pelse(int etiqIn) {
		//Declaración de las variables necesarias
		ParBooleanInt errorEtiq = new ParBooleanInt();
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Si no aparece el token 'else', consideramos que es sólo un if-then
		if (tokActual.getTipoToken() != tToken.elseC) {
			return new ParBooleanInt(false, etiqIn);
		}
		else {
			//Consumimos el token del 'else'
			consume(tToken.elseC);
			//Llamada a la sentencia que conforma el cuerpo del else
			errorEtiq = sent(etiqIn);
			if (errorEtiq.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucción 'if'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Devolvemos la nueva etiqueta
				return new ParBooleanInt(false, errorEtiq.getIntVal());
			}
		}
	}
	
	public ParBooleanInt swhile(int etiqIn) {
		//Declaración de las variables necesarias
		//tSintetiz tipo; //Ahora necesitamos guardar tipo y etiqueta
		ParTipoEtiq tipoEtiq = new ParTipoEtiq();
		ParBooleanInt errorEtiq = new ParBooleanInt();
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token del while
		consume(tToken.whileC);
		//LLamada a la epx()
		tipoEtiq = exp(etiqIn);
		if (tipoEtiq.getTipo() != tSintetiz.tBool) { 
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la instrucción 'while': El tipo de la expresión a evaluar no es 'boolean'." +"\n");
			return new ParBooleanInt(true, 0);
		}
		else {
			//Consumimos el token del 'do'
			consume(tToken.doC);
			//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
			//Instrucción a parchear
			emite("ir-f(?)");
			//Llamada a la sentencia que conforma el cuerpo del while
			errorEtiq = sent(tipoEtiq.getEtiq() + 1);
			if (errorEtiq.getBooleanVal()) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en el cuerpo de la instrucción 'while'." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
				emite("ir-a(" + etiqIn + ")");
				//Como ya tenemos la etiqueta de salida de 'sent()', 
				//podemos parchear el anterior 'ir-f()' del código a pila
				parchea(tipoEtiq.getEtiq(), errorEtiq.getIntVal() + 1);
				return new ParBooleanInt(false, errorEtiq.getIntVal() + 1);
			}
		}
	}
	
	public ParBooleanInt sfor(int etiqIn) {
		//Declaración de las variables necesarias
		//tSintetiz tipo; //Ahora necesitamos guardar tipo y etiqueta, 1 variable para cada expresión
		ParTipoEtiq tipoEtiq1 = new ParTipoEtiq();
		ParTipoEtiq tipoEtiq2 = new ParTipoEtiq();
		ParBooleanInt errorEtiq = new ParBooleanInt();
		String lexIden = new String();
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Consumimos el token del for
		consume(tToken.forC);
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema(), tClase.variable, new Integer(0))) {
			lexIden = tokActual.getLexema();
			consume(tToken.identificador);
			//Una vez parseado el identificador vamos con el simbolo '='
			consume(tToken.igual);
			//LLamada a la 1ª epx()
			tipoEtiq1 = exp(etiqIn);
			/////////////////////////////////////////////////////////////////////////
			//Falta cambiar el getTipo
			if (!(tipoEtiq1.getTipo() == tSintetiz.tNat || tipoEtiq1.getTipo() == tSintetiz.tInt) 
					|| !esCompatibleAsig(ts.getTabla().get(lexIden).getTipo(), tipoEtiq1.getTipo())) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la instrucción 'for': La 1ª expresión es errónea, o el tipo de la" + "\n" +
						"misma es incompatible con el del identificador." + "\n");
				return new ParBooleanInt(true, 0);
			}
			else {
				//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
				emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				//Instrucciones para la máquina virtual con 'emit'
				//emit.emit(emit.desapilaCode(ts.getTabla().get(lexIden).getTipo()), 
				//		new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));
				emite("apila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
				//Consumimos el token 'to'
				consume(tToken.toC);
				//LLamada a la 2ª epx()
				tipoEtiq2 = exp(tipoEtiq1.getEtiq() + 2);
				/////////////////////////////////////////////////////////////////////////
				//Falta cambiar el getTipo
				if (!(tipoEtiq2.getTipo() == tSintetiz.tNat || tipoEtiq2.getTipo() == tSintetiz.tInt) 
						|| !esCompatibleAsig(ts.getTabla().get(lexIden).getTipo(), tipoEtiq2.getTipo())) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en la instrucción 'for': La 2ª expresión es errónea, o el tipo de la" + "\n" +
							"misma es incompatible con el del identificador." + "\n");
					return new ParBooleanInt(true, 0);
				}
				else {
					//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
					//Se realiza la comparación sel contador del 'for'
					//Faltan las instrucciones para la máquina virtual con 'emit'
					emite("menorIgual");
					//Instrucción a parchear
					emite("ir-f(?)");
					//Consumimos el token del 'do'
					consume(tToken.doC);
					//Llamada a la sentencia que conforma el cuerpo del for
					errorEtiq = sent(tipoEtiq2.getEtiq() + 2);
					if (errorEtiq.getBooleanVal()) {
						errorProg = true;
						vaciaCod();
						System.out.println("Error en el cuerpo de la instrucción 'for'." + "\n");
						return new ParBooleanInt(true, 0);
					}
					else {
						//Como ya tenemos la etiqueta de salida de 'sent()', 
						//podemos parchear el anterior 'ir-f()' del código a pila
						parchea(tipoEtiq2.getEtiq() + 1, errorEtiq.getIntVal() + 5);
						//Emitimos las correspondientes instrucciones a pila ya que por ahora es todo correcto
						//Hay que incrementar el contador
						emite("apila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
						emite("apila(1)");
						emite("suma");
						emite("desapila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
						emite("ir-a(" + etiqIn + ")");
						return new ParBooleanInt(false, errorEtiq.getIntVal() + 5);
					}
				}
			}
		}
		else {
			errorProg = true;
			vaciaCod();
			System.out.println("Error: Se espera identificador declarado después de 'for'." + "\n" +
					"Token en preanálisis: " + tokActual.getLexema() + " Tipo de token: " + tokActual.getTipoToken() + "\n");
			return new ParBooleanInt(true, 0);
		}
	}
	
	public void parchea (int nInst, int etiq) {
		//Falta el código
	}
	
	public ParTipoEtiq exp(int etiqIn) {
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
			if (esTokenOp0(tokActual.getTipoToken())) {
				tipo2 = rexp1(tipoH);
				if (tipo2 == tSintetiz.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en expresión. Categoría sintáctica afectada: (REXP_1).\n");
					return tSintetiz.tError;
				}
				else
					return tSintetiz.tBool;
			}
			else {
				// Hemos llegado al fin de la instrucción
				return tipoH;
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
			System.out.println("Error de tipos en operación de igualdad: '"+ op.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			// TODO
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));

			return tipo;
		}	
	}
	
	public tSintetiz exp1() {
		//Declaración de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx2()
		tipo1 = exp2();
		tipoH = tipo1;
		if (esTokenOp1(tokActual.getTipoToken())) {
			tipo2 = rexp11(tipoH);
			if (tipo1 == tSintetiz.tError || tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en expresión. Categoría sintáctica afectada: (REXP1_1).\n");
				return tSintetiz.tError;
			}
			else
				return tipo2;
		}
		else {
			// Hemos llegado al fin de la instrucción
			return tipo1;
		}
	}
	
	public tSintetiz rexp11(tSintetiz tipoH) {
		//Declaración de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op1();
		tipo1 = exp2();
		tipoH1 = dameTipo(tipoH,tipo1,op);
		if (tipoH1 != tSintetiz.tError) {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
		}
		if (esTokenOp1(tokActual.getTipoToken())) {
			tipo2 = rexp11(tipoH1);
			if (tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la operación: '"+ op.toString() +"'.\n");
				return tSintetiz.tError;
			}
			else {
				return tipo2;
			}
		}
		else {
			// Hemos llegado al fin de la instrucción
			return tipoH1;
		}
	}
	
	public tSintetiz exp2() {
		//Declaración de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//LLamada a epx3()
		tipo1 = exp3();
		tipoH = tipo1;
		if (esTokenOp2(tokActual.getTipoToken())) {
			tipo2 = rexp21(tipoH);
			if (tipo1 == tSintetiz.tError || tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en expresión. Categoría sintáctica afectada: (REXP2_1).\n");
				return tSintetiz.tError;
			}
			else
				return tipo2;
		}
		else {
			// Hemos llegado al fin de la instrucción
			return tipo1;
		}
	}
	
	public tSintetiz rexp21(tSintetiz tipoH) {
		//Declaración de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		//Necesitamos obtener el operador concreto
		op = op2();
		tipo1 = exp3();
		tipoH1 = dameTipo(tipoH,tipo1,op);
		if (tipoH1 != tSintetiz.tError) {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
		}
		if (esTokenOp2(tokActual.getTipoToken())) {
			tipo2 = rexp21(tipoH1);
			if (tipo2 == tSintetiz.tError) {
				errorProg = true;
				vaciaCod();
				System.out.println("Error en la operación: '"+ op.toString() +"'.\n");
				return tSintetiz.tError;
			}
			else {
				return tipo2;
			}
		}
		else {
			// Hemos llegado al fin de la instrucción
			return tipoH1;
		}
	}
	
	public tSintetiz exp3() {
		//Declaración de las variables necesarias
		tSintetiz tipo1, tipo2, tipoH;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.opVAbs)
			tipo1 = exp42();
		else
			if (esTokenOp41(tokActual.getTipoToken()))
				tipo1 = exp41();
			else
				tipo1 = exp43();
//Antes
//		else
//			tipo1 = exp43();
		tipoH = tipo1;
		if (tipoH == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en expresión. Categoría sintáctica afectada: (EXP4_1), (EXP4_2) o (EXP4_3).\n");
			return tSintetiz.tError;
		}
		else {
			//Aquí es donde venía esta signación => 'tipoH = tipo1;' Se opta por poner arriba
			if (esTokenOp3(tokActual.getTipoToken())) {
				tipo2 = rexp31(tipoH);
				if (tipo2 == tSintetiz.tError) {
					errorProg = true;
					vaciaCod();
					System.out.println("Error en expresión. Categoría sintáctica afectada: (REXP3_1).\n");
					return tSintetiz.tError;
				}
				else
					return tSintetiz.tNat;
			}
			else {
				// Hemos llegado al fin de la instrucción
				return tipo1;
			}
		}
	}
	
	public tSintetiz rexp31(tSintetiz tipoH) {
		//Declaración de las variables necesarias
		tSintetiz tipo, tipo1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		op = op3();
		tipo1 = exp3();
		tipo = dameTipo(tipo1,tipoH,op);
		if (tipo == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operación: '"+ op.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));
			return tipo;
		}
	}
	
	public tSintetiz exp41() {
		//Declaración de las variables necesarias
		tSintetiz tipo, tipo1;
		tOp op;
		//Cuerpo asociado a la funcionalidad de los no terminales
		op = op41();
		tipo1 = term();
		tipo = dameTipo(tipo1,op);
		if (tipo == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operación: '"+ op.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			emite(op.toString());
			emit.emit(opUtils.getOperationCode(op));

			return tipo;
		}
	}
	
	public tSintetiz exp42() {
		//Declaración de las variables necesarias
		tSintetiz tipo, tipo1;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.opVAbs);
		//tipo1 = term();
		tipo1 = exp();
		tipo = dameTipo(tipo1,tOp.opVAbs);
		if (tipo == tSintetiz.tError) {
			errorProg = true;
			vaciaCod();
			System.out.println("Error en la operación: '"+ tOp.opVAbs.toString() +"'.\n");
			return tSintetiz.tError;
		}
		else {
			emite(tToken.opVAbs.toString());
			emit.emit(opUtils.getOperationCode(tOp.opVAbs));

			consume(tToken.opVAbs);
			return tipo;
		}
	}
	
	public tSintetiz exp43() {
		//Declaración de las variables necesarias
		tSintetiz tipo1;
		//Cuerpo asociado a la funcionalidad de los no terminales
		tipo1 = term();
		return tipo1;
	}
	
	public tSintetiz term() {
		//Declaración de las variables necesarias
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.booleanoCierto)
			return term1True();
		if (tokActual.getTipoToken() == tToken.booleanoFalso)
			return term1False();
		if (tokActual.getTipoToken() == tToken.caracter)
			return term2();
		if (tokActual.getTipoToken() == tToken.natural)
			return term3();
		if (tokActual.getTipoToken() == tToken.entero)
			return term4();
		if (tokActual.getTipoToken() == tToken.real)
			return term5();
		if (tokActual.getTipoToken() == tToken.identificador)
			return term6();
		if (tokActual.getTipoToken() == tToken.parApertura)
			return term7();
		return tSintetiz.tError;
	}
	
	public tSintetiz term1True() {
		emit.emit(Emit.APILA, new Token(tToken.booleano,"true"));

		emite("apila(" + true + ")");
		consume(tToken.booleanoCierto);
		return tSintetiz.tBool;
	}
	
	public tSintetiz term1False() {
		emite("apila(" + false + ")");
		emit.emit(Emit.APILA, new Token(tToken.booleano,"false"));
		consume(tToken.booleanoFalso);
		return tSintetiz.tBool;
	}
	
	public tSintetiz term2() {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.caracter,tokActual.getLexema()));

		consume(tToken.caracter);
		return tSintetiz.tChar;
	}
	
	public tSintetiz term3() {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.natural,tokActual.getLexema()));

		consume(tToken.natural);
		return tSintetiz.tNat;
	}
	
	public tSintetiz term4() {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.entero,tokActual.getLexema()));

		consume(tToken.entero);
		return tSintetiz.tInt;
	}
	
	public tSintetiz term5() {
		emite("apila(" + tokActual.getLexema() + ")");
		emit.emit(Emit.APILA, new Token(tToken.real,tokActual.getLexema()));
		consume(tToken.real);
		return tSintetiz.tFloat;
	}
	
	public tSintetiz term6() {
		//Declaración de las variables necesarias
		String lexIden = new String();
		tSintetiz tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.identificador &&
				ts.existeId(tokActual.getLexema(), tClase.variable, new Integer(0))) {
			lexIden = tokActual.getLexema();
			//Ya tenemos todo lo necesario acerca del token, pues lo consumimos
			consume(tToken.identificador);
			//OBTENEMOS EL TIPO DEL IDENTIFICADOR DE LA TS//
			tipo = ts.getTabla().get(lexIden).getTipo();
			emite("apila_dir(" + ts.getTabla().get(lexIden).getDirM() + ")");
			emit.emit(Emit.APILA_DIR, new Token(tToken.natural,""+ts.getTabla().get(lexIden).getDirM()));

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
		//Declaración de las variables necesarias
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
		
		if (esOp3(op)) {
			if (tipoEXPIzq == tSintetiz.tNat && tipoEXPDer == tSintetiz.tNat )
				return tSintetiz.tNat;
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
			if (esTipoNum(tipoEXP) && tipoEXP != tSintetiz.tNat)
				return tipoEXP;
			if (tipoEXP == tSintetiz.tNat)
				return tSintetiz.tInt;
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
	
	public boolean esTokenOp0(tToken tokOp) {
		if (tokOp == tToken.menor || tokOp == tToken.menorIgual || tokOp == tToken.mayor ||
				tokOp == tToken.mayorIgual || tokOp == tToken.igual || tokOp == tToken.distinto)
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
	
	public boolean esTokenOp1(tToken tokOp) {
		if (tokOp == tToken.suma || tokOp == tToken.resta || tokOp == tToken.oLogica)
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
	
	public boolean esTokenOp2(tToken tokOp) {
		if (tokOp == tToken.multiplicacion || tokOp == tToken.division || tokOp == tToken.resto ||
				tokOp == tToken.yLogica)
			return true;
		else
			return false;
	}
	
	public boolean esOp3(tOp op) {
		if (op == tOp.despIzq || op == tOp.despDer)
			return true;
		else
			return false;
	}
	
	public boolean esTokenOp3(tToken tokOp) {
		if (tokOp == tToken.despIzq || tokOp == tToken.despDer)
			return true;
		else
			return false;
	}
	
	public boolean esTokenOp41(tToken tokOp) {
		if (tokOp == tToken.negArit || tokOp == tToken.negLogica || tokOp == tToken.castChar ||
				tokOp == tToken.castFloat || tokOp == tToken.castInt || tokOp == tToken.castNat)
			return true;
		else
			return false;
	}
	
	public tOp op0() {
		switch (tokActual.getTipoToken()) {
		case menor:
			consume(tToken.menor);
			return tOp.menor;
		case menorIgual:
			consume(tToken.menorIgual);
			return tOp.menorIgual;
		case mayor:
			consume(tToken.mayor);
			return tOp.mayor;
		case mayorIgual:
			consume(tToken.mayorIgual);
			return tOp.mayorIgual;
		case igual:
			consume(tToken.igual);
			return tOp.igual;
		case distinto:
			consume(tToken.distinto);
			return tOp.distinto;	
		default:
			return tOp.error;
		}
	}
	
	public tOp op1() {
		switch (tokActual.getTipoToken()) {
		case suma:
			consume(tToken.suma);
			return tOp.suma;
		case resta:
			consume(tToken.resta);
			return tOp.resta;
		case oLogica:
			consume(tToken.oLogica);
			return tOp.oLogica;
		default:
			return tOp.error;
		}
	}
	
	public tOp op2() {
		switch (tokActual.getTipoToken()) {
		case multiplicacion:
			consume(tToken.multiplicacion);
			return tOp.multiplicacion;
		case division:
			consume(tToken.division);
			return tOp.division;
		case resto:
			consume(tToken.resto);
			return tOp.resto;
		case yLogica:
			consume(tToken.yLogica);
			return tOp.yLogica;
		default:
			return tOp.error;
		}
	}
	
	public tOp op3() {
		switch (tokActual.getTipoToken()) {
		case despIzq:
			consume(tToken.despIzq);
			return tOp.despIzq;
		case despDer:
			consume(tToken.despDer);
			return tOp.despDer;
		default:
			return tOp.error;
		}
	}
	
	public tOp op41() {
		switch (tokActual.getTipoToken()) {
		case negArit:
			consume(tToken.negArit);
			return tOp.negArit;
		case negLogica:
			consume(tToken.negLogica);
			return tOp.negLogica;
		case castChar:
			consume(tToken.castChar);
			return tOp.castChar;
		case castNat:
			consume(tToken.castNat);
			return tOp.castNat;
		case castInt:
			consume(tToken.castInt);
			return tOp.castInt;
		case castFloat:
			consume(tToken.castFloat);
			return tOp.castFloat;
		default:
			return tOp.error;
		}
	}
	
//	public void rexp2() {
//		
//	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombreFichero = "programa10.txt";
		
		ALexico scanner = new ALexico();
		ASintactico parser = new ASintactico();
		if (scanner.scanFichero(nombreFichero)) {
			parser.tokensIn = scanner.dameTokens();
			parser.tokActual = parser.tokensIn.get(parser.contTokens);
			parser.parse();
			parser.emit.write("programa.bin");
		}
	}
}