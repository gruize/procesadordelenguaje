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
		quedanCar = true;
		iniciaScanner();
		while (quedanCar && !errorLex) {
			switch (estado) {
				case e0:
					if (esBlanFLinTab(buff[0])) {
						transita(est.e0);
						lex = "";
						break;
					}
					if (esLetra(buff[0])) {
						transita(est.e3);
						break;
					}
					if (buff[0] == '0') {
						transita(est.e4);
						break;
					}
					if (esDigitoNo0(buff[0])) {
						transita(est.e5);
						break;
					}
					if (buff[0] == ':') {
						transita(est.e15);
						break;
					}
					if (buff[0] == '&' || buff[0] == ';' || buff[0] == '+' || buff[0] == '-' ||
							buff[0] == '*' || buff[0] == '/' || buff[0] == '(' || 
							buff[0] == ')') {
						tok = dameToken(buff[0]);
						transita(est.e27);
						break;
					}
					if (buff[0] == '<') {
						transita(est.e21);
						break;
					}
					if (buff[0] == '>') {
						transita(est.e24);
						break;
					}
					if (buff[0] == '=') {
						transita(est.e17);
						break;
					}
					if (buff[0] == '#') {
						transita(est.e1);
						break;
					}
					if (buff[0] == '"') {
						transita(est.e13);
						break;
					}
					else
						error(est.e0, "");
					break;	
				case e1:
					if (esFLin(buff[0])) {
						lex = "";
						transita(est.e0);
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
										tokens.lastElement().getTipoToken() == tiposToken.parApertura &&
										!esBlanFLinTab(carAntConsumido[0])) {
									tokens.add(dameTokenPalReservada(lex));
									lex = "";
									transita(est.e0);
									break;
								}
								if (lex == "float") {
									tokens.add(new Token(tiposToken.tipoVarReal));
									iniciaScanner();
									break;
								}
								else {
									error(est.e3, ". Operador de cast mal formado.");
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
						else 
							tokens.add(dameTokenIdentificador(lex));
							iniciaScanner();
					}
					break;
				case e4:
					if (buff[0] == '.') {
						transita(est.e8);
						break;
					}
					//Admitiremos números como 0005556 o hasta algo como 00000
					if (esDigito(buff[0])) {
						transita(est.e5);
						break;
					}
					if (esE(buff[0])) {
						transita(est.e10);
						break;
					}
					//Guardamos un 0
					else {
						tokens.add(new Token(tiposToken.natural, lex));
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
						tokens.add(new Token(tiposToken.natural, lex));
						iniciaScanner();
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
						error(est.e8, ". Después de '.' sólo debe haber dígitos, ó 'e' ó 'E'.");
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
						tokens.add(new Token(tiposToken.real, lex));
						iniciaScanner();
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
						error(est.e10, ". Después de 'e' ó 'E' sólo debe haber dígitos ó '-'.");
					break;
				case e11:
					if (esDigito(buff[0])) {
						transita(est.e12);
						break;
					}
					else
						error(est.e11, ". Después de un - en el exponente sólo debe haber dígitos.");	
					break;
				case e12:
					if (esDigito(buff[0])) {
						transita(est.e12);
						break;
					}
					else {
						tokens.add(new Token(tiposToken.real, lex));
						iniciaScanner();
					}
					break;
				case e13:
					if (buff[0] == '"') {
						lex = lex + buff[0];
						tokens.add(dameTokenPalReservada(lex));
						lex = "";
						transita(est.e0);
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
						tok = new Token(tiposToken.asignacion);
						transita (est.e27);
						break;
					}
					else {
						tokens.add(new Token(tiposToken.dosPuntos));
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
						tokens.add(new Token(tiposToken.igual));
						iniciaScanner();
					}
					break;
				case e18:
					if (buff[0] == '=') {
						tok = new Token(tiposToken.distinto);
						transita (est.e27);
						break;
					}
					else
						error(est.e18, ". Operador de desigualdad mal formado.");
					break;
				case e19:
					
					break;
				case e20:
					
					break;
				case e21:
					if (buff[0] == '=') {
						tok = new Token(tiposToken.menorIgual);
						transita (est.e27);
						break;
					}
					if (buff[0] == '<') {
						tok = new Token(tiposToken.despIzq);
						transita (est.e27);
						break;
					}
					else {
						tokens.add(new Token(tiposToken.menor));
						iniciaScanner();
					}
					break;
				case e22:
					
					break;
				case e23:
					
					break;
				case e24:
					if (buff[0] == '=') {
						tok = new Token(tiposToken.mayorIgual);
						transita (est.e27);
						break;
					}
					if (buff[0] == '>') {
						tok = new Token(tiposToken.despDer);
						transita (est.e27);
						break;
					}
					else {
						tokens.add(new Token(tiposToken.mayor));
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
	
	public void transita(est estSig) {
		try {
			lex = lex + buff[0];
			if (buff[0] == '\n')
				contPrograma++;
			if (bfr.read(buff) == -1) {
				quedanCar = false;
				tokens.add(new Token(tiposToken.finDeFichero));
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
		if (lexema == "char" || lexema == "int" || lexema == "nat" || lexema == "float")
			return true;
		else
			return false;
	}
	
	public void error(est estSig, String comentario) {
		switch (estSig) {
		case e0:
			System.out.println("Caracter inesperado en la linea " + 
					contPrograma + " :" + buff[0]);
			break;	
		case e1:
			
			break;
		
		case e2:
			
			break;
		
		case e3:
			System.out.println("Caracter inesperado en la linea " + 
					contPrograma + " :" + buff[0] + comentario);
			break;
		case e4:
			
			break;
		
		case e5:
			
			break;
		
		case e6:
			
			break;
		
		case e7:
			
			break;
		}
		errorLex = true;
	}
	
	public Token dameToken(char car) {
		switch (car) {
		case '&': 
			return new Token(tiposToken.separador);	
		case ';':
			return new Token(tiposToken.puntoyComa);
		case '+':
			return new Token(tiposToken.suma);
		case '-':
			//Distinguimos el caso del - unario
			if (tokens.lastElement().getLexema().toString() == "natural" || 
				tokens.lastElement().getLexema().toString() == "integer" ||
				tokens.lastElement().getLexema().toString() == "real" ||
				tokens.lastElement().getLexema().toString() == "parCierre" ||
				tokens.lastElement().getTipoToken() == tiposToken.identificador)
				return new Token(tiposToken.resta);
			else
				return new Token(tiposToken.negArit);
		case '*':
			return new Token(tiposToken.multiplicacion);
		case '/':
			return new Token(tiposToken.division);
		case '%':
			return new Token(tiposToken.resto);
		case '(':
			return new Token(tiposToken.parApertura);
		case ')':
			return new Token(tiposToken.parCierre);
		}
		return new Token();
	}
	
	public Token dameTokenPalReservada(String palReservada) {
		//Ninguna de las palabras reservadas debe llevar lexemas
		if (palReservada == "boolean") {
			return new Token(tiposToken.tipoVarBooleano);
		}
		if (palReservada == "character") {
			return new Token(tiposToken.tipoVarCadCaracteres);
		}
		if (palReservada == "natural") {
			return new Token(tiposToken.tipoVarNatural);
		}
		if (palReservada == "integer") {
			return new Token(tiposToken.tipoVarEntero);
		}
		if (palReservada == "true") {
			return new Token(tiposToken.booleanoCierto);
		}
		if (palReservada == "false") {
			return new Token(tiposToken.booleanoFalso);
		}
		if (palReservada == "or") {
			return new Token(tiposToken.oLogica);
		}
		if (palReservada == "and") {
			return new Token(tiposToken.yLogica);
		}
		if (palReservada == "not") {
			return new Token(tiposToken.negLogica);
		}
		if (palReservada == "in") {
			return new Token(tiposToken.entradaTeclado);
		}
		if (palReservada == "out") {
			return new Token(tiposToken.salidaPantalla);
		}
		if (palReservada == "char") {
			return new Token(tiposToken.castChar);
		}
		if (palReservada == "nat") {
			return new Token(tiposToken.castNat);
		}
		if (palReservada == "int") {
			return new Token(tiposToken.castInt);
		}
		if (palReservada == "float") {
			return new Token(tiposToken.castFloat);
		}
		return new Token();
	}
	
	public Token dameTokenIdentificador (String lexema) {
		return new Token(tiposToken.identificador, lexema);
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
		
		parser.inicio(nombreFichero);
		
		parser.scanner();
		
		System.out.println("Lineas: " + parser.contPrograma);
	}

}
