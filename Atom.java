/*
 * Maria Mueller
 * 163309
 * Math 126, Journal 3
 * November 4, 2016
 * 
 * Class creates Atom object, implements Comparable
 */
public class Atom implements Comparable<Atom>{
        public String name;
        
        public Atom(String n) {
            this.name = n;
        }
        @Override
        public String toString() {
            return this.name;
        }
        @Override
        public int compareTo(Atom other) {
            return this.name.compareTo(other.toString());
        }
        public boolean equals(Atom other) {
            return this.toString().equals(other.toString());
        }
    }