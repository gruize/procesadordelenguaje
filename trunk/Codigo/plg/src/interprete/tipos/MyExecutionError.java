package interprete.tipos;

public class MyExecutionError extends StackObject {
	public static int NO_ERROR = 0;
	public static int STACK_ERROR = 1;
	public static int OPERATION_ERROR = 2;
	public static final int MEMORY_ERROR = 3;
	
	public static String [] STRING_MESSAGE = {
		"no error", 			//0
		"stack error",			//1
		"operation error", 		//2
		"violation memory", 	//3
	};
	Integer codeError = 0;
	String message = "";
	public MyExecutionError(int codeError){
		this.codeError = codeError;
		this.message = null;
	}
	public MyExecutionError(int codeError, String message){
		this.codeError = codeError;
		this.message = message;
	}
	public Object getValue() {
		
		return codeError;
	}
	public void setValue(int value){
		codeError = value;
	}
	public void setValue(Object value) {
		if (value instanceof Integer)
			codeError = (Integer)value;
		
		// TODO Auto-generated method stub

	}
	public void setError(int codeError, String message){
		this.codeError = codeError;
		this.message = message;
	}
	public String toString(){
		String cadena = "";
		cadena = "Code error="+codeError+". "+STRING_MESSAGE[codeError];
		if (message != null && message.equals("")){
			cadena = "Message: "+message;
		}
		return cadena;
	}
	@Override
	public int size() {
		return 0;
	}
	@Override
	public byte[] toBytes() {
		return null;
	}
	@Override
	public byte type() {
		return MY_EXECUTIONERROR;
	}
	@Override
	public boolean fromBuffer(MyBuffer buffer) {
		return false;
	}
	

	

}
