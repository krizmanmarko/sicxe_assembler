package prev23.data.ast.tree;

import prev23.common.report.*;

public abstract class AstNode implements Cloneable, Locatable {
	private static int numNodes = 0;
	public int id;
	public Location location;

	public AstNode(Location location) {
		id = numNodes++;
		this.location = location;
	}

	public final int id() {
		return this.id;
	}

	public final void relocate(Location location) {
		this.location = location;
	}

	public final Location location() {
		return this.location;
	}

	public String log() {
		return this.id + ": " + this;
	}
}
