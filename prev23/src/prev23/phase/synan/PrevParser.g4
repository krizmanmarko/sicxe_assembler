parser grammar PrevParser;

@header {
	package prev23.phase.synan;
	import prev23.data.ast.tree.*;
	import prev23.data.ast.tree.misc.*;
	import prev23.data.ast.tree.directive.*;
	import prev23.data.ast.tree.instruction.*;

	import java.util.*;

	import prev23.common.report.*;
	import prev23.phase.lexan.*;
}

@members {
        private Location loc(Token tok) {
                return new Location((prev23.data.sym.Token) tok);
        }

        private Location loc(Locatable loc) {
                return new Location(loc);
        }

        private Location loc(Token tok1, Token tok2) {
                return new Location((prev23.data.sym.Token) tok1,
                                    (prev23.data.sym.Token) tok2);
        }

        private Location loc(Token tok1, Locatable loc2) {
                return new Location((prev23.data.sym.Token) tok1, loc2);
        }

        private Location loc(Locatable loc1, Token tok2) {
                return new Location(loc1, (prev23.data.sym.Token) tok2);
        }

        private Location loc(Locatable loc1, Locatable loc2) {
                return new Location(loc1, loc2);
        }
}


options{
    tokenVocab=PrevLexer;
}


source returns [LinkedList<AstNode> ll]
  : lines EOF
  {
	$ll = $lines.ll;
  }
;

lines returns [LinkedList<AstNode> ll]
  : line EOL lines
  {
	$ll = $lines.ll;
	if ($line.n != null)
		$ll.addFirst($line.n);
  }
  |
  {
	$ll = new LinkedList<AstNode>();
  }
;

line returns [AstNode n]
  : directive
  {
	$n = $directive.n;
  }
  | instruction
  {
	$n = $instruction.n;
  }
  |
  {
	$n = null;
  }
;

directive
  returns [AstNode n]
//: ID START address	// this is on prosojnice, but complicates things significantly when calculating addresses
  : ID START number
  {
	String name = $ID.getText();
	$n = new AstDirectiveStart(loc($ID, $number.num), name, $number.num);
  }
  | END address
  {
	$n = new AstDirectiveEnd(loc($END, $address.addr), $address.addr);
  }
// I do not intend on fixing this ever, it is a design choice
//| ORG address	// this is on prosojnice, but complicates things significantly when calculating addresses
  | ORG number
  {
	$n = new AstDirectiveOrg(loc($ORG, $number.num), $number.num);
  }
//| BASE address
  | BASE number
  {
	$n = new AstDirectiveBase(loc($BASE, $number.num), $number.num);
  }
  | NOBASE
  {
	$n = new AstDirectiveNoBase(loc($NOBASE));
  }
  | ID EQU expression
  {
	String name = $ID.getText();
	AstLabel l = new AstLabel(loc($ID), name);
	$n = new AstDirectiveEqu(loc($ID, $expression.num), l, $expression.num);
  }
  | EXTDEF ID (COMMA ID)*
  {
	if (true) throw new Report.Error(loc($EXTDEF), "EXTDEF not implemented");
  }
  | EXTREF ID (COMMA ID)*
  {
	if (true) throw new Report.Error(loc($EXTREF), "EXTREF not implemented");
  }
  | LTORG
  {
	if (true) throw new Report.Error(loc($LTORG), "LTORG not implemented");
  }
  | ID CSECT
  {
	if (true) throw new Report.Error(loc($CSECT), "CSECT not implemented");
  }
  | USE (ID)?
  {
	if (true) throw new Report.Error(loc($USE), "USE not implemented");
  }
  | possible_label RESB number
  {
	$n = new AstDirectiveResb(loc($RESB, $number.num), $possible_label.label, $number.num);
  }
  | possible_label RESW number
  {
	if (true) throw new Report.Error(loc($RESW), "RESW not implemented");
  }
  | possible_label RESF number
  {
	if (true) throw new Report.Error(loc($RESF), "RESF not implemented");
  }
  | possible_label BYTE data
  {
	$n = new AstDirectiveByte(loc($BYTE), $possible_label.label, $data.value);
  }
  | possible_label WORD data
  {
	if (true) throw new Report.Error(loc($WORD), "WORD not implemented");
  }
  | possible_label FLOT data
  {
	if (true) throw new Report.Error(loc($FLOT), "FLOT not implemented");
  }
;

