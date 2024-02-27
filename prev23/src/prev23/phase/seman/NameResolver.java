package prev23.phase.seman;

import prev23.common.report.*;
import prev23.data.ast.tree.*;
import prev23.data.ast.tree.directive.*;
import prev23.data.ast.tree.instruction.*;
import prev23.data.ast.tree.misc.*;

import java.util.*;

/**
 * Name resolver.
 */
public class NameResolver extends AstVisitor<Integer, Integer> {

	public final LinkedList<AstLabel> defined = new LinkedList<AstLabel>();

	@Override
	public Integer visit(LinkedList<AstNode> ll, Integer arg) {
		for (AstNode n : ll) {
			this.visit(n, 1);
		}

		LC = 0;
		for (AstNode n : ll) {
			this.visit(n, 2);
		}
		return null;
	}

	@Override
	public Integer visit(AstDirectiveByte directive, Integer arg) {
		this.visit(directive.label, arg);
		LC += 1;
		return null;
	}

	@Override
	public Integer visit(AstDirectiveEqu directive, Integer arg) {
		if (arg == 1) {
			int num = this.visit(directive.value, arg);
			SemAn.symTab.put(directive.name, num);
		}
		return null;
	}

	@Override
	public Integer visit(AstDirectiveResb directive, Integer arg) {
		this.visit(directive.label, arg);
		LC += this.visit(directive.size, arg);
		return null;
	}

	@Override
	public Integer visit(AstAddressName misc, Integer arg) {
		if (misc == null) return null;
		if (arg == 2) {
			//System.out.println("use: " + misc.name);
			for (AstLabel l : defined) {
				if (misc.name.equals(l.name)) {
					SemAn.lookup.put(misc, l);
					return null;
				}
			}
			throw new Report.Error(misc, "symbol '" + misc + "' not declared!");
		}
		return null;
	}

	@Override
	public Integer visit(AstNumber misc, Integer arg) {
		if (misc == null) return null;
		return misc.num;
	}

	@Override
	public Integer visit(AstLabel misc, Integer arg) {
		if (misc == null) return null;
		if (arg == 1) {
			//System.out.println("def: " + misc.name);
			for (AstLabel l : defined)
				if (l.name.equals(misc.name))
					throw new Report.Error(misc, "symbol '" + misc +"' already declared");
			defined.add(misc);
			SemAn.symTab.put(misc, LC);
		}
		return null;
	}
}
