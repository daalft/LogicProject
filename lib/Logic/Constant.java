package logic;

public class Constant {

	private String name;
	private int[] array;
	private int[] result;

	public Constant(String name, int[] array) {
		this.name = name;
		this.array = array;
	}

	public String getName () {
		return name;
	}

	public int[] getArray () {
		return array;
	}

	public void setName (String name) {
		this.name = name;
	}

	public void setArray (int[] array) {
		this.array = array;
	}

	public Constant and (Constant c) {
		return function(c, 0);
	}
	
	public Constant or (Constant c) {
		return function(c, 1);
	}
	
	public Constant implies (Constant c) {
		return function(c, 2);
	}
	
	public Constant equiv (Constant c) {
		return function(c, 3);
	}

	public Constant not () {
		return function(null, 4);
	}
	
	public Constant nand (Constant c) {
		return function(c, 0).not(); 
	}
	
	public Constant nor (Constant c) {
		return function(c, 1).not();
	}
	
	public Constant xor (Constant c) {
		return function(c, 3).not();
	}
	
	private Constant function (Constant c, int func) {
		/*
		 * 0 = AND
		 * 1 = OR
		 * 2 = IMPLIES
		 * 3 = EQUIVALENCE
		 * 4 = NOT
		 */
		result = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			switch(func) {
			case 0: result[i] = array[i] & c.array[i]; break;
			case 1: result[i] = array[i] | c.array[i]; break;
			case 2: result[i] = (array[i] == 0 || (array[i] == 1 && c.array[i] == 1)) ? 1 : 0; break;
			case 3: result[i] = array[i] == c.array[i] ? 1 : 0; break;
			case 4: result[i] = (array[i] == 0) ? 1 : 0; break;
			default: 
			}
		}
		return new Constant("R", result);
	}

	public static int[] generatePattern (int t, int n) {
		//t = total = size of unique names array
		//n = current number = index of name in unique names array
		//generates binary arrays in the form {0,0,1,1} and {0,1,0,1} for t = 2, n = 0 and 1 respectively
		int q = 0;
		int w = (int) Math.pow(2, t);
		int[] res = new int[w];
		int x = (int) Math.pow(2, n) - n/2 -1;
		for (int i = 0; i < w; i++) {
			res[i] = q;
			if (Math.abs((i+1)%(Math.pow(2,(t-1-x))))==0)
				q = (q==0)?1:0;
		}
		return res;
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
		}
		return name + " " + sb.toString();
	}
	
	public void run () {
		generatePattern(3,0);
	}
	public static void main(String[] args) {
		/*int[] a = {0,0,1,1};
		int[] b = {0,1,0,1};
		Constant nA = new Constant("A", a);
		Constant nB = new Constant("B", b);
		Constant nC = new Constant("A AND B", nA.and(nB));
		Constant nD = new Constant("A IMPL B", nA.implies(nB));
		Constant nE = new Constant("A AND B OR A", nC.or(nA));
		int[] res = nE.implies(nB);
		int[] res2 = nC.or(nD);
		for (int i = 0; i < res.length; i++) {
			System.out.print(res[i]);
		}
		System.out.println();
		for (int i = 0; i < res2.length; i++) {
			System.out.print(res2[i]);
		}
		*/
		//int[] a = generatePattern(4,3);
		/*int[] b = generatePattern(3,1);
		int[] c = generatePattern(3,2);*/
		//for (int i = 0; i < a.length; i++) {
		//	System.out.println(a[i]);
		
		//}
		
	}
}
