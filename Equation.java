/*
 * Maria Mueller
 * Math 126, Journal 3
 * November 4, 2016
 * 
 * Class defines a chemical equation, using Molecule and Atom objects
 * Can decipher if equation is balanced, and can reduce coefficients in
 * equation.
 * 
 * Does not need to be elements, just any capitalized strings
 */

import java.util.*;

public class Equation {
    /*INSTANCE VARIABLES*/
    private Map<Molecule, Integer> left, right;
    private Map<Atom, Integer> sumLeft, sumRight;
    private int numElements;
    
    /*CONSTRUCTOR*/
    public Equation(String wholeEquation) {
        int cursor = 0; boolean hasEquals = false;
        while (cursor < wholeEquation.length()) {
            if (wholeEquation.charAt(cursor) == '=') {
                hasEquals = true; break;
            }
            cursor++;
        }
        if (!hasEquals) {
            throw new IllegalArgumentException();
        }
        String leftSide = wholeEquation.substring(0,  cursor);
        String rightSide = wholeEquation.substring(cursor +1);
        this.left = mapFromString(leftSide);
        this.right = mapFromString(rightSide);
        this.sumLeft = sumAtoms(this.left);
        this.sumRight = sumAtoms(this.right);
    }
    public Equation(String leftSide, String rightSide) {
        this(leftSide + "=" + rightSide);
    }
    
    /*METHODS*/
    public boolean isBalanced() {
        return this.sumLeft.equals(this.sumRight);
    }
    
    public int numElements() {
        return this.numElements;
    }
    @Override
    public String toString() {
        return this.left + " = " + this.right;
    }
     
    /*SOLVE METHOD*/
    public void solve() {
        /**unfinished**/
        /*needs system of equations, of unspecificed size*/
        this.simplify();
    }
    
    /*checks for if coefficients have a greatest common factor larger than 1*/
    public void simplify() {
        List<Integer> coefficients = new ArrayList<>();
        for (Molecule m: this.left.keySet()) {
            coefficients.add(this.left.get(m));
        }
        for (Molecule m: this.right.keySet()) {
            coefficients.add(this.right.get(m));
        }
        int gcd = getGCD(coefficients);
        if (gcd < 1) {
            for (Molecule m: this.left.keySet()) {
                int newVal = this.left.get(m) / gcd;
                this.left.remove(m);
                this.left.put(m, newVal);
            }
            for (Molecule m: this.right.keySet()) {
                int newVal = this.right.get(m) / gcd;
                this.right.remove(m);
                this.right.put(m,  newVal);
            }
        }
    }
    
    public static int getGCD(List<Integer> nums) {
        if (nums.size() < 1) {
            throw new IllegalArgumentException("param list is empty");
        }
        int lowest = nums.get(0); int temp;
        for (int i = 0; i+1<nums.size(); i++) {
            temp = getGCD(nums.get(i), nums.get(i+1));         
            if (temp == 1) {
                return 1;
            }
            lowest = getGCD(temp, lowest);
        }
        return lowest;
    }
    
    public static int getGCD(int x, int y) {
        int a = x;
        int b = y;
        int temp;
        if (b > a) {
             temp = a;
             a = b;
             b = temp;
        }
        while (b !=0) {
            temp = a%b;
            a = b;
            b = temp;
        }
        return a;
    }
    /*PRIVATE METHODS*/
    private Map<Molecule, Integer> mapFromString(String x) {
        Map<Molecule, Integer> molecules = new TreeMap<Molecule, Integer>();
        x = x.trim();
        int start = 0, cursor = 0;
        String symbol;
        while (start < x.length() && cursor < x.length()) {
            if (x.charAt(cursor) == '+') {
                symbol = x.substring(start, cursor).trim();
                int change = digitToString(symbol);
                int coef = 1;
                if (change > 0) {
                    coef = Integer.parseInt(symbol.substring(0, change));
                    symbol = symbol.substring(change, symbol.length());
                }
                molecules.put(new Molecule(symbol), coef);
                start = cursor + 1;
            }
            cursor ++;
        }
        if (start != cursor) {
            symbol = x.substring(start, cursor).trim();
            int change = digitToString(symbol);
            int coef = 1;
            if (change > 0) {
                coef = Integer.parseInt(symbol.substring(0, change));
                symbol = symbol.substring(change);
            }
            molecules.put(new Molecule(symbol), coef);
        }
        return molecules;
    }
    
    private int digitToString(String x) {
        int cursor = -1;
        while (cursor < x.length()) {
            cursor++;
            if (!Character.isDigit(x.charAt(cursor))) {
                return cursor;
            }
        }
        return cursor;
    }
    
    /**ALSO INSTANTIATES INSTANCE VARIABLE NUMELEMENTS*/
    private Map<Atom, Integer> sumAtoms(Map<Molecule, Integer> map) {
        Map<Atom, Integer> sum = new TreeMap<Atom, Integer>();
        //map has molecules (and a number of each) and has 
        int numMolecules, numAtoms;
        this.numElements = 0;
        for (Molecule m: map.keySet()) {
            numMolecules = map.get(m);
            Map<Atom, Integer> atoms = m.getAtoms();
            for (Atom a: atoms.keySet()) {
                numAtoms = atoms.get(a) * numMolecules;
                if (sum.containsKey(a)) {
                    int existing = sum.get(a);
                    sum.put(a, existing + numAtoms);
                }
                else {
                    sum.put(a, numAtoms);
                    numElements++;
                }
            }
        }
        return sum;
    }
    /*MAIN METHOD*/
    public static void main (String [] args) {
        Scanner r = new Scanner(System.in);
        System.out.print("Enter new equation\n"
                + "\tNEED Capital for separate elements (ex:NaCl)"
                + "\nexample:'C6H12O6 + O2 = H2O + CO2'\n");
        String response = r.nextLine();
        Equation userEqu = new Equation(response);
        
        if (!userEqu.isBalanced()) {
            System.out.print("equation is not balanced");
        }
        else {
            System.out.print("equation is balanced");
        }
        System.out.println(" and has " + userEqu.numElements()
                + " elements");
        System.out.println("final equation: " + userEqu.toString());
    }
}
