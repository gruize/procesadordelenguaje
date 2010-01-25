package interprete.tipos;

public class MyBuffer extends StackObject{
	private String value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (value instanceof String)
			this.value = (String)value;
		if (value instanceof MyChar){
			this.value = (String)((MyBuffer)value).getValue();
		}
	}
	public int size() {
		return 0;
	}
	@Override
	public byte[] toBytes() {
		return null;
	}
	@Override 
	public StackObject fromBytes(byte[] bytes, int pos){
		return this;
	}

}
