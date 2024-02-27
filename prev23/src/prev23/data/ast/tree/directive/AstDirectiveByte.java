package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstDirectiveByte extends AstNode {
	public AstLabel label;
	public int value;

	public AstDirectiveByte(Location loc, AstLabel label, int value) {
		super(loc);
		this.label = label;
		this.value = value;
	}

	@Override
	public final String log() {
		if (this.label == null)
			return "	BYTE " + value;
		return this.label.log() + "	BYTE " + value;
	}
}
