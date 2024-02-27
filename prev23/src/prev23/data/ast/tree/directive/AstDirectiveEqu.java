package prev23.data.ast.tree.directive;

import prev23.data.ast.tree.*;
import prev23.data.ast.tree.misc.*;
import prev23.common.report.*;

public class AstDirectiveEqu extends AstNode {
	public AstLabel name;
	public AstNumber value;		// TODO: for now just numbers are implemented

	public AstDirectiveEqu(Location loc, AstLabel name, AstNumber value) {
		super(loc);
		this.name = name;
		this.value = value;
	}

	@Override
	public final String log() {
		return name + "	EQU " + value.log();
	}
}
