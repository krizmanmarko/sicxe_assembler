package prev23.data.ast.tree.misc;

import prev23.common.report.*;
import prev23.data.ast.tree.*;

public class AstNumber extends AstNode {
	public int num;

	public AstNumber(Location loc, boolean negative, int num) {
		super(loc);
		this.num = num;
	}

	@Override
	public String log() {
		return String.valueOf(this.num);
	}
}
