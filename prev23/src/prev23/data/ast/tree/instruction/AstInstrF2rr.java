package prev23.data.ast.tree.instruction;

import java.lang.StringBuilder;
import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstInstrF2rr extends AstInstrF2 {
	public AstReg r1;
	public AstReg r2;

	public AstInstrF2rr(Location loc, String mnemonic, AstReg r1,
			    AstReg r2) {
		super(loc, mnemonic);
		this.r1 = r1;
		this.r2 = r2;
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
		sb.append(this.r1.log());
		sb.append(", ");
		sb.append(this.r2.log());
		return sb.toString();
	}
}
