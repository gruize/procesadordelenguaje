package interprete.tipos;

public abstract class StackObject {
	public abstract Object getValue();
	public abstract void setValue(Object value);
	public abstract int size();
	public abstract byte[] toBytes();
	public StackObject fromBytes(byte[] bytes, int pos){
		return null;
	}

}
