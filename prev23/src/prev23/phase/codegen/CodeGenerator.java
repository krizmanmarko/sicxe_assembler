package prev23.phase.codegen;

import prev23.phase.seman.*;
import prev23.common.report.*;
import prev23.data.ast.tree.*;
import prev23.data.ast.tree.directive.*;
import prev23.data.ast.tree.instruction.*;
import prev23.data.ast.tree.misc.*;
import prev23.data.codegen.*;

import java.util.*;

/**
 * Integer code generator
 */
public class CodeGenerator extends AstVisitor<Integer, Integer> {

	private int codeLength = 0;
	// b == null -> NOBASE
	// NOTE: if base is set to 0 it has no real effect
	private Integer B = null;

	private int getOpcodeF1(AstInstrF1 instr) {
		switch (instr.mnemonic) {
			case "FIX": return 0xc4;
			case "FLOAT": return 0xc0;
			case "HIO": return 0xf4;
			case "NORM": return 0xc8;
			case "SIO": return 0xf0;
			case "TIO": return 0xf8;
			default:
				throw new Report.Error(instr, "Invalid mnemonic");
		}
	}

	private int getOpcodeF2n(AstInstrF2n instr) {
		int opcode = 0;
		int number = this.visit(instr.number, null) & 0xf;
		switch (instr.mnemonic) {
			case "SVC": opcode = 0xb0; break;
			default:
				throw new Report.Error(instr, "Invalid mnemonic");
		}
		return (opcode << 8) | (number << 4);
	}

	private int getOpcodeF2r(AstInstrF2r instr) {
		int opcode = 0;
		int regnum = this.visit(instr.reg, null) & 0xf;
		switch (instr.mnemonic) {
			case "CLEAR": opcode = 0xb4; break;
			case "TIXR": opcode = 0xb8; break;
			default:
				throw new Report.Error(instr, "Invalid mnemonic");
		}
		return (opcode << 8) | (regnum << 4);
	}
	private int getOpcodeF2rn(AstInstrF2rn instr) {
		int opcode = 0;
		int regnum = this.visit(instr.reg, null) & 0xf;
		int num = this.visit(instr.number, null) & 0xf;
		switch (instr.mnemonic) {
			case "SHIFTL": opcode = 0xa4; break;
			case "SHIFTR": opcode = 0xa8; break;
			default:
				throw new Report.Error(instr, "Invalid mnemonic");
		}
		return (opcode << 8) | (regnum << 4) | num;
	}

	private int getOpcodeF2rr(AstInstrF2rr instr) {
		int opcode = 0;
		int r1 = this.visit(instr.r1, null) & 0xf;
		int r2 = this.visit(instr.r2, null) & 0xf;
		switch (instr.mnemonic) {
			case "ADDR": opcode = 0x90; break;
			case "COMPR": opcode = 0xa0; break;
			case "DIVR": opcode = 0x9c; break;
			case "MULR": opcode = 0x98; break;
			case "RMO": opcode = 0xac; break;
			case "SUBR": opcode = 0x94; break;
			default:
				throw new Report.Error(instr, "Invalid mnemonic");
		}
		return (opcode << 8) | (r1 << 4) | r2;
	}

	private int mnemonicF34ToOpcode(String mnemonic) {
		int opcode = -1;
		switch (mnemonic) {
			case "ADD": opcode = 0x18; break;
			case "ADDF": opcode = 0x58; break;
			case "AND": opcode = 0x40; break;
			case "COMP": opcode = 0x28; break;
			case "COMPF": opcode = 0x88; break;
			case "DIV": opcode = 0x24; break;
			case "DIVF": opcode = 0x64; break;
			case "J": opcode = 0x3c; break;
			case "JEQ": opcode = 0x30; break;
			case "JGT": opcode = 0x34; break;
			case "JLT": opcode = 0x38; break;
			case "JSUB": opcode = 0x48; break;
			case "LDA": opcode = 0x00; break;
			case "LDB": opcode = 0x68; break;
			case "LDCH": opcode = 0x50; break;
			case "LDF": opcode = 0x70; break;
			case "LDL": opcode = 0x08; break;
			case "LDS": opcode = 0x6c; break;
			case "LDT": opcode = 0x74; break;
			case "LDX": opcode = 0x04; break;
			case "LPS": opcode = 0xd0; break;
			case "MUL": opcode = 0x20; break;
			case "MULF": opcode = 0x60; break;
			case "OR": opcode = 0x44; break;
			case "RD": opcode = 0xd8; break;
			case "SSK": opcode = 0xec; break;
			case "STA": opcode = 0x0c; break;
			case "STB": opcode = 0x78; break;
			case "STCH": opcode = 0x54; break;
			case "STF": opcode = 0x80; break;
			case "STI": opcode = 0xd4; break;
			case "STL": opcode = 0x14; break;
			case "STS": opcode = 0x7c; break;
			case "STSW": opcode = 0xe8; break;
			case "STT": opcode = 0x84; break;
			case "STX": opcode = 0x10; break;
			case "SUB": opcode = 0x1c; break;
			case "SUBF": opcode = 0x5c; break;
			case "TD": opcode = 0xe0; break;
			case "TIX": opcode = 0x2c; break;
			case "WD": opcode = 0xdc; break;
		}
		return opcode;
	}

