package analizadorSintactico;

public class ParString {
	
	private String iden;
	private tSintetiz tipo;

	public ParString() {
		// TODO Auto-generated constructor stub
		iden = new String();
		tipo = tSintetiz.tInicial;
	}
	
	public ParString(String _iden, tSintetiz _tipo) {
		// TODO Auto-generated constructor stub
		iden = new String(_iden);
		tipo = _tipo;
	}
	
	public String getIden() {
		return iden;
	}

	public void setIden(String _iden) {
		iden = new String(_iden);
	}

	public tSintetiz getTipo() {
		return tipo;
	}

	public void setTipo(tSintetiz _tipo) {
		tipo = _tipo;
	}

}
