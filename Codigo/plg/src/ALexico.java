
public class ALexico {
	public static int leeLexema(String cadena, String lexema,Integer pos){
		// Los espacios se ignoran siempre
		int pos = 0;
		while (true){
			// fin de linea ¿como lo hacemos?
			if (cadena.length() == pos){
				return util.TokensLexicos.NO_TOKEN;
			}
			if (cadena.charAt(pos) == ' '){
				pos++;
			}
			// TODO hacer salto de linea
			// salto de linea
			else if (cadena.charAt(pos) == '#'){
				// TODO copiar en lexema todo hasta el fin de linea
				return util.TokensLexicos.COMENTARIO;
			}
			else if (letra(cadena.charAt(pos))){
				return estado3(cadena,lexema,pos);
			}
			else
				return util.Errores.TOKEN_NO_IDENTIFICADO;
		}
	}
	private static int estado3(String cadena, String lexema, Integer pos) {
		int init = pos;
		while (true){
			if (letra(cadena.charAt(pos)) || digito(cadena.charAt(pos))){
				pos++;
			}
			// XXX siempre tiene que haber un espacio separando?
			else if (cadena.charAt(' ')){
				lexema = cadena.substring(init, pos);
				// reconociendo si es una palabra reservada
				int tipo = reservada(lexema);
				return 
			}
			else
		}
		return 0;
	}
	private static int reservada(String lexema) {
		if (lexema.equals("character"))
			return util.TokensLexicos.CHARACTER;
		if (lexema.equals("natural"))
			return util.TokensLexicos.NATURAL;
		if (lexema.equals("integer"))
			return util.TokensLexicos.INTEGER;
		if (lexema.equals("float"))
			return util.TokensLexicos.FLOAT;
		if (lexema.equals("boolean"))
			return util.TokensLexicos.BOOLEAN;
		if (lexema.equals("and"))
			return util.TokensLexicos.AND;
		return util.TokensLexicos.IDEN;
	}
	private static boolean letra(char caracter){
		if ((caracter >= 'a' && caracter <= 'z' ) || (caracter >= 'A' && caracter <= 'Z' )) 
			return true;
		return false;
	}
	private static boolean digito(char caracter){
		if (caracter <= '0' && caracter >='9')
			return true;
		return false;
	}
}
