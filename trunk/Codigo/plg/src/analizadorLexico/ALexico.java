package analizadorLexico;
import java.util.*;
import java.io.*;

enum est {e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, 
	e17, e18, e19, e20, e21, e22, e23, e24, e25, e26, e27};

public class ALexico {

	private char buff[];
	private String lex;
	private est estado;
	private Vector<Token> tokensOut;
	private BufferedReader bfr;
	private int contPrograma;
	private boolean errorLex;
	private String descripError;
	private boolean quedanCar;
	private Vector<String> palReservadas;
	private char carAntConsumido[];
	private boolean finFichero;
//	private est estAnterior;
//	private boolean esCast;
	
	public ALexico() {
		// TODO Auto-generated constructor stub
		buff = new char[1];
		lex = new String();
		tokensOut = new Vector<Token>();
		contPrograma = 1;
		errorLex = false;
		descripError = new String();
		quedanCar = true;
		palReservadas = new Vector<String>();
		iniciaVecPalReservadas();
		carAntConsumido = new char[1];
		finFichero = false;
		estado=est.e0;
//		estAnterior=est.e0;

//		esCast = false;
	}
	
	public Vector<Token> dameTokens(){
		return tokensOut;
	}
	
	public void iniciaVecPalReservadas() {
		palReservadas.clear();
		//Primero añadimos los tipos
		palReservadas.add("boolean");
		palReservadas.add("character");
		palReservadas.add("natural");
		palReservadas.add("integer");
		//Palabra reservada para el tipo y el operador de cast
		palReservadas.add("float");
		//Valores booleanos
		palReservadas.add("true");
		palReservadas.add("false");
		//Operadores booleanos
		palReservadas.add("and");
		palReservadas.add("or");
		palReservadas.add("not");
		//Operadores de entrada / salida
		palReservadas.add("in");
		palReservadas.add("out");
		//Operadores de casting
		palReservadas.add("char");
		palReservadas.add("nat");
		palReservadas.add("int");
		//Palabras reservadas para la parte del 2º Cuat
		//Instrucciones de control
		palReservadas.add("if");
		palReservadas.add("then");
		palReservadas.add("else");
		palReservadas.add("null");
		palReservadas.add("record");
		palReservadas.add("array");
		palReservadas.add("of");
		palReservadas.add("pointer");
		palReservadas.add("tipo");
		palReservadas.add("procedure");
		palReservadas.add("var");
		palReservadas.add("dispose");
		palReservadas.add("while");
		palReservadas.add("for");
		palReservadas.add("to");
		palReservadas.add("do");
		palReservadas.add("forward");
		palReservadas.add("new");
	}
	
