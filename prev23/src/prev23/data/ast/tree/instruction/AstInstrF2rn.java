package prev23.data.ast.tree.instruction;

import java.lang.StringBuilder;
import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstInstrF2rn extends AstInstrF2 {
	public AstReg reg;
	public AstNumber number;

	public AstInstrF2rn(Location loc, String mnemonic, AstReg reg,
			    AstNumber number) {
		super(loc, mnemonic);
		this.reg = reg;
		this.number = number;
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
		sb.append(this.reg.log());
		sb.append(", ");
		sb.append(this.number.log());
		return sb.toString();
	}
}
