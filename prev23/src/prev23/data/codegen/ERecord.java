package prev23.data.codegen;

public class ERecord {
	final String type = "E";
	int entry;

	public ERecord(int entry) {
		this.entry = entry & 0xffffff;
	}

	public String log() {
		String s = type;
		s += String.format("%06X", entry);
		return s;
	}
}
