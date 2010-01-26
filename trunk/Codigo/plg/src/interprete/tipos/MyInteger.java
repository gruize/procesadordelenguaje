package interprete.tipos;


public class MyInteger extends StackObject {
	private Integer value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (value instanceof Integer)
			this.value = (Integer)value;
		if (value instanceof MyChar){
			this.value = (Integer)((MyInteger)value).getValue();
		}
	}	
	@Override
	public int size() {
		return Integer.SIZE;
	}
	@Override
	public byte[] toBytes() {
		return util.Utils.toBytes(value.intValue());
	}
	@Override 
	public StackObject fromBytes(byte[] bytes, int pos){
		value = util.Utils.toInt(bytes, pos);
		return this;
	}
	@Override
	public byte type() {
		return MY_INTEGER;
	}
	@Override
	public boolean fromBuffer(MyBuffer buffer) {
		String sBuffer = (String)buffer.getValue();
		if (sBuffer == null)
			return false;
		try{
			value = Integer.valueOf(sBuffer);
		}catch ( NumberFormatException e) {
			value = null;
			return false;
		}
		return true;

	}

}
