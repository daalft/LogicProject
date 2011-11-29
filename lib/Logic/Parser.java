package logic;

import java.util.Vector;

public class Parser {

	private Vector<String> ops;
	private static Vector<String> consts;
	private static Vector<InterCon> exp;
	private static Vector<Constant> realconst;
	private static InterCon inter;

	public Parser () {
		ops = new Vector<String>();
		exp = new Vector<InterCon>();
		consts = new Vector<String>();
		realconst = new Vector<Constant>();
		inter = new InterCon();
	}

	public void parse (String s) {
		String[] c = s.split(" ");
		for (int i = 0; i < c.length; i++) {
			if (i+1 < c.length && (c[i]+c[i+1]).matches("NOT[A-Z]")) {
				if (!consts.contains(c[i+1])) {
					consts.add(c[i+1]);
				}
				
				if (ops.size() == 0) {
				inter.setR1(c[i] + " " + c[i+1]);
				
				} else {
					inter.setR2(c[i] + " " + c[i+1]);
					ops.clear();
					exp.add(inter);
					inter = new InterCon();
				}
				i += (i + 2 < c.length)?2:1;
			}
			if (c[i].matches("[A-Z]")) {
				if (!consts.contains(c[i])) {
					consts.add(c[i]);
				}
				if (ops.size() == 0) {
					inter.setR1(c[i]);
					
				} else {
					
					inter.setR2(c[i]);
					ops.clear();
					exp.add(inter);
					inter = new InterCon();
				}
			} else {
		
				ops.add(c[i]);
				if (inter.getR1()!=null)
					inter.setOp(Operators.valueOf(c[i]));
				else {
					inter.setR1(exp.toString());
					exp.clear();
					inter.setOp(Operators.valueOf(c[i]));
				}
				
			}

		}
		
	}
	
	public void operate () {
		for (InterCon s : exp) {
			switch (s.getOp()) {
			case AND: 
			case OR:
			case NAND:
			case NOR:
			case XOR:
			case EQUIV:
			case IMPLIES:
				default: return;
			}
		}
	}
	
	public static void main(String[] args) {
		Parser p = new Parser();
		p.parse("A EQUIV C AND C OR B EQUIV NOT NOT A OR C OR B AND C");
		Constant A = new Constant("A", Constant.generatePattern(3, 0));
		Constant B = new Constant("B", Constant.generatePattern(3, 1));
		Constant C = new Constant("C", Constant.generatePattern(3, 2));
		System.out.println(exp.toString());
		System.out.println(inter.toString());
		System.out.println(consts.toString());
		for (String s : consts) {
			realconst.add(new Constant(s, Constant.generatePattern(consts.size(), consts.indexOf(s))));
		}
		for (Constant c : realconst) {
			System.out.println(c);
		}
		System.out.println((A.equiv(C).and(C.or(B))).equiv(((A.not().or(C))).not().or(B.and(C))));
	
	}

}
