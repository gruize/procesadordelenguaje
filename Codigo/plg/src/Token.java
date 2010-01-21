enum tiposToken {tipoVar, booleano, cadCaracteres, natural, entero, real, separador, 
	puntoyComa, suma, resta, negArit, multiplicacion, division, resto, operador, tokenError,
	parApertura, parCierre, identificador, finDeFichero};

public class Token {

	private String lexema;
	private tiposToken tipoToken;
	
	public Token() {
		setLexema(new String(""));
		setTipoToken(tiposToken.tokenError);
	}
	
	public Token(tiposToken _tipoToken) {
		// TODO Auto-generated constructor stub
		setLexema(new String(""));
		setTipoToken(_tipoToken);
	}
	
	public Token(tiposToken _tipoToken, String _lexema) {
		// TODO Auto-generated constructor stub
		setLexema(new String(_lexema));
		setTipoToken(_tipoToken);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public String getLexema() {
		return lexema;
	}

	public void setTipoToken(tiposToken tipoToken) {
		this.tipoToken = tipoToken;
	}

	public tiposToken getTipoToken() {
		return tipoToken;
	}

}
