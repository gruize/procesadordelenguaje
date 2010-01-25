package util;

public class Utils {
	

	public static byte[] toBytes(int data) {
		return new byte[] {
				(byte)((data >> 24) & 0xff),
				(byte)((data >> 16) & 0xff),
				(byte)((data >> 8) & 0xff),
				(byte)((data >> 0) & 0xff),
		};
	}
	public static byte[] toBytes(float data) {
		return toBytes(Float.floatToRawIntBits(data));
	}
	public static int toInt(byte[] data,int pos) {
		if (data == null) return 0x0;
		return (int)( // NOTE: type cast not necessary for int
				(0xff & data[pos]) << 24 |
				(0xff & data[pos+1]) << 16 |
				(0xff & data[pos+2]) << 8 |
				(0xff & data[pos+3]) << 0
		);
	}
	public static float toFloat(byte[] data,int pos) {
		if (data == null) return 0x0;
		byte[] tmp = new byte[Integer.SIZE];
		System.arraycopy(data, pos, tmp, 0, Integer.SIZE);
		return Float.intBitsToFloat(toInt(tmp,0));
	}



}