	public void inicio(String nomFichero) {
		try {
			bfr=new BufferedReader(new FileReader(nomFichero));
			bfr.read(buff);
			carAntConsumido[0] = ' ';
			contPrograma = 1;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void resetearFichero() {
		try {
			bfr.reset();
			bfr.read(buff);
			contPrograma = 1;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void scan() {
		Token tok = new Token();
		double realAux = 0;
		
		quedanCar = true;
		finFichero = false;
		iniciaScanner();
		while (quedanCar && !errorLex) {
			if (finFichero && estado == est.e0)	{
				quedanCar = false;
				tokensOut.add(new Token(tToken.finDeFichero));
			}
			else {
				switch (estado) {
					case e0:
						if (esBlanFLinTab(buff[0])) {
							carAntConsumido[0] = buff[0];
							transita(est.e0);
							lex = "";
							break;
						}
						if (esLetra(buff[0])) {
							transita(est.e3);
							break;
						}
						if (buff[0] == '0') {
							carAntConsumido[0] = buff[0];
							transita(est.e4);
							break;
						}
						if (esDigitoNo0(buff[0])) {
							carAntConsumido[0] = buff[0];
							transita(est.e5);
							break;
						}
						if (buff[0] == ':') {
							carAntConsumido[0] = buff[0];
							transita(est.e15);
							break;
						}
						//Nuevo tratamiento del símbolo '-'
						if (buff[0] == '-' || buff.toString().equals("-")) {
							carAntConsumido[0] = buff[0];
							transita(est.e6);
							break;
						}
						///////////////////////////////////
						if (buff[0] == '&' || buff[0] == ';' || buff[0] == '+' || buff[0] == '.' ||	buff[0] == ',' ||	//buff[0] == '-' || buff.toString().equals("-")
								buff[0] == '*' || buff[0] == '/' || buff[0] == '(' || buff[0] == '|' ||
								buff[0] == ')' || buff[0] =='{' || buff[0] =='}' || buff[0] =='['|| buff[0] ==']') {
							carAntConsumido[0] = buff[0];
							tok = dameToken(buff[0]);
							transita(est.e27);
							break;
						}
						if (buff[0] == '<') {
							carAntConsumido[0] = buff[0];
							transita(est.e21);
							break;
						}
						if (buff[0] == '>') {
							carAntConsumido[0] = buff[0];
							transita(est.e24);
							break;
						}
						if (buff[0] == '=') {
							carAntConsumido[0] = buff[0];
							transita(est.e17);
							break;
						}
						if (buff[0] == '#') {
							carAntConsumido[0] = buff[0];
							transita(est.e1);
							break;
						}
						if (buff[0] == '\'') {
							carAntConsumido[0] = buff[0];
							transita(est.e13);
							break;
						}
						else
							error(null);
						break;	
					case e1:
						if (esFLin(buff[0])) {
							transita(est.e0);
							lex = "";
							break;
						}
						if (finFichero) {
							iniciaScanner();
							break;
						}
						else
							transita(est.e1);
						break;
					case e2:
						//Sobra (en el autómata estaba pensado para guardar algo relativo al comentario)
						break;
					case e3:
						if (esLetra(buff[0]) || esDigito(buff[0])) {
							transita(est.e3);
						}
						else {
							if (palReservadas.contains(lex)) {
								//Resolvemos problemas entre identificadores y las operaciones de cast,
								//Distinguimos aquí también el caso del float como operador y como tipo
								if (esOpCast(lex)) {
									if (buff[0] == ')' && 
											tokensOut.lastElement().getTipoToken() == tToken.parApertura &&
											carAntConsumido[0] == '(') {
										tokensOut.remove(tokensOut.size() - 1);
										tokensOut.add(dameTokenPalReservada(lex));
										transita(est.e0);
										lex = "";
										break;
									}
									if (lex.equals("float") &&
											(tokensOut.lastElement().getTipoToken() == tToken.dosPuntos || 
											tokensOut.lastElement().getTipoToken() == tToken.ofT ||
											tokensOut.lastElement().getTipoToken() == tToken.pointerT)) {
										tokensOut.add(new Token(tToken.tipoVarReal));
										iniciaScanner();
										break;
									}
									else {
										if (lex.equals("float"))
											error("Operador de 'cast float' mal formado, o declaración incorrecta de tipo 'float'.");
										else
											error("Operador de 'cast " + lex + "' mal formado.");
										tokensOut.add(new Token());
										break;
									}
								}
	//Resolvemos problemas entre identificadores y operaciones de entrada salida
	//							if (lex == "in") {
	//							}
								else {
									tokensOut.add(dameTokenPalReservada(lex));
									iniciaScanner();
								}
							}
							else {
								tokensOut.add(dameTokenIdentificador(lex));
								iniciaScanner();
							}
						}
						break;
					case e4:
						if (buff[0] == '.') {
							transita(est.e8);
							break;
						}
						if (buff[0] == '0') {
							transita(est.e4);
							break;
						}
						//Admitiremos números como 0005556 o hasta algo como 00000
						if (esDigitoNo0(buff[0])) {
							transita(est.e5);
							break;
						}
						if (esE(buff[0])) {
							transita(est.e10);
							break;
						}
						//Guardamos un 0
						else {
							realAux = Double.valueOf(lex).doubleValue();
							tokensOut.add(new Token(tToken.natural, "" + (int)realAux + ""));
							iniciaScanner();
						}
						break;
					case e5:
						if (esDigito(buff[0])) {
							transita(est.e5);
							break;
						}
						if (buff[0] == '.') {
							transita(est.e8);
							break;
						}
						if (esE(buff[0])) {
							transita(est.e10);
							break;
						}
						else {
							realAux = Double.valueOf(lex).doubleValue();
							if (realAux <= Integer.MAX_VALUE) {
								tokensOut.add(new Token(tToken.natural, "" + (int)realAux + ""));
								iniciaScanner();
								break;
							}
							if (realAux <= Double.MAX_VALUE) {
								tokensOut.add(new Token(tToken.real, "" + realAux + ""));
								iniciaScanner();
								break;
							}
							error("Número demasiado grande.");
						}
						break;		
					case e6:
						if (buff[0] == '>') {
							tok = new Token(tToken.puntero);
							transita (est.e27);
							break;
						}
						else {
							tokensOut.add(dameToken(carAntConsumido[0]));
							iniciaScanner();
						}
						break;
					case e7:
						//Sobra (en el autómata estaba preparado para los enteros)
						break;
					case e8:
						if (esDigito(buff[0])) {
							transita(est.e9);
							break;
						}
						if (esE(buff[0])) {
							transita(est.e10);
							break;
						}
						else
							//Sobra realmente??
							error("Después de '.' sólo debe haber dígitos, ó 'e' ó 'E'.");
						break;
					case e9:
						if (esDigito(buff[0])) {
							transita(est.e9);
							break;
						}
						if (esE(buff[0])) {
							transita(est.e10);
							break;
						}
						else {
							realAux = Double.valueOf(lex).doubleValue();
							if (realAux <= Double.MAX_VALUE) {
								/*if (realAux == 0) {
									tokensOut.add(new Token(tToken.real, "" + realAux + ""));
									iniciaScanner();
									break;
								}
								if (Math.floor(realAux) == realAux) {
									if (Math.floor(realAux) > Integer.MAX_VALUE) {
										tokensOut.add(new Token(tToken.real, "" + realAux + ""));
										iniciaScanner();
										break;
									}
									else {	
										tokensOut.add(new Token(tToken.natural, "" + (int)realAux + ""));
										iniciaScanner();
										break;
									}
								}*/
								tokensOut.add(new Token(tToken.real, "" + realAux + ""));
								iniciaScanner();
								break;
							}
							else
									error("Número demasiado grande.");
						}
						break;
					case e10:
						if (esDigito(buff[0])) {
							transita(est.e12);
							break;
						}
						if (buff[0] == '-') {
							transita(est.e11);
							break;
						}
						else
							error("Después de 'e' ó 'E' sólo debe haber dígitos ó '-'.");
						break;
					case e11:
						if (esDigito(buff[0])) {
							transita(est.e12);
							break;
						}
						else
							error("Después de un - en el exponente sólo debe haber dígitos.");	
						break;
					case e12:
						if (esDigito(buff[0])) {
							transita(est.e12);
							break;
						}
						else {
							realAux = Double.valueOf(lex).doubleValue();
							if (realAux <= Double.MAX_VALUE) {
								/*if (realAux == 0 && estAntNat(estAnterior)) {
									tokensOut.add(new Token(tToken.natural, "0"));
									iniciaScanner();
									break;
								}
								if (Math.floor(realAux) == realAux && estAntNat(estAnterior)) {
									if (Math.floor(realAux) > Integer.MAX_VALUE) {
										tokensOut.add(new Token(tToken.real, "" + realAux + ""));
										iniciaScanner();
										break;
									}
									else {
										tokensOut.add(new Token(tToken.natural, "" + (int)realAux + ""));
										iniciaScanner();
										break;
									}	
								}*/
								tokensOut.add(new Token(tToken.real, "" + realAux + ""));
								iniciaScanner();
								break;
							}
							else
								error("Número demasiado grande.");
						}
						break;
					case e13:
						carAntConsumido[0] = buff[0];
						transita(est.e14);
						/*if (buff[0] == '\'') {
							tokensOut.add(dameTokenCaracter(lex)); 		//tokensOut.add(dameTokenCadCaracteres(lex));
							transita(est.e0);
							lex = "";
							break;
						}
						else
							transita(est.e13);*/
						break;
					case e14:
						if (buff[0] != '\'') {
							error("Se esperaba el caracter `'´.");
							break;
						}
						else {
							tokensOut.add(dameTokenCaracter("" + carAntConsumido[0] + ""));
							transita(est.e0);
							lex = "";
							break;
						}
					case e15:
						if (buff[0] == '=') {
							tok = new Token(tToken.asignacion);
							transita (est.e27);
							break;
						}
						else {
							tokensOut.add(new Token(tToken.dosPuntos));
							iniciaScanner();
						}
						break;
					case e16:
						//Sobra (en el automata se encarga del reconocimiento del '=' para la asignación)
						break;
					case e17:
						if (buff[0] == '/') {
							transita (est.e18);
							break;
						}
						else {
							tokensOut.add(new Token(tToken.igual));
							iniciaScanner();
						}
						break;
					case e18:
						if (buff[0] == '=') {
							tok = new Token(tToken.distinto);
							transita (est.e27);
							break;
						}
						else
							error("Operador de desigualdad mal formado.");
						break;
					case e19:
						//Sobra 
						break;
					case e20:
						//Sobra 
						break;
					case e21:
						if (buff[0] == '=') {
							tok = new Token(tToken.menorIgual);
							transita (est.e27);
							break;
						}
						if (buff[0] == '<') {
							tok = new Token(tToken.despIzq);
							transita (est.e27);
							break;
						}
						else {
							tokensOut.add(new Token(tToken.menor));
							iniciaScanner();
						}
						break;
					case e22:
						//Sobra 
						break;
					case e23:
						//Sobra 
						break;
					case e24:
						if (buff[0] == '=') {
							tok = new Token(tToken.mayorIgual);
							transita (est.e27);
							break;
						}
						if (buff[0] == '>') {
							tok = new Token(tToken.despDer);
							transita (est.e27);
							break;
						}
						else {
							tokensOut.add(new Token(tToken.mayor));
							iniciaScanner();
						}
						break;
					case e25:
						//Sobra 
						break;
					case e26:
						//Sobra 
						break;
					case e27:
						tokensOut.add(tok);
						iniciaScanner();
						break;
					default:
						errorLex = true;
				}
			}
		}
	}
	
	public void transita(est estSig) {
		try {
			//No añadimos las comillas simples en los tipo character
			if (buff[0] != '\'')
				lex = lex + buff[0];
			if (buff[0] == '\n')
				contPrograma++;
			if (bfr.read(buff) == -1) {
				finFichero = true;
				buff[0] = ' ';
			}
//			estAnterior = estado;
			estado = estSig;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void iniciaScanner(){
		//Se usa para volver al estado 0 sin consumir ningún caracter.
		lex = "";
		estado = est.e0;
	}
	
	public boolean esLetra(char car) {
		if ((car >= 'a' && car <= 'z') || (car >= 'A' && car <= 'Z'))
			return true;
		else
			return false;
	}
	
	public boolean esDigito(char car) {
		if (car >= '0' && car <= '9')
			return true;
		else
			return false;
	}
	
	public boolean esDigitoNo0(char car) {
		if (car >= '1' && car <= '9')
			return true;
		else
			return false;
	}
	
	public boolean esBlanFLinTab(char car) {
		if (buff[0] == ' ' || buff[0] == '\r' || buff[0] == '\n' || buff[0] == '\t')
			return true;
		else
			return false;
	}
	
	public boolean esFLin(char car) {
		if (buff[0] == '\r' || buff[0] == '\n')
			return true;
		else
			return false;
	}
	
	public boolean esE(char car) {
		if (buff[0] == 'e' || buff[0] == 'E')
			return true;
		else
			return false;
	}

	public boolean esOpCast(String lexema) {
		if (lexema.equals("char") || lexema.equals("int") || lexema.equals("nat") ||
				lexema.equals("float"))
			return true;
		else
			return false;
	}
	
	public boolean estAntNat(est _estado) {
		if (_estado == est.e4 || _estado == est.e5)
			return true;
		else
			return false;
	}
	 
	
	public void error(String comentario) {
		if (comentario == null)
			descripError = "Caracter inesperado en la linea " + contPrograma + " : '" + buff[0] + "'\n";
		else
			descripError = "Caracter en buffer: '" + buff[0] + "'. Linea: " + contPrograma + '\n' +
					"Error: " + comentario;
		errorLex = true;
	}
	
	public void errorCarAnt(String comentario) {
		if (comentario == null)
			descripError = "Caracter inesperado en la linea " + contPrograma + " : '" + carAntConsumido[0] + "'\n";
		else
			descripError = "Caracter en buffer: '" + carAntConsumido[0] + "'. Linea: " + contPrograma + '\n' +
					"Error: " + comentario;
		errorLex = true;
	}
	
	public Token dameToken(char car) {
		switch (car) {
		case '&': 
			return new Token(tToken.separador);	
		case ';':
			return new Token(tToken.puntoyComa);
		case '+':
			return new Token(tToken.suma);
		case '-':
			//Distinguimos el caso del - unario
			if (!tokensOut.isEmpty()) 
				if (tokensOut.lastElement().getTipoToken() == tToken.natural || 
						tokensOut.lastElement().getTipoToken() == tToken.entero ||
						tokensOut.lastElement().getTipoToken() == tToken.real ||
						tokensOut.lastElement().getTipoToken() == tToken.parCierre ||
						tokensOut.lastElement().getTipoToken() == tToken.identificador)
					return new Token(tToken.resta);
				else
					return new Token(tToken.negArit);
			else
				errorCarAnt(null);
			break;
		case '*':
			return new Token(tToken.multiplicacion);
		case '/':
			return new Token(tToken.division);
		case '%':
			return new Token(tToken.resto);
		case '(':
			return new Token(tToken.parApertura);
		case ')':
			return new Token(tToken.parCierre);
		case '|':
			return new Token(tToken.opVAbs);
		case '{':
			return new Token(tToken.llaveApertura);
		case '}':
			return new Token(tToken.llaveCierre);
		case '[':
			return new Token(tToken.corApertura);
		case ']':
			return new Token(tToken.corCierre);
		case '.':
			return new Token(tToken.punto);
		case ',':
			return new Token(tToken.coma);
		}
		return new Token();
	}
	
	public Token dameTokenPalReservada(String palReservada) {
		//Ninguna de las palabras reservadas debe llevar lexemas
		if (palReservada.equals("boolean")) {
			if (tokensOut.lastElement().getTipoToken() == tToken.dosPuntos|| 
					tokensOut.lastElement().getTipoToken() == tToken.ofT ||
					tokensOut.lastElement().getTipoToken() == tToken.pointerT)
				return new Token(tToken.tipoVarBooleano);
			else {
				error("Declaración incorrecta de tipo 'boolean'.");
				return new Token();
			}
		}
		if (palReservada.equals("character")) {
			if (tokensOut.lastElement().getTipoToken() == tToken.dosPuntos|| 
					tokensOut.lastElement().getTipoToken() == tToken.ofT ||
					tokensOut.lastElement().getTipoToken() == tToken.pointerT)
				return new Token(tToken.tipoVarCaracter);
			else {
				error("Declaración incorrecta de tipo 'character'.");
				return new Token();
			}
		}
		if (palReservada.equals("natural")) {
			if (tokensOut.lastElement().getTipoToken() == tToken.dosPuntos|| 
					tokensOut.lastElement().getTipoToken() == tToken.ofT ||
					tokensOut.lastElement().getTipoToken() == tToken.pointerT)
				return new Token(tToken.tipoVarNatural);
			else {
				error("Declaración incorrecta de tipo 'natural'.");
				return new Token();
			}
		}
		if (palReservada.equals("integer")) {
			if (tokensOut.lastElement().getTipoToken() == tToken.dosPuntos|| 
					tokensOut.lastElement().getTipoToken() == tToken.ofT ||
					tokensOut.lastElement().getTipoToken() == tToken.pointerT)
				return new Token(tToken.tipoVarEntero);
			else {
				error("Declaración incorrecta de tipo 'integer'.");
				return new Token();
			}
		}
		if (palReservada.equals("true")) {
			return new Token(tToken.booleanoCierto);
		}
		if (palReservada.equals("false")) {
			return new Token(tToken.booleanoFalso);
		}
		if (palReservada.equals("or")) {
			return new Token(tToken.oLogica);
		}
		if (palReservada.equals("and")) {
			return new Token(tToken.yLogica);
		}
		if (palReservada.equals("not")) {
			return new Token(tToken.negLogica);
		}
		if (palReservada.equals("in")) {
			return new Token(tToken.entradaTeclado);
		}
		if (palReservada.equals("out")) {
			return new Token(tToken.salidaPantalla);
		}
		if (palReservada.equals("char")) {
			return new Token(tToken.castChar);
		}
		if (palReservada.equals("nat")) {
			return new Token(tToken.castNat);
		}
		if (palReservada.equals("int")) {
			return new Token(tToken.castInt);
		}
		if (palReservada.equals("float")) {
			return new Token(tToken.castFloat);
		}
		//Palabras correspondientes a sentencias de control
		if (palReservada.equals("if")) {
			return new Token(tToken.ifC);
		}
		if (palReservada.equals("then")) {
			return new Token(tToken.thenC);
		}
		if (palReservada.equals("else")) {
			return new Token(tToken.elseC);
		}
		if (palReservada.equals("while")) {
			return new Token(tToken.whileC);
		}
		if (palReservada.equals("do")) {
			return new Token(tToken.doC);
		}
		if (palReservada.equals("for")) {
			return new Token(tToken.forC);
		}
		if (palReservada.equals("to")) {
			return new Token(tToken.toC);
		}
		//Sentencias de reserva y liberación de memoria
		if (palReservada.equals("new")) {
			return new Token(tToken.newM);
		}
		if (palReservada.equals("dispose")) {
			return new Token(tToken.disposeM);
		}
		//Palabras correspondientes a los tipos
		if (palReservada.equals("pointer")) {
			return new Token(tToken.pointerT);
		}
		if (palReservada.equals("array")) {
			return new Token(tToken.arrayT);
		}
		if (palReservada.equals("of")) {
			return new Token(tToken.ofT);
		}
		if (palReservada.equals("record")) {
			return new Token(tToken.recordT);
		}
		if (palReservada.equals("var")) {
			return new Token(tToken.var);
		}
		if (palReservada.equals("forward")) {
			return new Token(tToken.forward);
		}
		if (palReservada.equals("tipo")) {
			return new Token(tToken.decTipo);
		}
		if (palReservada.equals("procedure")) {
			return new Token(tToken.procedure);
		}
		if (palReservada.equals("null")) {
			return new Token(tToken.nullM);
		}
		return new Token();
	}
	
	public Token dameTokenIdentificador(String lexema) {
		return new Token(tToken.identificador, lexema);
	}
	
	public Token dameTokenCaracter(String car) {
		return new Token(tToken.caracter,car);
	}
	
//	public void extraerPalabras(String archivo){
//		try{
//			BufferedReader bfr=new BufferedReader(new FileReader(archivo));
//			String linea=bfr.readLine();
//			while(linea!=null){
//				StringTokenizer cadena=new StringTokenizer(linea);
//				while(cadena.hasMoretokensOut()){
//					tokensOut.add(cadena.nextToken());
//				}
//				linea=bfr.readLine();
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//	}
	
	public boolean scanFichero(String nombreFichero) {
		//Inicializamos y preparamos el fichero para su lectura. De hecho se lee el
		//primer caracter del fichero de entrada
		inicio(nombreFichero);
		//Realizamos el escaneo del fichero
		scan();
		//Mostramos por pantalla los resultados
		System.out.println("***********************************************************************");
		System.out.println("*                           ANÁLISIS LÉXICO                           *");
		System.out.println("***********************************************************************");
		System.out.println();
		System.out.println("Fichero de entrada: " + nombreFichero);
		System.out.println();
		System.out.println("Resultado");
		System.out.println("---------");
		System.out.println();
		if (!errorLex)
			System.out.println("El análisis ha sido satisfactorio." + "\n" +
					"Fueron leidas " + contPrograma + " líneas.");
		else
			System.out.println("Ocurrierron fallos durante el análisis:" + "\n" +
					descripError);
		System.out.println();
		System.out.println("Detalle de los tokens reconocidos");
		System.out.println("---------------------------------");
		System.out.println();
		for (int i = 0; i < tokensOut.size(); i++)
			if (tokensOut.get(i).getTipoToken() == tToken.puntoyComa ||
					tokensOut.get(i).getTipoToken() == tToken.separador) {
				if (tokensOut.get(i).getTipoToken() == tToken.separador)
					System.out.println();
				System.out.print(tokensOut.get(i).getTipoToken().toString());
				System.out.println();
			}
			else {
				if (tokensOut.get(i).getLexema() == null)
					System.out.print("{" + tokensOut.get(i).getTipoToken().toString() + "} ");
				else
					System.out.print("{" + tokensOut.get(i).getTipoToken().toString() + ", " +
						tokensOut.get(i).getLexema() + "} ");
			}
		System.out.println();
		System.out.println();
		System.out.println();
		
		return !errorLex;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombreFichero = "programa10.txt";
		
		ALexico scanner = new ALexico();
		
		scanner.scanFichero(nombreFichero);
		/*scanner.inicio(nombreFichero);
		
		scanner.scan();
		
		System.out.println("Lineas: " + scanner.contPrograma);
		
		for (int i = 0; i < scanner.tokensOut.size(); i++)
			if (scanner.tokensOut.get(i).getTipoToken() == tToken.puntoyComa ||
					scanner.tokensOut.get(i).getTipoToken() == tToken.separador) {
				if (scanner.tokensOut.get(i).getTipoToken() == tToken.separador)
					System.out.println();
				System.out.print(scanner.tokensOut.get(i).getTipoToken().toString());
				System.out.println();
			}
			else {
				if (scanner.tokensOut.get(i).getLexema() == null)
					System.out.print("{" + scanner.tokensOut.get(i).getTipoToken().toString() + "} ");
				else
					System.out.print("{" + scanner.tokensOut.get(i).getTipoToken().toString() + ", " +
						scanner.tokensOut.get(i).getLexema() + "} ");
			}*/

//		int a = 1e1;
//		double x = 50 + 00.00;
//		int a = 000043 - 04555.00
		
//		System.out.println(Integer.MAX_VALUE);
//		double x = 0025e056;
		
//Forma para pasar String a numéricos
//		String sX = "004566.34515e-005";
//		double x = 004566.34515e-005;
//		
//Pasamos String a Double		
//		double resul = sX.valueOf();
//		double numSX = Double.valueOf(sX).doubleValue();
//
//Pasamos String a Integer		
//		String str="12";
//		int numero=Integer.valueOf(str).intValue();
//
//		System.out.println();
//		System.out.println(x * 2);
//		System.out.println(numSX * 2);
//		System.out.println(sX);
//		System.out.println(x);
//		System.out.println(numSX);
/*		
		String _id = "id original";
		String _tipo = "tipo original";
		boolean _error = true;
		int _dir = 1;
		
		scanner.dec(_id, _tipo, _error, _dir);
		
		System.out.println();
		System.out.println(_id +" "+ _tipo +" "+ _error +" "+ _dir);*/
	}

	/*//Prueba de la i/o en Java
	public void dec(String id, String tipo, boolean error, int dir) {
		id = " id cambiado";
		tipo = "tipo cambiado";
		error = false;
		dir = 2;
	}*/
}