instruction returns [AstNode n]
  : possible_label f1
  {
	$f1.instr.setLabel($possible_label.label);
	$n = $f1.instr;
  }
  | possible_label f2
  {
	$f2.instr.setLabel($possible_label.label);
	$n = $f2.instr;
  }
  | possible_label f3
  {
	$f3.instr.setLabel($possible_label.label);
	$n = $f3.instr;
  }
  | possible_label f4
  {
	$f4.instr.setLabel($possible_label.label);
	$n = $f4.instr;
  }
;

possible_label returns [AstLabel label]
  : ID
  {
	$label = new AstLabel(loc($ID), $ID.getText());
  }
  |
  {
	$label = null;
  }
;

f1 returns [AstInstrF1 instr]
  : FIX
  {
	$instr = new AstInstrF1(loc($FIX), $FIX.getText());
  }
  | FLOAT
  {
	$instr = new AstInstrF1(loc($FLOAT), $FLOAT.getText());
  }
  | HIO
  {
	$instr = new AstInstrF1(loc($HIO), $HIO.getText());
  }
  | NORM
  {
	$instr = new AstInstrF1(loc($NORM), $NORM.getText());
  }
  | SIO
  {
	$instr = new AstInstrF1(loc($SIO), $SIO.getText());
  }
  | TIO
  {
	$instr = new AstInstrF1(loc($TIO), $TIO.getText());
  }
;

f2 returns [AstInstrF2 instr]
  : ADDR r1=reg COMMA r2=reg
  {
	$instr = new AstInstrF2rr(loc($ADDR, $r2.r), $ADDR.getText(), $r1.r, $r2.r);
  }
  | CLEAR reg
  {
	$instr = new AstInstrF2r(loc($CLEAR, $reg.r), $CLEAR.getText(), $reg.r);
  }
  | COMPR r1=reg COMMA r2=reg
  {
	$instr = new AstInstrF2rr(loc($COMPR, $r2.r), $COMPR.getText(), $r1.r, $r2.r);
  }
  | DIVR r1=reg COMMA r2=reg
  {
	$instr = new AstInstrF2rr(loc($DIVR, $r2.r), $DIVR.getText(), $r1.r, $r2.r);
  }
  | MULR r1=reg COMMA r2=reg
  {
	$instr = new AstInstrF2rr(loc($MULR, $r2.r), $MULR.getText(), $r1.r, $r2.r);
  }
  | RMO r1=reg COMMA r2=reg
  {
	$instr = new AstInstrF2rr(loc($RMO, $r2.r), $RMO.getText(), $r1.r, $r2.r);
  }
  | SHIFTL reg COMMA number
  {
	$instr = new AstInstrF2rn(
		loc($SHIFTL, $number.num),
		$SHIFTL.getText(),
		$reg.r,
		$number.num);
  }
  | SHIFTR reg COMMA number
  {
	$instr = new AstInstrF2rn(
		loc($SHIFTR, $number.num),
		$SHIFTR.getText(),
		$reg.r,
		$number.num);
  }
  | SUBR r1=reg COMMA r2=reg
  {
	$instr = new AstInstrF2rr(loc($SUBR, $r2.r), $SUBR.getText(), $r1.r, $r2.r);
  }
  | SVC number
  {
	$instr = new AstInstrF2n(loc($SVC, $number.num), $SVC.getText(), $number.num);
  }
  | TIXR reg
  {
	$instr = new AstInstrF2r(loc($TIXR, $reg.r), $TIXR.getText(), $reg.r);
  }
;

