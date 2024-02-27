lexer grammar PrevLexer;

@header {
	package prev23.phase.lexan;
	import prev23.common.report.*;
	import prev23.data.sym.*;
}

@members {
    @Override
	public Token nextToken() {
		return (Token) super.nextToken();
	}
}


///////////////////////////////////////////////////////////////////////////
// constants
///////////////////////////////////////////////////////////////////////////

INT_CONST
  : '0'
  | [1-9]([0-9])*
  | '0x'[0-9a-fA-F]+
  | '0o'[0-7]+
  | '0b'[01]+
;

// special handling for \'
STRING_CONST
  : 'C\''('\\\''|[ -!#-~])*'\''
  | 'C\''('\\\''|[ -!#-~])* {
	if (true) {
		//System.out.println(getText());	// prints current token
		String msg = "EOF while scanning string literal (' missing?)";
		int begLine = _tokenStartLine;
		int begColumn = _tokenStartCharPositionInLine;
		int endLine = getLine();
		int endColumn = getCharPositionInLine();
		Location loc = new Location(begLine, begColumn,
					    endLine, endColumn);
		throw new Report.Error(loc, msg);
	}
  }
;

HEX_CONST
  : 'X\''[0-9a-fA-F]*'\''
  | 'X\''[0-9a-fA-F]* {
	if (true) {
		String msg = "EOF while scanning hex literal (' missing?)";
		int begLine = _tokenStartLine;
		int begColumn = _tokenStartCharPositionInLine;
		int endLine = getLine();
		int endColumn = getCharPositionInLine();
		Location loc = new Location(begLine, begColumn,
					    endLine, endColumn);
		throw new Report.Error(loc, msg);
	}
  }
;


///////////////////////////////////////////////////////////////////////////
// symbols
///////////////////////////////////////////////////////////////////////////

MINUS : '-' ;
PLUS : '+' ;
ASTERISK : '*' ;
HASH : '#' ;
AT : '@' ;
EQUAL : '=' ;
COMMA : ',' ;


///////////////////////////////////////////////////////////////////////////
// keywords
///////////////////////////////////////////////////////////////////////////

// directive
START : 'START' ;
ORG : 'ORG' ;
BASE : 'BASE' ;
NOBASE : 'NOBASE' ;
EQU : 'EQU' ;
EXTDEF : 'EXTDEF' ;
EXTREF : 'EXTREF' ;
LTORG : 'LTORG' ;
CSECT : 'CSECT' ;
USE : 'USE' ;
RESB : 'RESB' ;
RESW : 'RESW' ;
RESF : 'RESF' ;
BYTE : 'BYTE' ;
WORD : 'WORD' ;
FLOT : 'FLOT' ;
END : 'END' ;

// mnemonic
ADD : 'ADD' ;
ADDF : 'ADDF' ;
ADDR : 'ADDR' ;
AND : 'AND' ;
CLEAR : 'CLEAR' ;
COMP : 'COMP' ;
COMPF : 'COMPF' ;
COMPR : 'COMPR' ;
DIV : 'DIV' ;
DIVF : 'DIVF' ;
DIVR : 'DIVR' ;
FIX : 'FIX' ;
FLOAT : 'FLOAT' ;
HIO : 'HIO' ;
JEQ : 'JEQ' ;
JGT : 'JGT' ;
J : 'J' ;
JLT : 'JLT' ;
JSUB : 'JSUB' ;
LDA : 'LDA' ;
LDB : 'LDB' ;
LDCH : 'LDCH' ;
LDF : 'LDF' ;
LDL : 'LDL' ;
LDS : 'LDS' ;
LDT : 'LDT' ;
LDX : 'LDX' ;
LPS : 'LPS' ;
MULF : 'MULF' ;
MUL : 'MUL' ;
MULR : 'MULR' ;
NORM : 'NORM' ;
OR : 'OR' ;
RD : 'RD' ;
RMO : 'RMO' ;
RSUB : 'RSUB' ;
SHIFTL : 'SHIFTL' ;
SHIFTR : 'SHIFTR' ;
SIO : 'SIO' ;
SSK : 'SSK' ;
STA : 'STA' ;
STB : 'STB' ;
STCH : 'STCH' ;
STF : 'STF' ;
STI : 'STI' ;
STL : 'STL' ;
STS : 'STS' ;
STSW : 'STSW' ;
STT : 'STT' ;
STX : 'STX' ;
SUBF : 'SUBF' ;
SUBR : 'SUBR' ;
SUB : 'SUB' ;
SVC : 'SVC' ;
TD : 'TD' ;
TIO : 'TIO' ;
TIXR : 'TIXR' ;
TIX : 'TIX' ;
WD : 'WD' ;

// misc

// regs
A : 'A' ;
X : 'X' ;
L : 'L' ;
B : 'B' ;
S : 'S' ;
T : 'T' ;
F : 'F' ;
PC : 'PC' ;
SW : 'SW' ;


///////////////////////////////////////////////////////////////////////////
// identifiers
///////////////////////////////////////////////////////////////////////////

ID
  : [a-zA-Z_][a-zA-Z0-9_]* {
	if (getText().length() > 6) {
		String msg = "Identifier name too long (max 6)";
		int begLine = _tokenStartLine;
		int begColumn = _tokenStartCharPositionInLine;
		int endLine = getLine();
		int endColumn = getCharPositionInLine();
		Location loc = new Location(begLine, begColumn,
					    endLine, endColumn);
		throw new Report.Error(loc, msg);
	}
  }
;


///////////////////////////////////////////////////////////////////////////
// comments
///////////////////////////////////////////////////////////////////////////

COMMENT : '.'(~[\n])* -> skip;


///////////////////////////////////////////////////////////////////////////
// whitespace
///////////////////////////////////////////////////////////////////////////

// fix tab and lexer communication
EOL
  : '\n'
  | '\r\n'
;

WHITESPACE
  : (' ' | '\t' {
	int new_pos = _tokenStartCharPositionInLine;
	new_pos += 8 - new_pos % 8;
	setCharPositionInLine(new_pos);
  }) -> skip
;
