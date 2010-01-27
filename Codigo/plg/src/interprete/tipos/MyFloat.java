package interprete.tipos;


public class MyFloat extends StackObject{
	private Float value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (value instanceof Float)
			this.value = (Float)value;
		if (value instanceof MyFloat){
			this.value = (Float)((MyFloat)value).getValue();
		}
		if (value instanceof Integer){
			this.value = new Float((Integer)value);
		}
	}
	@Override
	public int size() {
		return util.Utils.toBytes((new Float(0.0))).length;
	}
	@Override
	public byte[] toBytes() {
		return util.Utils.toBytes(value.floatValue());
	}
	@Override 
	public StackObject fromBytes(byte[] bytes, int pos){
		value = util.Utils.toFloat(bytes, pos);
		return this;
	}
	@Override
	public byte type() {
		return MY_FLOAT;
	}
	@Override
	public boolean fromBuffer(MyBuffer buffer) {
		String sBuffer = (String)buffer.getValue();
		if (sBuffer == null)
			return false;
		try{
			value = Float.valueOf(sBuffer);
		}catch ( NumberFormatException e) {
			value = null;
			return false;
		}
		return true;
	}
	public String toString(){
		return ""+value;
	}




	
}
