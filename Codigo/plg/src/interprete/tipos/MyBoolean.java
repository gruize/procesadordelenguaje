package interprete.tipos;

public class MyBoolean implements StackObject{
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
	
}
