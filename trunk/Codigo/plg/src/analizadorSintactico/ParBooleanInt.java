package analizadorSintactico;

public class ParBooleanInt {
	
	private int intVal;
	private boolean booleanVal;
	private int etiq;
	
	public ParBooleanInt() {
		// TODO Auto-generated constructor stub
		intVal = 0;
		booleanVal = false;
		etiq = 0;
	}
	
	public ParBooleanInt(boolean _booleanVal, int _intVal) {
		// TODO Auto-generated constructor stub
		intVal = _intVal;
		booleanVal = _booleanVal;
	}
	
	public ParBooleanInt(boolean _booleanVal, int _intVal, int _etiq) {
		// TODO Auto-generated constructor stub
		intVal = _intVal;
		booleanVal = _booleanVal;
		etiq = _etiq;	
	}
	
	public int getIntVal() {
		return intVal;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public boolean getBooleanVal() {
		return booleanVal;
	}

	public void setBooleanVal(boolean booleanVal) {
		this.booleanVal = booleanVal;
	}

	public int getEtiq() {
		return etiq;
	}

	public void setEtiq(int etiq) {
		this.etiq = etiq;
	}

}
