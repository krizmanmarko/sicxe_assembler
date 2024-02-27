package prev23.data.ast.tree.instruction;

import java.lang.StringBuilder;
import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstInstrF3a extends AstInstrF3 {
	public AstAddress address;
	public boolean x;

	public AstInstrF3a(Location loc, String mnemonic, AstAddress address,
			   boolean x) {
		super(loc, mnemonic);
		this.address = address;
		this.x = x;
	}

	@Override
	public String log() {
		StringBuilder sb = new StringBuilder();
		if (this.label != null) {
			sb.append(this.label.name);
		}
		sb.append("	");
		sb.append(this.mnemonic);
		sb.append(" ");
		sb.append(this.address.log());
		if (x)
			sb.append(", X");
		return sb.toString();
	}
}
