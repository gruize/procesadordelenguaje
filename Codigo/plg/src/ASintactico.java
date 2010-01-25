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
		Token tokDevolver = tokensIn.get(contTokens);
		contTokens++;
		tokActual = tokensIn.get(contTokens);
		return tokDevolver;
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
					tokenEsperado.toString() + "'.");
//�Esto ser�a una buena forma de abortar la ejecuci�n?
			errorProg = true;
			emite("stop");
		}
	}
	
	public void vaciaCod() {
		instMPOut.clear();
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
		System.out.println("Tabla de S�mbolos");
		System.out.println("-----------------");
		System.out.println();
		//Mostramos la informaci�n de la tabla de s�mbolos		
		e = ts.getTabla().keys();
		while (e.hasMoreElements()) {
			id = e.nextElement();
			if (ts.getTabla().get(id).getTipo().equals("tipoVarCadCaracteres"))
				System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo() + "\tDirecci�n: " +
						ts.getTabla().get(id).getDirM());
			else
				System.out.println("Id: " + id + "\t\tTipo: " + ts.getTabla().get(id).getTipo() + "\t\tDirecci�n: " +
						ts.getTabla().get(id).getDirM());
		}
		if (errorProg) {
			System.out.println();
			System.out.println();
			System.out.println("Error en el an�lisis.");
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
			return new ParBooleanInt(true, 0);
		else {
			ts.anadeId(id_tipo.getStr1(), id_tipo.getStr2(), errorDec1_dir1.getIntVal());
			return new ParBooleanInt(false, errorDec1_dir1.getIntVal() + 1);
		}
	}
	
	//Devolvemos el par: (error = false, dir = 0)
	public ParBooleanInt rdecs2() {
		//La tabla se crea en el constructor, sino la crear�amos aqu�
		return new ParBooleanInt(false, 0);
	}
	
	public ParString dec() {
		ParString parOut = new ParString();
		parOut.setStr1(consume().getLexema());
		consume(tToken.dosPuntos);
		parOut.setStr2(consume().getTipoToken().toString());
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
		return false;
	}
	
	public boolean sent() {
		//Declaraci�n de las variables necesarias
		boolean errorSent1 = false;
		//Cuerpo asociado a la funcionalidad de los no terminales
		if (tokActual.getTipoToken() == tToken.entradaTeclado) {
			errorSent1 = sread();
		}
		if (tokActual.getTipoToken() == tToken.salidaPantalla) {
			errorSent1 = swrite();
		}
		if (tokActual.getTipoToken() == tToken.asignacion) {
			errorSent1 = sasign();
		}
		consume(tToken.puntoyComa);
		return errorSent1;
	}
	
	public boolean swrite() {
		//Declaraci�n de las variables necesarias
		tipoSint tipo;
		//Cuerpo asociado a la funcionalidad de los no terminales
		consume(tToken.salidaPantalla);
		tipo = exp();
		if (tipo == tipoSint.tError)
			vaciaCod();
		else
			emite("escribir");
		consume(tToken.parCierre);
		return (tipo == tipoSint.tError);
	}
	
	public boolean sread() {
		//Declaraci�n de las variables necesarias
		boolean errorSent = false;
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
			}
			else {
				errorSent = true;
				vaciaCod();
				System.out.println("Error: El par�metro de la operaci�n 'in' debe ser un identificador, o" + "\n" +
						"el identificador no existe en la tabla de s�mbolos.");
			}	
		}
		else {
			errorSent = true;
			vaciaCod();
			System.out.println("Error: Se esperaba par�ntesis de apertura despu�s de operaci�n de entrada" + "\n" +
					"por teclado.");
		}
//		consume(�in�) // in
//		iden(out lexema)
//		errorSent = not existeID(ts, lexema)
//		si errorSent
//		entonces
//			vaciaCod()
//		si no
//			emit(leer)
//			emit(desapila_dir(damePropiedadesTS(tsh, lexema).dirProp));
//		fin si
//		consume (�)�) // (
		consume(tToken.parCierre);
		return errorSent;
	}
	
	public boolean sasign() {
//		iden(out lexema)
//		consume (�:=�)
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
//			si token pertenece {; )} // fin de instrucci�n
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
			parser.parse();
		}
	}
}