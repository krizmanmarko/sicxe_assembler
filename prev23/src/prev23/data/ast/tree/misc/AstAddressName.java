package prev23.data.ast.tree.misc;

import prev23.common.report.*;

public class AstAddressName extends AstAddress {
	public final String name;

	public AstAddressName(Location loc, String name, boolean n, boolean i) {
		super(loc, n, i);
		this.name = name;
	}

	@Override
	public String log() {
		if (n && !i)
			return name +":indirect";
		else if (!n && i)
			return name +":immediate";
		// TODO: old SIC
		return name +":simple";
	}

	@Override
	public String toString() {
		return this.name;
	}
}
