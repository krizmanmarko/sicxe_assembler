package prev23.data.ast.tree;

import prev23.common.report.*;
import prev23.data.ast.tree.*;
import prev23.data.ast.tree.directive.*;
import prev23.data.ast.tree.instruction.*;
import prev23.data.ast.tree.misc.*;

import java.util.LinkedList;

public abstract class AstVisitor<Result, Arg> {

	protected int LC = 0;

	public Result visit(LinkedList<AstNode> ll, Arg arg) {
		for (AstNode n : ll)
			this.visit(n, arg);
		return null;
	}

	public Result visit(AstNode n, Arg arg) {
		if (n instanceof AstDirectiveBase)
			this.visit((AstDirectiveBase) n, arg);
		else if (n instanceof AstDirectiveByte)
			this.visit((AstDirectiveByte) n, arg);
		else if (n instanceof AstDirectiveEnd)
			this.visit((AstDirectiveEnd) n, arg);
		else if (n instanceof AstDirectiveEqu)
			this.visit((AstDirectiveEqu) n, arg);
		else if (n instanceof AstDirectiveNoBase)
			this.visit((AstDirectiveNoBase) n, arg);
		else if (n instanceof AstDirectiveOrg)
			this.visit((AstDirectiveOrg) n, arg);
		else if (n instanceof AstDirectiveResb)
			this.visit((AstDirectiveResb) n, arg);
		else if (n instanceof AstDirectiveStart)
			this.visit((AstDirectiveStart) n, arg);

		else if (n instanceof AstInstrF1)
			this.visit((AstInstrF1) n, arg);
		else if (n instanceof AstInstrF2)
			this.visit((AstInstrF2) n, arg);
		else if (n instanceof AstInstrF3)
			this.visit((AstInstrF3) n, arg);
		else if (n instanceof AstInstrF4)
			this.visit((AstInstrF4) n, arg);

		else if (n instanceof AstAddress)
			this.visit((AstAddress) n, arg);
		else if (n instanceof AstAddressName)
			this.visit((AstAddressName) n, arg);
		else if (n instanceof AstAddressNum)
			this.visit((AstAddressNum) n, arg);
		else if (n instanceof AstLabel)
			this.visit((AstLabel) n, arg);
		else if (n instanceof AstNumber)
			this.visit((AstNumber) n, arg);
		else if (n instanceof AstReg)
			this.visit((AstReg) n, arg);
		return null;
	}

	public Result visit(AstDirectiveBase directive, Arg arg) {
		this.visit(directive.base, arg);
		return null;
	}

	public Result visit(AstDirectiveByte directive, Arg arg) {
		this.visit(directive.label, arg);
		LC += 1;
		return null;
	}

	public Result visit(AstDirectiveEnd directive, Arg arg) {
		this.visit(directive.entry, arg);
		return null;
	}

	public Result visit(AstDirectiveEqu directive, Arg arg) {
		this.visit(directive.name, arg);
		this.visit(directive.value, arg);
		return null;
	}

	public Result visit(AstDirectiveNoBase directive, Arg arg) {
		return null;
	}

	public Result visit(AstDirectiveOrg directive, Arg arg) {
		this.visit(directive.address, arg);
		LC = directive.address.num;
		if (LC < 0)
			throw new Report.Error(directive, "ORG cannot have negative operand");
		return null;
	}

	public Result visit(AstDirectiveResb directive, Arg arg) {
		this.visit(directive.label, arg);
		this.visit(directive.size, arg);
		LC += directive.size.num;
		return null;
	}

	public Result visit(AstDirectiveStart directive, Arg arg) {
		this.visit(directive.baseAddr, arg);
		LC = directive.baseAddr.num;
		if (LC < 0)
			throw new Report.Error(directive, "START cannot have negative operand");
		return null;
	}

	public Result visit(AstInstrF1 instr, Arg arg) {
		this.visit(instr.label, arg);
		LC += 1;
		return null;
	}

	public Result visit(AstInstrF2 instr, Arg arg) {
		if (instr instanceof AstInstrF2n)
			this.visit((AstInstrF2n) instr, arg);
		else if (instr instanceof AstInstrF2r)
			this.visit((AstInstrF2r) instr, arg);
		else if (instr instanceof AstInstrF2rn)
			this.visit((AstInstrF2rn) instr, arg);
		else if (instr instanceof AstInstrF2rr)
			this.visit((AstInstrF2rr) instr, arg);
		LC += 2;
		return null;
	}

	public Result visit(AstInstrF2n instr, Arg arg) {
		this.visit(instr.label, arg);
		this.visit(instr.number, arg);
		return null;
	}

	public Result visit(AstInstrF2r instr, Arg arg) {
		this.visit(instr.label, arg);
		this.visit(instr.reg, arg);
		return null;
	}

	public Result visit(AstInstrF2rn instr, Arg arg) {
		this.visit(instr.label, arg);
		this.visit(instr.reg, arg);
		this.visit(instr.number, arg);
		return null;
	}

	public Result visit(AstInstrF2rr instr, Arg arg) {
		this.visit(instr.label, arg);
		this.visit(instr.r1, arg);
		this.visit(instr.r2, arg);
		return null;
	}

	public Result visit(AstInstrF3a instr, Arg arg) {
		this.visit(instr.label, arg);
		this.visit(instr.address, arg);
		return null;
	}

	public Result visit(AstInstrF3 instr, Arg arg) {
		if (instr instanceof AstInstrF3a)
			this.visit((AstInstrF3a) instr, arg);
		else
			this.visit(instr.label, arg);
		LC += 3;
		return null;
	}

	public Result visit(AstInstrF4 instr, Arg arg) {
		this.visit(instr.label, arg);
		this.visit(instr.f3, arg);
		LC += 1;
		return null;
	}

	public Result visit(AstAddress misc, Arg arg) {
		if (misc instanceof AstAddressName)
			this.visit((AstAddressName) misc, arg);
		else if (misc instanceof AstAddressNum)
			this.visit((AstAddressNum) misc, arg);
		return null;
	}

	public Result visit(AstAddressName misc, Arg arg) {
		return null;
	}

	public Result visit(AstAddressNum misc, Arg arg) {
		this.visit(misc.num, arg);
		return null;
	}

	public Result visit(AstLabel misc, Arg arg) {
		return null;
	}

	public Result visit(AstNumber misc, Arg arg) {
		return null;
	}

	public Result visit(AstReg misc, Arg arg) {
		return null;
	}
}
