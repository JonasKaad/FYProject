package application;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This is a helper class to create tuples to represent tuples of information.
 * For example entries ( i, j ) into a matrix or coordinates ( x, y ).
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * April 21, 2018
 */

class Tuple {

    private int i, j;
    private double x, y;

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

    Tuple( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the value i of this tuple.
     *
     * @return the value of i of this tuple.
     */
    int getI() { return this.i; }

    /**
     * Gets the value j of this tuple.
     *
     * @return the value of j of this tuple.
     */
    int getJ() { return this.j; }

    /**
     * Gets the value x of this tuple.
     *
     * @return the value of x of this tuple.
     */
    double getX() { return this.x; }

    /**
     * Gets the value y of this tuple.
     *
     * @return the value of y of this tuple.
     */
    double getY() { return this.y; }

} // end class