f3 returns [AstInstrF3 instr]
  : ADD address possible_x
  {
	$instr = new AstInstrF3a(loc($ADD, $address.addr), $ADD.getText(), $address.addr, $possible_x.x);
  }
  | ADDF address possible_x
  {
	$instr = new AstInstrF3a(loc($ADDF, $address.addr), $ADDF.getText(), $address.addr, $possible_x.x);
  }
  | AND address possible_x
  {
	$instr = new AstInstrF3a(loc($AND, $address.addr), $AND.getText(), $address.addr, $possible_x.x);
  }
  | COMP address possible_x
  {
	$instr = new AstInstrF3a(loc($COMP, $address.addr), $COMP.getText(), $address.addr, $possible_x.x);
  }
  | COMPF address possible_x
  {
	$instr = new AstInstrF3a(loc($COMPF, $address.addr), $COMPF.getText(), $address.addr, $possible_x.x);
  }
  | DIV address possible_x
  {
	$instr = new AstInstrF3a(loc($DIV, $address.addr), $DIV.getText(), $address.addr, $possible_x.x);
  }
  | DIVF address possible_x
  {
	$instr = new AstInstrF3a(loc($DIVF, $address.addr), $DIVF.getText(), $address.addr, $possible_x.x);
  }
  | J address possible_x
  {
	$instr = new AstInstrF3a(loc($J, $address.addr), $J.getText(), $address.addr, $possible_x.x);
  }
  | JEQ address possible_x
  {
	$instr = new AstInstrF3a(loc($JEQ, $address.addr), $JEQ.getText(), $address.addr, $possible_x.x);
  }
  | JGT address possible_x
  {
	$instr = new AstInstrF3a(loc($JGT, $address.addr), $JGT.getText(), $address.addr, $possible_x.x);
  }
  | JLT address possible_x
  {
	$instr = new AstInstrF3a(loc($JLT, $address.addr), $JLT.getText(), $address.addr, $possible_x.x);
  }
  | JSUB address possible_x
  {
	$instr = new AstInstrF3a(loc($JSUB, $address.addr), $JSUB.getText(), $address.addr, $possible_x.x);
  }
  | LDA address possible_x
  {
	$instr = new AstInstrF3a(loc($LDA, $address.addr), $LDA.getText(), $address.addr, $possible_x.x);
  }
  | LDB address possible_x
  {
	$instr = new AstInstrF3a(loc($LDB, $address.addr), $LDB.getText(), $address.addr, $possible_x.x);
  }
  | LDCH address possible_x
  {
	$instr = new AstInstrF3a(loc($LDCH, $address.addr), $LDCH.getText(), $address.addr, $possible_x.x);
  }
  | LDF address possible_x
  {
	$instr = new AstInstrF3a(loc($LDF, $address.addr), $LDF.getText(), $address.addr, $possible_x.x);
  }
  | LDL address possible_x
  {
	$instr = new AstInstrF3a(loc($LDL, $address.addr), $LDL.getText(), $address.addr, $possible_x.x);
  }
  | LDS address possible_x
  {
	$instr = new AstInstrF3a(loc($LDS, $address.addr), $LDS.getText(), $address.addr, $possible_x.x);
  }
  | LDT address possible_x
  {
	$instr = new AstInstrF3a(loc($LDT, $address.addr), $LDT.getText(), $address.addr, $possible_x.x);
  }
  | LDX address possible_x
  {
	$instr = new AstInstrF3a(loc($LDX, $address.addr), $LDX.getText(), $address.addr, $possible_x.x);
  }
  | LPS address possible_x
  {
	$instr = new AstInstrF3a(loc($LPS, $address.addr), $LPS.getText(), $address.addr, $possible_x.x);
  }
  | MUL address possible_x
  {
	$instr = new AstInstrF3a(loc($MUL, $address.addr), $MUL.getText(), $address.addr, $possible_x.x);
  }
  | MULF address possible_x
  {
	$instr = new AstInstrF3a(loc($MULF, $address.addr), $MULF.getText(), $address.addr, $possible_x.x);
  }
  | OR address possible_x
  {
	$instr = new AstInstrF3a(loc($OR, $address.addr), $OR.getText(), $address.addr, $possible_x.x);
  }
  | RD address possible_x
  {
	$instr = new AstInstrF3a(loc($RD, $address.addr), $RD.getText(), $address.addr, $possible_x.x);
  }
  | RSUB
  {
	$instr = new AstInstrF3(loc($RSUB), $RSUB.getText());
  }
  | SSK address possible_x
  {
	$instr = new AstInstrF3a(loc($SSK, $address.addr), $SSK.getText(), $address.addr, $possible_x.x);
  }
  | STA address possible_x
  {
	$instr = new AstInstrF3a(loc($STA, $address.addr), $STA.getText(), $address.addr, $possible_x.x);
  }
  | STB address possible_x
  {
	$instr = new AstInstrF3a(loc($STB, $address.addr), $STB.getText(), $address.addr, $possible_x.x);
  }
  | STCH address possible_x
  {
	$instr = new AstInstrF3a(loc($STCH, $address.addr), $STCH.getText(), $address.addr, $possible_x.x);
  }
  | STF address possible_x
  {
	$instr = new AstInstrF3a(loc($STF, $address.addr), $STF.getText(), $address.addr, $possible_x.x);
  }
  | STI address possible_x
  {
	$instr = new AstInstrF3a(loc($STI, $address.addr), $STI.getText(), $address.addr, $possible_x.x);
  }
  | STL address possible_x
  {
	$instr = new AstInstrF3a(loc($STL, $address.addr), $STL.getText(), $address.addr, $possible_x.x);
  }
  | STS address possible_x
  {
	$instr = new AstInstrF3a(loc($STS, $address.addr), $STS.getText(), $address.addr, $possible_x.x);
  }
  | STSW address possible_x
  {
	$instr = new AstInstrF3a(loc($STSW, $address.addr), $STSW.getText(), $address.addr, $possible_x.x);
  }
  | STT address possible_x
  {
	$instr = new AstInstrF3a(loc($STT, $address.addr), $STT.getText(), $address.addr, $possible_x.x);
  }
  | STX address possible_x
  {
	$instr = new AstInstrF3a(loc($STX, $address.addr), $STX.getText(), $address.addr, $possible_x.x);
  }
  | SUB address possible_x
  {
	$instr = new AstInstrF3a(loc($SUB, $address.addr), $SUB.getText(), $address.addr, $possible_x.x);
  }
  | SUBF address possible_x
  {
	$instr = new AstInstrF3a(loc($SUBF, $address.addr), $SUBF.getText(), $address.addr, $possible_x.x);
  }
  | TD address possible_x
  {
	$instr = new AstInstrF3a(loc($TD, $address.addr), $TD.getText(), $address.addr, $possible_x.x);
  }
  | TIX address possible_x
  {
	$instr = new AstInstrF3a(loc($TIX, $address.addr), $TIX.getText(), $address.addr, $possible_x.x);
  }
  | WD address possible_x
  {
	$instr = new AstInstrF3a(loc($WD, $address.addr), $WD.getText(), $address.addr, $possible_x.x);
  }
