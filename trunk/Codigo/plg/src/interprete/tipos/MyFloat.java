package interprete.tipos;


public class MyFloat implements StackObject{
	private Float value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (value instanceof Float)
			this.value = (Float)value;
		if (value instanceof MyChar){
			this.value = (Float)((MyFloat)value).getValue();
		}
	}
	
}
