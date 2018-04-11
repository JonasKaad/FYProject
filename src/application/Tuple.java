package application;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This is a helper class to create tuples to represent ( i, j ) entries
 * to a matrix.
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * 2018-04-11
 */

class Tuple {

    private int i, j;

    /**
     * Constructor which creates a new Tuple with parameters ( i, j ).
     *
     * @param i an integer. Usually to represent the row number.
     * @param j an integer. Usually to represent the column number.
     */
    Tuple( int i, int j ) {
        this.i = i;
        this.j = j;
    }

    /**
     * Gets the value i of this tuple.
     *
     * @return the value of i of this tuple.
     */
    int getI() {
        return this.i;
    }

    /**
     * Gets the value j of this tuple.
     *
     * @return the value of j of this tuple.
     */
    int getJ() {
        return this.j;
    }

} // end class