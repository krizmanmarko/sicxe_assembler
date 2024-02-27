package prev23.data.codegen;

public class TRecord {
	private final String type = "T";
	private int addr;
	private int size;
	private int value;

	public TRecord(int addr, int size, int value) {
		this.addr = addr & 0xfffff;	// 20-bits
		this.size = size & 0xff;	// 1 byte
		this.value = value;
	}

	public String log() {
		String s = type;
		String fmt = "%06X%02X%0" + (size * 2) + "X";
		s += String.format(fmt, addr, size, value);
		return s;
	}
}
