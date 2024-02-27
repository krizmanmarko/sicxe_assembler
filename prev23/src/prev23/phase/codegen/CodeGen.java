package prev23.phase.codegen;

import prev23.phase.*;
import prev23.data.ast.tree.*;
import prev23.data.ast.tree.directive.*;
import prev23.data.ast.tree.instruction.*;
import prev23.data.ast.tree.misc.*;
import prev23.data.codegen.*;

import java.util.*;

/**
 * Object code generation
 */
public class CodeGen extends Phase {

	public static HRecord start;
	public static final LinkedList<TRecord> text = new LinkedList<TRecord>();
	public static final LinkedList<MRecord> relocations = new LinkedList<MRecord>();
	public static ERecord end;

	/**
	 * Phase construction.
	 */
	public CodeGen() {
		super("seman");
	}

}
