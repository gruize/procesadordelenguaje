package interprete.tipos;

public class MyNatural implements StackObject{
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

}
