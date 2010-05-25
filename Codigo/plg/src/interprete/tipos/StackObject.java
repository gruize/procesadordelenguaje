package interprete.tipos;

public abstract class StackObject {
	public final static byte MY_BOOLEAN = 0X00;
	public final static byte MY_BUFFER = 0X01;
	public final static byte MY_CHAR = 0X02;
	public final static byte MY_EXECUTIONERROR = 0X03;
	public final static byte MY_FLOAT = 0X04;
	public final static byte MY_INTEGER = 0X05;
	public final static byte MY_NATURAL = 0X06;
	public final static byte INDETERMINATED = -0X80;
	public abstract Object getValue();
	public abstract void setValue(Object value);
	public abstract int size();
	public abstract byte[] toBytes();
	public StackObject fromBytes(byte[] bytes, int pos){
		return null;
	}
	public abstract byte type();
	public abstract boolean fromBuffer(MyBuffer buffer);
	public final StackObject duplica(){
		StackObject o = null;
		if (this instanceof MyBoolean){
			o = new MyBoolean();
			o.setValue(this.getValue());
		}
		if (this instanceof MyBuffer){
			o = new MyBuffer();
			o.setValue(this.getValue());
		}
		if (this instanceof MyChar){
			o = new MyChar();
			o.setValue(this.getValue());
		}
		if (this instanceof MyFloat){
			o = new MyFloat();
			o.setValue(this.getValue());
		}
		if (this instanceof MyInteger){
			o = new MyInteger();
			o.setValue(this.getValue());
		}
		if (this instanceof MyNatural){
			o = new MyNatural();
			o.setValue(this.getValue());
		}
	
		return o;
	}
	

}
