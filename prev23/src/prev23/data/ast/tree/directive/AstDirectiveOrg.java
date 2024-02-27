package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.common.report.*;
import prev23.data.ast.tree.misc.*;

public class AstDirectiveOrg extends AstNode {
	public AstNumber address;

	public AstDirectiveOrg(Location loc, AstNumber address) {
		super(loc);
		this.address = address;
	}

	@Override
	public final String log() {
		return "	ORG " + address.log();
	}
}
