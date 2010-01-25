package interprete.tipos;

public class MyChar implements StackObject{
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
}
