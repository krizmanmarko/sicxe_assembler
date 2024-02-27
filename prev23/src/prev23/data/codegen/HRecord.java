package prev23.data.codegen;

public class HRecord {
	final String type = "H";
	String name;
	int startAddr;
	int length;

	public HRecord(String name, int startAddr, int length) {
		this.name = String.format("%-6s", name.substring(0, Integer.min(6, name.length())));
		this.startAddr = startAddr & 0xffffff;
		this.length = length & 0xffffff;
	}

	public String log() {
		String s = type;
		s += name;
		s += String.format("%06X", startAddr);
		s += String.format("%06X", length);
		return s;
	}
}