	private int getOpcodeF3a(AstInstrF3a instr) {
		int opcode = mnemonicF34ToOpcode(instr.mnemonic);
		if (opcode == -1)
			throw new Report.Error(instr, "Invalid mnemonic");

		int x = instr.x ? 1 : 0;
		// arg[0:2] -> format, arg[3] -> x
		int address = this.visit(instr.address, (x << 3) | 3);		// with nixbpe
		// [23:18][ni][xbpe][11:0]
		return (opcode << 16) | address;
	}

	private int getOpcodeF3(AstInstrF3 instr) {
		// RSUB
		int opcode = 0x4c;
		return opcode << 16;
	}

	private int getOpcodeF4(AstInstrF4 instr) {
		AstInstrF3a f3 = null;
		if (instr.f3 instanceof AstInstrF3a)
			f3 = (AstInstrF3a) instr.f3;
		else
			throw new Report.Error(instr, "RSUB cannot be F4");

		int opcode = mnemonicF34ToOpcode(f3.mnemonic);
		if (opcode == -1)
			throw new Report.Error(instr, "Invalid mnemonic");

		Integer x = f3.x ? 1 : 0;
		// arg[3] -> x, arg[2:0] -> format
		int address = this.visit(f3.address, (x << 3) | 4);		// with nixbpe

		//[31:26][ni][xbpe][19:0]
		return (opcode << 24) | address;
	}

	private int getOpcode(AstNode instr) {
		if (instr instanceof AstInstrF1) {
			return getOpcodeF1((AstInstrF1) instr);
		} else if (instr instanceof AstInstrF2n) {
			return getOpcodeF2n((AstInstrF2n) instr);
		} else if (instr instanceof AstInstrF2r) {
			return getOpcodeF2r((AstInstrF2r) instr);
		} else if (instr instanceof AstInstrF2rn) {
			return getOpcodeF2rn((AstInstrF2rn) instr);
		} else if (instr instanceof AstInstrF2rr) {
			return getOpcodeF2rr((AstInstrF2rr) instr);
		} else if (instr instanceof AstInstrF3a) {
			return getOpcodeF3a((AstInstrF3a) instr);
		} else if (instr instanceof AstInstrF3) {
			return getOpcodeF3((AstInstrF3) instr);
		} else if (instr instanceof AstInstrF4) {
			return getOpcodeF4((AstInstrF4) instr);
		} else {
			throw new Report.Error(instr, "Oops");
		}
	}

	private boolean usePCRelative(int address) {
		address -= 3;
		if ((LC + 3) - 2048 > address || address > (LC + 3) + 2047)
			return false;
		return true;
	}

	private boolean useBRelative(int address) {
		if (B == null)
			return false;
		if (B > address || address > B + 4095)
			return false;
		return true;
	}

	private boolean useAbsoluteF3(int address) {
		if (address > 4095)
			return false;
		return true;
	}

	private boolean immediate(int n, int i) {
		if (n == 0 && i == 1)
			return true;
		return false;
	}

	private boolean needsRelocation(int n, int i, int b, int p, AstAddress addr) {
		if (b != 0 || p != 0)
			return false;
		if (immediate(n, i) && addr instanceof AstAddressNum)
			return false;
		return true;
	} 

	@Override
	public Integer visit(LinkedList<AstNode> ll, Integer arg) {
		for (AstNode n : ll) {
			if (n instanceof AstInstrF1)
				codeLength += 1;
			else if (n instanceof AstInstrF2)
				codeLength += 2;
			else if (n instanceof AstInstrF3)
				codeLength += 3;
			else if (n instanceof AstInstrF4)
				codeLength += 4;
			else if (n instanceof AstDirectiveByte)
				codeLength += 1;
			else if (n instanceof AstDirectiveResb)
				codeLength += this.visit((AstDirectiveResb) n, arg);
			// add support for WORB BYTE...?
		}
		for (AstNode n : ll)
			this.visit(n, arg);
		return null;
	}

	@Override
	public Integer visit(AstDirectiveStart directive, Integer arg) {
		String name = directive.progName;
		int baseAddr = directive.baseAddr.num;
		int length = codeLength;
		LC = baseAddr;
		HRecord h = new HRecord(name, baseAddr, length);
		CodeGen.start = h;
		return null;
	}

	@Override
	public Integer visit(AstDirectiveEnd directive, Integer arg) {
		int entry = (Integer) this.visit(directive.entry, 0);
		ERecord e = new ERecord(entry);
		CodeGen.end = e;
		return null;
	}

	@Override
	public Integer visit(AstDirectiveByte directive, Integer arg) {
		TRecord t = new TRecord(LC, 1, directive.value);
		CodeGen.text.add(t);
		LC += 1;
		return null;
	}

