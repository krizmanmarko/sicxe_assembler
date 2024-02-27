package prev23.data.ast.tree.instruction;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstInstrF3 extends AstNode {
	public AstLabel label;
	public String mnemonic;

	public AstInstrF3(Location loc, String mnemonic) {
		super(loc);
		this.mnemonic = mnemonic;
	}

	public void setLabel(AstLabel label) {
		this.label = label;
	}

	@Override
	public String log() {
		return "	RSUB";
	}
}
