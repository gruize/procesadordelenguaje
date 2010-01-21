import java.util.*;
import java.io.*;

enum est {e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e27};

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
	
	public ALexico() {
		// TODO Auto-generated constructor stub
		
		buff = new char[1];
		lex = new String();
		tokens = new Vector<Token>();
		contPrograma = 1;
		errorLex = false;
		quedanCar = true;
		palReservadas = new Vector<String>();
	}
	
	public void iniciaVecPalReservadas() {
		palReservadas.clear();
		//Primero añadimos los tipos
		palReservadas.add("boolean");
		palReservadas.add("character");
		palReservadas.add("natural");
		palReservadas.add("integer");
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
		palReservadas.add("(char)");
		palReservadas.add("(nat)");
		palReservadas.add("(int)");
		palReservadas.add("(float)");
		//Operadores de desplazamiento
		palReservadas.add("<<");
		palReservadas.add(">>");
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
		quedanCar = true;
		lex = "";
		estado = est.e0;
		while (quedanCar && !errorLex) {
			switch (estado) {
				case e0:
					if (buff[0] == ' ' || buff[0] == '\n' || buff[0] == '\t' || buff[0] == '\r') {
						transita(est.e0);
						lex = "";
						break;
					}
					if ((buff[0] >= 'a' && buff[0] <= 'z') || (buff[0] >= 'A' && buff[0] <= 'Z')) {
						transita(est.e3);
						break;
					}
					if (buff[0] == '0') {
						transita(est.e4);
						break;
					}
					if (buff[0] >= '1' && buff[0] <= '9') {
						transita(est.e5);
						break;
					}
					if (buff[0] == ':') {
						transita(est.e15);
						break;
					}
					if (buff[0] == '&') {
						tokens.add(dameToken(buff[0]));
						transita(est.e0);
						break;
					}
					//			e0: caso buff de
					//				{&,;,+,-,*,/,(,),<eof>}: tok ¬ token(buff);
					//				transita(e7);
					//				si no error(e0)
					break;	
				case e1:
					
					break;
				
				case e2:
					
					break;
				
				case e3:
					
					break;
				case e4:
					
					break;
				
				case e5:
					
					break;
				
				case e6:
					
					break;
				
				case e7:
					
					break;
				case e8:
					
					break;
				case e9:
					
					break;
				
				case e10:
					
					break;
				
				case e11:
					
					break;
				
				case e12:
					
					break;
				case e13:
					
					break;
				case e14:
		
					break;
				case e15:
					errorLex = true;
					break;
				case e27:
					if (buff[0] == ' ' || buff[0] == '\n' || buff[0] == '\t' || buff[0] == '\r') {
						transita(est.e0);
						lex = "";
						break;
					}
					else
						errorLex = true;
					break;
				default:
					transita(est.e0);
			}
		}

	//			fcaso
	//			e1: caso
	//				[a-z]|[A-Z]: transita(e1);
	//				si no devuelve Iden
	//			fcaso
	//			e2: caso
	//				.: transita(e4);
	//				si no devuelve Num
	//			fcaso
	//			e3: caso
	//				[0-9]: transita(e3);
	//				.: transita(e4)
	//				si no devuelve Num
	//			fcaso
	//			e4: caso
	//				[0-9]: transita(e5)
	//				si no error(e4)
	//			fcaso
	//			e5: caso
	//				[0-9]: transita(e5)
	//				si no devuelve Num
	//			fcaso
	//				e6: caso
	//				=: tok ¬ Asig; transita(e7);
	//				si no error(e6)
	//			fcaso
	//			e7: devuelve tok;
	//			fcaso
	//			frepite
	}
	
	public void transita(est estSig) {
		try {
			lex = lex + buff[0];
			if (buff[0] == '\n')
				contPrograma++;
			if (bfr.read(buff) == -1)
				quedanCar = false;
			estado = estSig;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void error(est estSig) {
		switch (estSig) {
		case e0:

			break;	
		case e1:
			
			break;
		
		case e2:
			
			break;
		
		case e3:
			
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
		System.out.println("Caracter inesperado en la linea " + contPrograma + " :" + buff[0]);
		errorLex = true;
	}
	
	public Token dameToken(char car) {
		switch (car) {
		case '&': 
			return new Token(tiposToken.separador);	
		case ';':
			return new Token(tiposToken.puntoyComa);
		case '+':
			return new Token(tiposToken.operador, "suma");
		case '-':
			//Distinguimos el caso del - unario
			if (tokens.lastElement().toString() == "natural" || 
				tokens.lastElement().toString() == "integer" ||
				tokens.lastElement().toString() == "real" ||
				tokens.lastElement().toString() == "parCierre")
				return new Token(tiposToken.operador, "resta");
			else
				return new Token(tiposToken.operador, "negArit");
		case '*':
			return new Token(tiposToken.operador, "multiplicacion");
		case '/':
			return new Token(tiposToken.operador, "division");
		case '%':
			return new Token(tiposToken.operador, "resto");
		case '(':
			return new Token(tiposToken.parApertura);
		case ')':
			return new Token(tiposToken.parCierre);
		}
		return new Token();
	}
	
	public Token dameTokenPalReservada(String palReservada) {
		if (palReservada == "boolean") {
			return new Token(tiposToken.tipoVar, "booleano");
		}
		if (palReservada == "character") {
			return new Token(tiposToken.tipoVar, "cadCaracteres");
		}
		if (palReservada == "natural") {
			return new Token(tiposToken.tipoVar, "natural");
		}
		if (palReservada == "integer") {
			return new Token(tiposToken.tipoVar, "entero");
		}
		if (palReservada == "float") {
			return new Token(tiposToken.tipoVar, "real");
		}
		if (palReservada == "true") {
			return new Token(tiposToken.booleano, "cierto");
		}
		if (palReservada == "false") {
			return new Token(tiposToken.booleano, "falso");
		}
		if (palReservada == "or") {
			return new Token(tiposToken.operador, "oLogica");
		}
		if (palReservada == "and") {
			return new Token(tiposToken.operador, "yLogica");
		}
		if (palReservada == "not") {
			return new Token(tiposToken.operador, "negLogica");
		}
		if (palReservada == "in") {
			return new Token(tiposToken.operador, "entradaTeclado");
		}
		if (palReservada == "out") {
			return new Token(tiposToken.operador, "salidaPantalla");
		}
		if (palReservada == "(char)") {
			return new Token(tiposToken.operador, "castChar");
		}
		if (palReservada == "(nat)") {
			return new Token(tiposToken.operador, "castNat");
		}
		if (palReservada == "(int)") {
			return new Token(tiposToken.operador, "castInt");
		}
		if (palReservada == "(float)") {
			return new Token(tiposToken.operador, "castFloat");
		}
		if (palReservada == "<<") {
			return new Token(tiposToken.operador, "despIzq");
		}
		if (palReservada == ">>") {
			return new Token(tiposToken.operador, "despDer");
		}
		return new Token();

//		palReservadas.add("natural");
//		palReservadas.add("integer");
//		palReservadas.add("float");
//		//Valores booleanos
//		palReservadas.add("true");
//		palReservadas.add("false");
//		//Operadores booleanos
//		palReservadas.add("and");
//		palReservadas.add("or");
//		//Operadores de entrada / salida
//		palReservadas.add("in");
//		palReservadas.add("out");
//		//Operadores de casting
//		palReservadas.add("(char)");
//		palReservadas.add("(nat)");
//		palReservadas.add("(int)");
//		palReservadas.add("(float)");
//		//Operadores de desplazamiento
//		palReservadas.add("<<");
//		palReservadas.add(">>");
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
