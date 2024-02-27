package prev23.data.ast.tree.instruction;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public abstract class AstInstrF2 extends AstNode {
	public AstLabel label;
	public String mnemonic;

	public AstInstrF2(Location loc, String mnemonic) {
		super(loc);
		this.mnemonic = mnemonic;
	}

	public void setLabel(AstLabel label) {
		this.label = label;
	}
}