;

possible_x returns [boolean x]
  : COMMA X { $x = true; }
  | { $x = false; }
;

f4 returns [AstInstrF4 instr]
  : PLUS f3
  {
	$instr = new AstInstrF4(loc($PLUS, $f3.instr), $f3.instr);
  }
;

address returns [AstAddress addr]
  : addressing_mode number
  {
	boolean n = ($addressing_mode.ni & 2) != 0 ? true : false;
	boolean i = ($addressing_mode.ni & 1) != 0 ? true : false;
	$addr = new AstAddressNum(loc($number.num), $number.num, n, i);
  }
  | addressing_mode ID
  {
	boolean n = ($addressing_mode.ni & 2) != 0 ? true : false;
	boolean i = ($addressing_mode.ni & 1) != 0 ? true : false;
	$addr = new AstAddressName(loc($ID), $ID.getText(), n, i);
  }
;

addressing_mode returns [int ni]
  : HASH { $ni = 1; }
  | { $ni = 3; }	// TODO: stari SIC
  | AT { $ni = 2; }
  | EQUAL
  {
	if (true)
		throw new Report.Error(loc($EQUAL), "Literals not implemented");
  }
;

// for now any calculations are omitted
expression returns [AstNumber num]
  : number
  {
	$num = $number.num;
  }
;

number returns [AstNumber num]
  : signed INT_CONST
  {
	Integer num = 0x41;
	if ($INT_CONST.getText().startsWith("0x")) {
		num = Integer.parseInt($INT_CONST.getText().substring(2), 16);
	}
	else if ($INT_CONST.getText().startsWith("0o"))
		num = Integer.parseInt($INT_CONST.getText().substring(2), 8);
	else if ($INT_CONST.getText().startsWith("0b"))
		num = Integer.parseInt($INT_CONST.getText().substring(2), 2);
	else
		num = Integer.parseInt($INT_CONST.getText());
	$num = new AstNumber(loc($INT_CONST), $signed.negative, num);
  }
;

signed returns [boolean negative]
  : PLUS { $negative = false; }
  | MINUS { $negative = true; }
  | { $negative = false; }
;

// memory reservations
// TODO: implement this if you want RESB, RESW
data returns [int value]
  : number
  {
	$value = $number.num.num & 0xff;
	if ($value != $number.num.num)
		Report.warning("Byte was truncated");
  }
//  | STRING_CONST
//  | HEX_CONST
;

reg returns [AstReg r]
  : A { $r = new AstReg(loc($A), 0); }
  | X { $r = new AstReg(loc($X), 1); }
  | L { $r = new AstReg(loc($L), 2); }
  | B { $r = new AstReg(loc($B), 3); }
  | S { $r = new AstReg(loc($S), 4); }
  | T { $r = new AstReg(loc($T), 5); }
  | F { $r = new AstReg(loc($F), 6); }
  | PC { $r = new AstReg(loc($PC), 8); }
  | SW { $r = new AstReg(loc($SW), 9); }
;
