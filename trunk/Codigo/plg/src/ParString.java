
public class ParString {
	
	private String str1;
	private String str2;

	public ParString() {
		// TODO Auto-generated constructor stub
		str1 = str2 = new String();
	}
	
	public ParString(String _str1, String _str2) {
		// TODO Auto-generated constructor stub
		str1 = new String(_str1);
		str2 = new String(_str2);
	}
	
	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = new String(str1);
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = new String(str2);
	}

}
