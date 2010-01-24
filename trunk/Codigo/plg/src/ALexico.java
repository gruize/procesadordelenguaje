import java.util.*;
import java.io.*;

enum est {e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, 
	e17, e18, e19, e20, e21, e22, e23, e24, e25, e26, e27};

public class ALexico {

	private char buff[];
	private String lex;
	private est estado;
	private Vector<Token> tokens;
	private BufferedReader bfr;
	private int contPrograma;
	private boolean errorLex;
	private boolean quedanCar;
	private Vector<String> palReservadas;
	private char carAntConsumido[];
	private boolean finFichero;
//	private boolean esCast;
	
	public ALexico() {
		// TODO Auto-generated constructor stub
		buff = new char[1];
		lex = new String();
		tokens = new Vector<Token>();
		contPrograma = 1;
		errorLex = false;
		quedanCar = true;
		palReservadas = new Vector<String>();
		carAntConsumido = new char[1];
		finFichero = false;
		
//		esCast = false;
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
	
	public void scanner() {
		Token tok = new Token();
		double realAux = 0;
		
		quedanCar = true;
		finFichero = false;
		iniciaScanner();
		while (quedanCar && !errorLex) {
			if (finFichero && estado == est.e0)	{
				quedanCar = false;
				tokens.add(new Token(tToken.finDeFichero));
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
						if (buff[0] == '&' || buff[0] == ';' || buff[0] == '+' || buff[0] == '-' ||
								buff[0] == '*' || buff[0] == '/' || buff[0] == '(' || 
								buff[0] == ')' || buff.toString().equals("-")) {
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
											tokens.lastElement().getTipoToken() == tToken.parApertura &&
											carAntConsumido[0] == '(') {
										tokens.remove(tokens.size() - 1);
										tokens.add(dameTokenPalReservada(lex));
										transita(est.e0);
										lex = "";
										break;
									}
									if (lex.equals("float") &&
											tokens.lastElement().getTipoToken() == tToken.dosPuntos) {
										tokens.add(new Token(tToken.tipoVarReal));
										iniciaScanner();
										break;
									}
									else {
										if (lex.equals("float"))
											error("Operador de 'cast float' mal formado, o declaración incorrecta de tipo 'float'.");
										else
											error("Operador de 'cast' mal formado.");
										tokens.add(new Token());
										break;
									}
								}
	//Resolvemos problemas entre identificadores y operaciones de entrada salida
	//							if (lex == "in") {
	//							}
								else {
									tokens.add(dameTokenPalReservada(lex));
									iniciaScanner();
								}
							}
							else {
								tokens.add(dameTokenIdentificador(lex));
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
							tokens.add(new Token(tToken.natural, "" + (int)realAux + ""));
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
								tokens.add(new Token(tToken.natural, "" + (int)realAux + ""));
								iniciaScanner();
								break;
							}
							if (realAux <= Double.MAX_VALUE) {
								tokens.add(new Token(tToken.real, "" + realAux + ""));
								iniciaScanner();
								break;
							}
							error("Número demasiado grande.");
						}
						break;		
					case e6:
						//Sobra (en el autómata estaba preparado para los enteros)
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
								if (realAux == 0) {
									tokens.add(new Token(tToken.natural, "0"));
									iniciaScanner();
									break;
								}
								if (Math.floor(realAux) == realAux) {
									if (Math.floor(realAux) > Integer.MAX_VALUE) {
										tokens.add(new Token(tToken.real, "" + realAux + ""));
										iniciaScanner();
										break;
									}
									else {	
										tokens.add(new Token(tToken.natural, "" + (int)realAux + ""));
										iniciaScanner();
										break;
									}
								}
								tokens.add(new Token(tToken.real, "" + realAux + ""));
								iniciaScanner();
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
								if (realAux == 0) {
									tokens.add(new Token(tToken.natural, "0"));
									iniciaScanner();
									break;
								}
								if (Math.floor(realAux) == realAux) {
									if (Math.floor(realAux) > Integer.MAX_VALUE) {
										tokens.add(new Token(tToken.real, "" + realAux + ""));
										iniciaScanner();
										break;
									}
									else {
										tokens.add(new Token(tToken.natural, "" + (int)realAux + ""));
										iniciaScanner();
										break;
									}	
								}
								tokens.add(new Token(tToken.real, "" + realAux + ""));
								iniciaScanner();
							}
							else
								error("Número demasiado grande.");
						}
						break;
					case e13:
						if (buff[0] == '\'') {
							tokens.add(dameTokenCadCaracteres(lex));
							transita(est.e0);
							lex = "";
							break;
						}
						else
							transita(est.e13);
						break;
					case e14:
						//Sobra (en el autómata se usaba cuando se reconocían las siguientes ")
						break;
					case e15:
						if (buff[0] == '=') {
							tok = new Token(tToken.asignacion);
							transita (est.e27);
							break;
						}
						else {
							tokens.add(new Token(tToken.dosPuntos));
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
							tokens.add(new Token(tToken.igual));
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
						
						break;
					case e20:
						
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
							tokens.add(new Token(tToken.menor));
							iniciaScanner();
						}
						break;
					case e22:
						
						break;
					case e23:
						
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
							tokens.add(new Token(tToken.mayor));
							iniciaScanner();
						}
						break;
					case e25:
		
						break;
					case e26:
		
						break;
					case e27:
						tokens.add(tok);
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
	
	public void error(String comentario) {
		if (comentario == null)
			System.out.println("Caracter inesperado en la linea " + contPrograma + " : '" + buff[0] + "'\n");
		else
			System.out.println("Caracter en buffer: '" + buff[0] + "'. Linea: " + contPrograma + '\n' +
					"Error: " + comentario);
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
			if (!tokens.isEmpty()) 
				if (tokens.lastElement().getTipoToken() == tToken.natural || 
						tokens.lastElement().getTipoToken() == tToken.entero ||
						tokens.lastElement().getTipoToken() == tToken.real ||
						tokens.lastElement().getTipoToken() == tToken.parCierre ||
						tokens.lastElement().getTipoToken() == tToken.identificador)
					return new Token(tToken.resta);
			else
				return new Token(tToken.negArit);
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
		}
		return new Token();
	}
	
	public Token dameTokenPalReservada(String palReservada) {
		//Ninguna de las palabras reservadas debe llevar lexemas
		if (palReservada.equals("boolean")) {
			if (tokens.lastElement().getTipoToken() == tToken.dosPuntos)
				return new Token(tToken.tipoVarBooleano);
			else {
				error("Declaración incorrecta de tipo 'boolean'.");
				return new Token();
			}
		}
		if (palReservada.equals("character")) {
			if (tokens.lastElement().getTipoToken() == tToken.dosPuntos)
				return new Token(tToken.tipoVarCadCaracteres);
			else {
				error("Declaración incorrecta de tipo 'character'.");
				return new Token();
			}
		}
		if (palReservada.equals("natural")) {
			if (tokens.lastElement().getTipoToken() == tToken.dosPuntos)
				return new Token(tToken.tipoVarNatural);
			else {
				error("Declaración incorrecta de tipo 'natural'.");
				return new Token();
			}
		}
		if (palReservada.equals("integer")) {
			if (tokens.lastElement().getTipoToken() == tToken.dosPuntos)
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
		return new Token();
	}
	
	public Token dameTokenIdentificador(String lexema) {
		return new Token(tToken.identificador, lexema);
	}
	
	public Token dameTokenCadCaracteres(String lexema) {
		return new Token(tToken.cadCaracteres, lexema);
	}
	
//	public void extraerPalabras(String archivo){
//		try{
//			BufferedReader bfr=new BufferedReader(new FileReader(archivo));
//			String linea=bfr.readLine();
//			while(linea!=null){
//				StringTokenizer cadena=new StringTokenizer(linea);
//				while(cadena.hasMoreTokens()){
//					tokens.add(cadena.nextToken());
//				}
//				linea=bfr.readLine();
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombreFichero = "programa.txt";
		
		ALexico parser = new ALexico();
		
		parser.iniciaVecPalReservadas();
		
		parser.inicio(nombreFichero);
		
		parser.scanner();
		
		System.out.println("Lineas: " + parser.contPrograma);
		
		for (int i = 0; i < parser.tokens.size(); i++)
			if (parser.tokens.get(i).getTipoToken() == tToken.puntoyComa ||
					parser.tokens.get(i).getTipoToken() == tToken.separador) {
				if (parser.tokens.get(i).getTipoToken() == tToken.separador)
					System.out.println();
				System.out.print(parser.tokens.get(i).getTipoToken().toString());
				System.out.println();
			}
			else {
				if (parser.tokens.get(i).getLexema() == null)
					System.out.print("{" + parser.tokens.get(i).getTipoToken().toString() + "} ");
				else
					System.out.print("{" + parser.tokens.get(i).getTipoToken().toString() + ", " +
						parser.tokens.get(i).getLexema() + "} ");
			}
		
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
	}
}
