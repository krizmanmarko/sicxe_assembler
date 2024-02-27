package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.data.ast.tree.misc.*;
import prev23.common.report.*;

public class AstDirectiveStart extends AstNode {
	public String progName;
	public AstNumber baseAddr;		// 20-bit address space

	public AstDirectiveStart(Location loc, String progName, AstNumber baseAddr) {
		super(loc);
		this.progName = progName;
		this.baseAddr = baseAddr;
	}

	@Override
	public final String log() {
		return progName + "	START " + baseAddr.log();
	}
}
