package util;

public class TokensLexicos {
	// TOKENS
	public static int NO_TOKEN = 0;
	
	
	
	public static int CHARACTER = 1; 	// character 
	public static int NATURAL = 2;		// natural
	public static int INTEGER = 3;		// integer
	public static int FLOAT = 4;		// float
	public static int BOOLEAN = 5; 		// boolean
	
	public static int AND = 15;			// and
	public static int OR = 16;			// or
	public static int NOT = 17;			// not
	public static int CAST_NAT = 18;		// (nat)
	public static int CAST_INT = 19;		// (int)
	public static int CAST_CHAR = 20;	// (char)
	public static int CAST_FLOAT = 21;	// (float)
	public static int IN = 22;			// in
	public static int OUT = 23;			// out
	public static int IGUAL = 25;		// =
	public static int MAYOR = 26;		// >
	public static int MENOR = 27;		// <
	public static int MAYOR_IGUAL = 28;	// >=
	public static int MENOR_IGUAL = 29;	// <=
	public static int ASIGNACION = 30;	// := 
	public static int MAS=31;			// +
	public static int MENOS=32;			// -
	public static int PRODUCTO=33;		// *
	public static int DIVISION=34;		// /
	public static int MODULO=35;		// %
	
	
	public static int SEPARADOR = 50;			// &
	public static int FIN_LINEA = 51;			// ;
	public static int SEPARADOR_DEC_VARIABLES =52; // :
	public static int COMENTARIO=53;			// #
	public static int PARENTESIS_ABRIR=54;	// (
	public static int PARENTESIS_CERRAR=55;	// )
	
	// tipos que tienen valor añadido
	public static int TRUE = 80;		// true
	public static int FALSE = 81;		// false
	// NO ES NECESARIO PORQUE TENEMOS TRUE FALSE 
//	public static int BOOLEAN_VALUE=100;// 
	public static int LETRA=101;		// a, g, h
	public static int REAL_VALUE=102;	// -5 E 45
	public static int NAT_VALUE=103;	// -8545
	public static int INT_VALUE=104;	// 1447
	
	// identificador 
	public static int IDEN = 2000;
}	
