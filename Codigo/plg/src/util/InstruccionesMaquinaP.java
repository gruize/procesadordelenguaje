package util;

public class InstruccionesMaquinaP {

	private OperacionesMaquinaP operacion;
	private Object argumento;
	
	public InstruccionesMaquinaP(OperacionesMaquinaP operacion, Object argumento) {
		this.operacion = operacion;
		this.argumento = argumento;
	}

	public InstruccionesMaquinaP(OperacionesMaquinaP operacion) {
		this.operacion = operacion;
	}

	public OperacionesMaquinaP getOperacion() {
		return operacion;
	}

	public void setOperacion(OperacionesMaquinaP operacion) {
		this.operacion = operacion;
	}

	public Object getArgumento() {
		return argumento;
	}

	public void setArgumento(Object argumento) {
		this.argumento = argumento;
	}
	
	
	
}
