package interprete.tipos;

public class MyBoolean extends StackObject{
	private Boolean value;

	public Object getValue() {
		return value;
	}
	public void setValue(boolean value){
		this.value = value;
	}
	public void setValue(Object value) {
		if (value instanceof Boolean)
			this.value = (Boolean)value;
		if (value instanceof MyBoolean){
			this.value = (Boolean)((MyBoolean)value).getValue();
		}
	}
	@Override
	public int size() {
		return 1;
	}
	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[size()];
		if (value){
			bytes[0] = 0;
		}else
			bytes[0] = -1;
		return bytes;
	}
	@Override 
	public StackObject fromBytes(byte[] bytes, int pos){
		if (bytes.length > pos){
			if (bytes[pos] == 0)
				value = true;
			else
				value = false;
			return this;
		}
		return null;
	}
	
}
