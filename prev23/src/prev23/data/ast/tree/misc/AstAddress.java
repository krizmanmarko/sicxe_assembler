package prev23.data.ast.tree.misc;

import prev23.data.ast.tree.AstNode;
import prev23.common.report.*;

public abstract class AstAddress extends AstNode {
	public boolean n;
	public boolean i;

	public AstAddress(Location loc, boolean n, boolean i) {
		super(loc);
		this.n = n;
		this.i = i;
	}
}
