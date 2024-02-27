package prev23.data.ast.tree.instruction;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstInstrF1 extends AstNode {
	public AstLabel label;
	public String mnemonic;

	public AstInstrF1(Location loc, String mnemonic) {
		super(loc);
		this.mnemonic = mnemonic;
	}

	public void setLabel(AstLabel label) {
		this.label = label;
	}

	@Override
	public String log() {
		StringBuilder sb = new StringBuilder();
		if (this.label != null) {
			sb.append(this.label.name);
		}
		sb.append("	");
		sb.append(this.mnemonic);
		return sb.toString();
	}
}