	@Override
	public Integer visit(AstDirectiveResb directive, Integer arg) {
		int size = this.visit(directive.size, arg);
		LC += size;
		return size;
	}

	@Override
	public Integer visit(AstDirectiveBase directive, Integer arg) {
		B = this.visit(directive.base, arg);
		//Report.info(directive, "B = " + B);
		return null;
	}

	@Override
	public Integer visit(AstDirectiveNoBase directive, Integer arg) {
		B = null;
		//Report.info(directive, "B = " + B);
		return null;
	}

	@Override
	public Integer visit(AstInstrF1 instr, Integer arg) {
		TRecord t = new TRecord(LC, 1, getOpcode(instr));
		CodeGen.text.add(t);
		LC += 1;
		return null;
	}

	@Override
	public Integer visit(AstInstrF2 instr, Integer arg) {
		TRecord t = new TRecord(LC, 2, getOpcode(instr));
		CodeGen.text.add(t);
		LC += 2;
		return null;
	}

	@Override
	public Integer visit(AstInstrF3 instr, Integer arg) {
		TRecord t = new TRecord(LC, 3, getOpcode(instr));
		CodeGen.text.add(t);
		LC += 3;
		return null;
	}

	@Override
	public Integer visit(AstInstrF4 instr, Integer arg) {
		// TODO: tle mal pazi kako se LC racuna (ker F4 rata F3)
		TRecord t = new TRecord(LC, 4, getOpcode(instr));
		CodeGen.text.add(t);
		LC += 4;
		return null;
	}

	// arg = arg[0:2] -> format, arg[3] -> x
	@Override
	public Integer visit(AstAddress addr, Integer arg) {
		int code = 0;
		int format = arg & 0x7;
		int n = addr.n ? 1 : 0;
		int i = addr.i ? 1 : 0;
		int x = (arg & (1 << 3)) != 0 ? 1 : 0;
		int b = 0;
		int p = 0;
		int e = 0;
		int address;
		if (addr instanceof AstAddressNum) {
			address = this.visit(((AstAddressNum) addr).num, arg);
		} else {
			AstLabel l = SemAn.lookup.get((AstAddressName) addr);
			address = SemAn.symTab.get(l);
		}
		if (arg == 0) { // directive (END)
			return address;
		}

		/*
		relocate only
		f3, b=0, p=0	(except #num)
		f4		(except #num)

		dont relocate
		f3, #label
		f4, #label
		*/

		if (format == 4) {
			//[31:26][25:n][24:i][23:x][22:b][21:p][20:e][19:0]
			e = 1;
			//Report.info(addr, "(" + addr.log() + ") absolute F4: " + address);

			// absolute addressing
			code |= n << 25;
			code |= i << 24;
			code |= x << 23;
			code |= b << 22;	// 0
			code |= p << 21;	// 0
			code |= e << 20;	// 1
			code |= address & 0xfffff;

			// generate MRecord
			if (needsRelocation(n, i, b, p, addr)) {
				MRecord m = new MRecord(LC + 1, 5);
				CodeGen.relocations.add(m);
			}
			return code;
		}

		// handle format 3
		if (usePCRelative(address)) {
			p = 1;
			address -= LC;
			address -= 3;
			//Report.info(addr, "(" + addr.log() + ") LC: " + LC + ", offset: " + address);
		} else if (useBRelative(address)) {
			b = 1;
			address -= B;
			//Report.info(addr, "(" + addr.log() + ") B: " + B + ", offset: " + address);
		} else if (useAbsoluteF3(address)) {
			//Report.info(addr, "(" + addr.log() + ") absolute: " + address);
		} else {
			throw new Report.Error(addr, "Cannot address with F3 (try F4)");
		}

		// [23:18][nixbpe][11:0]
		code |= n << 17;
		code |= i << 16;
		code |= x << 15;
		code |= b << 14;
		code |= p << 13;
		code |= e << 12;
		code |= address & 0xfff;

		// generate MRecord
		if (needsRelocation(n, i, b, p, addr)) {
			MRecord m = new MRecord(LC + 1, 3);
			CodeGen.relocations.add(m);
		}

		return code;
	}

	@Override
	public Integer visit(AstNumber num, Integer arg) {
		return num.num;
	}

	@Override
	public Integer visit(AstReg reg, Integer arg) {
		return reg.regnum;
	}

//AstDirectiveBase
//AstDirectiveEnd
//AstDirectiveEqu
//AstDirectiveNoBase
//AstDirectiveOrg
//AstDirectiveStart
//
//AstInstrF1
//AstInstrF2
//AstInstrF2n
//AstInstrF2r
//AstInstrF2rn
//AstInstrF2rr
//AstInstrF3a
//AstInstrF3
//AstInstrF4
//
//AstAddress
//AstAddressName
//AstAddressNum
//AstLabel
//AstNumber
//AstReg
}
