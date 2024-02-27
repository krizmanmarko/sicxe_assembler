package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstDirectiveResb extends AstNode {
	public AstLabel label;
	public AstNumber size;

	public AstDirectiveResb(Location loc, AstLabel label, AstNumber size) {
		super(loc);
		this.label = label;
		this.size = size;
	}

	@Override
	public final String log() {
		if (this.label == null)
			return "	RESB " + size.log();
		return this.label.log() + "	RESB " + size.log();
	}
}
