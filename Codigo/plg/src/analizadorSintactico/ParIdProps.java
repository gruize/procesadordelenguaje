package analizadorSintactico;

import tablaSimbolos.*;

public class ParIdProps {
	String id;
	PropsObjTS props;
	tClase clase;
	int nivel;
	
	public ParIdProps(String id, PropsObjTS props, tClase clase, int nivel) {
		this.id = id;
		this.props = props;
		this.clase = clase;
		this.nivel = nivel;
	}
	
	public ParIdProps(tClase clase, int nivel) {
		this.id = new String();
		this.clase = clase;
		this.nivel = nivel;
		//this.props = new PropsObjTS();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PropsObjTS getProps() {
		return props;
	}
	public void setProps(PropsObjTS props) {
		this.props = props;
	}

	public tClase getClase() {
		return clase;
	}

	public void setClase(tClase clase) {
		this.clase = clase;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
}
