package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstDirectiveEnd extends AstNode {
	public AstAddress entry;

	public AstDirectiveEnd(Location loc, AstAddress entry) {
		super(loc);
		this.entry = entry;
	}

	@Override
	public final String log() {
		return "	END " + entry.log();
	}
}
