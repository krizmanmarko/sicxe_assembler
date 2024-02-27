package prev23.phase.seman;

import prev23.phase.*;
import prev23.data.ast.tree.*;
import prev23.data.ast.tree.directive.*;
import prev23.data.ast.tree.instruction.*;
import prev23.data.ast.tree.misc.*;

import java.util.*;

/**
 * Semantic analysis.
 */
public class SemAn extends Phase {

	public static final HashMap<AstLabel, Integer> symTab = new HashMap<AstLabel, Integer>();
	public static final HashMap<AstAddressName, AstLabel> lookup = new HashMap<AstAddressName, AstLabel>();

	/**
	 * Phase construction.
	 */
	public SemAn() {
		super("seman");
	}

}
