package prev23.phase.debuginfo;

import prev23.phase.*;
import prev23.phase.synan.*;
import prev23.phase.seman.*;
import prev23.phase.codegen.*;
import prev23.data.ast.tree.*;
import prev23.data.ast.tree.directive.*;
import prev23.data.ast.tree.instruction.*;
import prev23.data.ast.tree.misc.*;
import prev23.data.codegen.*;

import java.util.*;

/**
 * Pretty printing
 */
public class DebugInfo extends Phase {

	public static String output;

	public String log() {
		StringBuilder sb = new StringBuilder();

		sb.append("-------------------\n");
		sb.append("--- Source code ---\n");
		sb.append("-------------------\n");
		for (AstNode n : SynAn.tree.ll)
			sb.append(n.log() + "\n");

		sb.append("\n");
		sb.append("--------------------\n");
		sb.append("--- Symbol table ---\n");
		sb.append("--------------------\n");
		sb.append(SemAn.symTab + "\n");

		sb.append("\n");
		sb.append("-------------------\n");
		sb.append("--- Object code ---\n");
		sb.append("-------------------\n");
		sb.append(CodeGen.start.log() + "\n");
		for (TRecord t : CodeGen.text)
			sb.append(t.log() + "\n");
		for (MRecord m : CodeGen.relocations)
			sb.append(m.log() + "\n");
		sb.append(CodeGen.end.log() + "\n");

		return sb.toString();
	}

	/**
	 * Phase construction.
	 */
	public DebugInfo() {
		super("seman");
	}

}
