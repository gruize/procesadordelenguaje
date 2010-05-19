package analizadorSintactico;

public class ParTipoEtiq {

	private tSintetiz tipo;
	private int etiq;
	
	public ParTipoEtiq() {
		this.tipo = tSintetiz.tInicial;
		this.etiq = 0;
	}
	
	public ParTipoEtiq(tSintetiz tipo, int etiq) {
		this.tipo = tipo;
		this.etiq = etiq;
	}
	
	public tSintetiz getTipo() {
		return tipo;
	}
	
	public void setTipo(tSintetiz tipo) {
		this.tipo = tipo;
	}
	
	public int getEtiq() {
		return etiq;
	}
	
	public void setEtiq(int etiq) {
		this.etiq = etiq;
	}
}
