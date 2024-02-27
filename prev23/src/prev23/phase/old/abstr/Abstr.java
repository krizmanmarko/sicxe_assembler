package prev23.phase.abstr;

import java.util.LinkedList;
import prev23.phase.*;
import prev23.data.ast.tree.*;

/**
 * Abstract syntax tree construction.
 */
public class Abstr extends Phase {

	/** The abstract syntax tree. */
	public static LinkedList<AstNode> source;
		
	/**
	 * Phase construction.
	 */
	public Abstr() {
		super("abstr");
	}

}
