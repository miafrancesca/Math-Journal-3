/*
 * Maria Mueller
 * Math 126, Journal 3
 * November 4, 2016
 * 
 * Creates molecule object, which holds ratio of atoms in it
 */

import java.util.Map;
import java.util.TreeMap;

public class Molecule implements Comparable<Molecule>{
        private Map<Atom, Integer> atoms;
        private String name;
        
        public Molecule(String formula) {
            this.atoms = new TreeMap<Atom, Integer>();
            this.name = formula;
            int start = 0, cursor = 1; String segment;
            while (cursor < formula.length()) {
                if (Character.isUpperCase(formula.charAt(cursor))) {
                    segment = formula.substring(start, cursor);
                    this.insertAtom(segment);
                    start = cursor;
                }
                cursor++;
            }
            if (cursor != start) {
                segment = formula.substring(start, cursor);
                this.insertAtom(segment);
            }       
        }
        @Override
        public String toString() {
            String result = this.atoms.toString();
            return result;
        }
        
        public Map<Atom, Integer> getAtoms() {
            return new TreeMap<Atom, Integer>(this.atoms);
        }
        public String getName() {
            return this.name;
        }
        
        public void insertAtom(String x) {
            int counter = 0; int value = 1;
            while (counter < x.length()) {
                if (Character.isDigit(x.charAt(counter))) {
                    value = Integer.parseInt(x.substring(counter));
                    break;
                }
                counter++;
            }
            String name = x.substring(0,  counter);
            this.atoms.put(new Atom(name), value);
        }
        
        @Override
        public int compareTo(Molecule other){
            return this.getName().compareTo(other.getName());
            
        }
        
        public boolean equals(Molecule other) {
            return this.getName().equals(other.getName());                    
        }
}