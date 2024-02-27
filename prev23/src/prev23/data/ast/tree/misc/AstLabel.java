package prev23.data.ast.tree.misc;

import prev23.data.ast.tree.AstNode;
import prev23.common.report.*;

public class AstLabel extends AstNode {
	public String name;

	public AstLabel(Location loc, String name) {
		super(loc);
		this.name = name;
	}

	@Override
	public String toString() {
		if (this.name == null)
			return "";
		return this.name;
	}

	public String log() {
		if (this.name == null)
			return "";
		return this.name;
	}
}
