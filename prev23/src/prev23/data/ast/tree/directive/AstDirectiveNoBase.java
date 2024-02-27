package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstDirectiveNoBase extends AstNode {

	public AstDirectiveNoBase(Location loc) {
		super(loc);
	}

	@Override
	public final String log() {
		return "	NOBASE";
	}
}
