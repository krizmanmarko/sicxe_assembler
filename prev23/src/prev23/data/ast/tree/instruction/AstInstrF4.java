package prev23.data.ast.tree.instruction;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;
import prev23.data.ast.tree.instruction.*;

public class AstInstrF4 extends AstNode {
	public AstLabel label;
	public AstInstrF3 f3;

	public AstInstrF4(Location loc, AstInstrF3 f3) {
		super(loc);
		this.f3 = f3;
	}

	public void setLabel(AstLabel label) {
		this.label = label;
	}

	@Override
	public String log() {
		return this.f3.log() + " | (+)";
	}
}
