package prev23.data.ast.tree.misc;

import prev23.data.ast.tree.AstNode;
import prev23.common.report.*;

public class AstReg extends AstNode {
	public int regnum;

	public AstReg(Location loc, int regnum) {
		super(loc);
		this.regnum = regnum;
	}

	@Override
	public String log() {
		if (this.regnum == 0)
			return "A";
		else if (this.regnum == 1)
			return "X";
		else if (this.regnum == 2)
			return "L";
		else if (this.regnum == 3)
			return "B";
		else if (this.regnum == 4)
			return "S";
		else if (this.regnum == 5)
			return "T";
		else if (this.regnum == 6)
			return "F";
		else if (this.regnum == 8)
			return "PC";
		else if (this.regnum == 9)
			return "SW";
		return "[WTF NOT A REAL REG]";
	}
}
