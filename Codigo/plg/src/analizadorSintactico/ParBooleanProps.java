package analizadorSintactico;

import tablaSimbolos.*;

public class ParBooleanProps {
	boolean error;
	PropsObjTS props;
	
	public ParBooleanProps(boolean error, PropsObjTS props) {
		this.error = error;
		this.props = props;
	}
	
	public ParBooleanProps() {
		this.error = false;
		//this.props = new PropsObjTS();
	}
	
	public boolean getError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public PropsObjTS getProps() {
		return props;
	}
	public void setProps(PropsObjTS props) {
		this.props = props;
	}
}
