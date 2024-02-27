package prev23.data.ast.tree.misc;

import prev23.common.report.*;

public class AstAddressNum extends AstAddress {
	public AstNumber num;

	public AstAddressNum(Location loc, AstNumber num, boolean n, boolean i) {
		super(loc, n, i);
		this.num = num;
	}

	@Override
	public String log() {
		if (n && !i)
			return num.log() + ":indirect";
		else if (!n && i)
			return num.log() + ":immediate";
		// TODO: old SIC
		return num.log() + ":simple";
	}
}
