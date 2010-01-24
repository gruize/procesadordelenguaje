enum tToken {
	tipoVarBooleano, tipoVarCadCaracteres, tipoVarNatural, tipoVarEntero,tipoVarReal,
	
	booleano, cadCaracteres, natural, entero, real,
	
	igual, mayor, menor, mayorIgual, menorIgual, distinto,
	
	separador, puntoyComa, dosPuntos, asignacion,
	
	suma, resta, negArit, multiplicacion, division, resto,
	
	tokenError,
	
	parApertura, parCierre,
	
	identificador,
	
	booleanoCierto, booleanoFalso,
	negLogica, oLogica, yLogica,
	
	castChar, castNat, castInt, castFloat,
	
	despIzq, despDer,
	
	entradaTeclado, salidaPantalla,
	
	finDeFichero
	};

public class Token {

	private String lexema;
	private tToken tipoToken;
	
	public Token() {
		setLexema(null);
		setTipoToken(tToken.tokenError);
	}
	
	public Token(tToken _tipoToken) {
		// TODO Auto-generated constructor stub
		setLexema(null);
		setTipoToken(_tipoToken);
	}
	
	public Token(tToken _tipoToken, String _lexema) {
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

	public void setTipoToken(tToken tipoToken) {
		this.tipoToken = tipoToken;
	}

	public tToken getTipoToken() {
		return tipoToken;
	}

}