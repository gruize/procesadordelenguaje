
public class ParBooleanInt {
	
	private int intVal;
	private boolean booleanVal;
	
	public ParBooleanInt() {
		// TODO Auto-generated constructor stub
		intVal = 0;
		booleanVal = false;
	}
	
	public ParBooleanInt(boolean _booleanVal, int _intVal) {
		// TODO Auto-generated constructor stub
		intVal = _intVal;
		booleanVal = _booleanVal;
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

}
