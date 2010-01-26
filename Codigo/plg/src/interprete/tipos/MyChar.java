package interprete.tipos;

public class MyChar extends StackObject{
	private Character value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (value instanceof Character)
			this.value = (Character)value;
		if (value instanceof MyChar){
			this.value = (Character)((MyBoolean)value).getValue();
		}
	}
	public int size() {
		return Integer.SIZE/8;
	}
	@Override
	public byte[] toBytes() {
		return util.Utils.toBytes((int)value.charValue());
	}
	@Override 
	public StackObject fromBytes(byte[] bytes, int pos){
		Integer i = util.Utils.toInt(bytes, pos);
		value = (char)i.intValue();
		return this;
	}
	@Override
	public byte type() {
		return MY_CHAR;
	}
	@Override
	public boolean fromBuffer(MyBuffer buffer) {
		String sBuffer = (String)buffer.getValue();
		if (sBuffer == null)
			return false;
		value = sBuffer.charAt(0);
		return true;
	}
	public String toString(){
		return "MyChar:"+value;
	}

}
