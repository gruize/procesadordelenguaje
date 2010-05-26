package tablaSimbolos;

public class Pendiente extends PropsObjTS {
	private tipoT t;
	private String nomTipo;
	private Integer tam;
	
	
	public Pendiente(String nomTipo) {
		this.t = tipoT.pendiente;
		this.nomTipo = nomTipo;
		this.tam = new Integer(0);
	}
	
	public String getNomTipo() {
		return nomTipo;
	}

	public void setNomTipo(String nomTipo) {
		this.nomTipo = nomTipo;
	}
	
	@Override
	public tipoT getT() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public int getTam() {
		// TODO Auto-generated method stub
		return tam.intValue();
	}
	
	public void setTam(int tam) {
		this.tam = new Integer(tam);
	}

}

