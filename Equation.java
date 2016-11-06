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
    private int numElements, numMolecules;
    private List<Atom> elements; 
    
    /*CONSTRUCTOR*/
    /*ASSUMES NO DOUBLES ENTERED AS COEFFICIENTS, ONLY INTEGERS*/
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
        
        this.numMolecules = this.left.size() + this.right.size();
        
        /*this throws an exception if it's not solvable */
        this.elements = makeElementList(); 
        this.numElements = elements.size();
    }
    
    public Equation(String leftSide, String rightSide) {
        this(leftSide + "=" + rightSide);
    }
    
    /*METHODS*/
    public boolean isBalanced() {
        return this.sumLeft.equals(this.sumRight);
    }
    /* current state of constructor throws exception if equation
     * is unsolvable, so this method is not necessary
     */
    public boolean isSolvable() {
        boolean solvable = true;
        for (Atom aLeft: sumLeft.keySet()) {
            if (!sumRight.containsKey(aLeft)) {
                solvable = false; break;
            }
        }
        for (Atom aRight: sumRight.keySet()) {
            if(!sumLeft.containsKey(aRight)) {
                solvable = false; break;
            }
        }
        return solvable;
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
        int[][] system = this.makeSystem();
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
    
    
    /*PRIVATE METHODS*/
    private List<Atom> makeElementList() {
        List<Atom> elements = new ArrayList<Atom>();
        for (Atom aLeft: sumLeft.keySet()) {
            if (!sumRight.containsKey(aLeft)) {
                throw new IllegalArgumentException("invalid equation");
            }
            elements.add(aLeft);
        }
        for (Atom aRight: sumRight.keySet()) {
            if(!sumLeft.containsKey(aRight)) {
                throw new IllegalArgumentException("invalid equation");
            }
            /* do not need to handle adding atoms from right side to elements
             * because they should have already been added on left
             * and if they aren't - then it will have thrown exception
             */
        }
        return elements;
    }
    private int getGCD(List<Integer> nums) {
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
    
    private int getGCD(int x, int y) {
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
    
    /* method returns the index at which the given String switches
     * from a digit to a string, ex: 42Hello returns 2
     */
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
                }
            }
        }
        return sum;
    }
    
    private int[][] makeSystem() {
        int[][] system = new int[numElements][numMolecules+1];
        for (int element = 0; element < system.length; element++) {
            int molecule = 0;
            /*need to fix this terribly redundant code, I KNOW*/
            for (Molecule m: left.keySet()) {
                int coefficient;
                if (m.contains(elements.get(element))) {
                    coefficient = m.get(elements.get(element));
                    
                }
                else {
                    coefficient = 0;
                }
                system[element][molecule] = coefficient;
                molecule++;
            }
            for (Molecule m: right.keySet()) {
                int coefficient;
                if (m.contains(elements.get(element))) {
                    coefficient = -1 * m.get(elements.get(element));
                }
                else {
                    coefficient = 0;
                }
                system[element][molecule] = coefficient;
                molecule++;
            }
            /*is this necessary? is it set to 0 or null by default*/
            if (molecule == system[element].length - 1) {
                system[element][molecule] = 0;
            }
        }
        return system;
    }
    

    
    /*MAIN METHOD*/
    public static void main (String [] args) {
        Scanner r = new Scanner(System.in);
        System.out.print("Enter new equation"
                + "\n\tNEED Capital for separate elements (ex:NaCl)"
                + "\n\texample:'C6H12O6 + O2 = H2O + CO2'\n");
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
