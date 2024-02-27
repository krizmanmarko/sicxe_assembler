package prev23.data.codegen;

public class MRecord {
	private final String type = "M";
	private int addr;
	private int nibbleCount;
	private int value;

	public MRecord(int addr, int nibbleCount) {
		this.addr = addr & 0xfffff;	// 20-bits
		this.nibbleCount = nibbleCount & 0xff;	// 1 byte
		this.value = value;
	}

	public String log() {
		String s = type;
		String fmt = "%06X%02X";
		s += String.format(fmt, addr, nibbleCount, value);
		return s;
	}
}
