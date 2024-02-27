package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstDirectiveBase extends AstNode {
	public AstNumber base;

	public AstDirectiveBase(Location loc, AstNumber base) {
		super(loc);
		this.base = base;
	}

	@Override
	public final String log() {
		return "	BASE " + base.log();
	}
}
