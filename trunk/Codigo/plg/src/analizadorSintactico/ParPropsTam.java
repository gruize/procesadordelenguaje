package analizadorSintactico;

import tablaSimbolos.*;

public class ParPropsTam {
	PropsObjTS props;
	int tam;
	
	public ParPropsTam(PropsObjTS props, int tam) {
		this.props = props;
		this.tam = tam;
	}
	
	public PropsObjTS getProps() {
		return props;
	}
	public void setProps(PropsObjTS props) {
		this.props = props;
	}
	public int getTam() {
		return tam;
	}
	public void setTam(int tam) {
		this.tam = tam;
	}
	
	
}
