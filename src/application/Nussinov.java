package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * An implementation of the Nussinov algorithm, based on the work done by
 * R. Nussinov, G. Pieczenik, D. J. Kleitman (1978) - Algorithms for Loop
 * Matching. SIAM J Appl.
 *
 * This particular implementation is based on pseudo-code from slides by
 * Oliver Kohlbacher (2014), Eberhard Karls Universität Tubringen,
 * slides for BIOINF 4120 - Bioinformatics 2 - Structures and Systems
 *
 * The following sequence from above-mentioned slides can be used to test
 * the algorithm, and should yield results (1,7) and (2,6). Beware however
 * that the slides represent the results as (2,8) and (3,7), which is the
 * exact same result, the only difference being that the slides use an index
 * starting from 1, where this implementation uses an index starting from 0.
 *
 * Test Sequence:  GGGAAACCU
 *
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * 2018-04-11
 */

public class Nussinov {

    /** Dynamically programmed matrix */
    private int[][] DPmatrix;

    /** char arrays to represent input and outputArray sequences */
    private char[] inputArray, outputArray;

    /** List of tuples to hold the entries to DPmatrix of the matches found */
    private List<Tuple> matches = new ArrayList<>();


    /**
     * Starts the Nussinov algorithm.
     *
     * This method computes the DPmatrix from a provided sequence of
     * characters using a dynamic programming approach. Once the entries
     * of the matrix has been computed, a traceback procedure is initiated
     * to find any matches in the sequence.
     *
     * @param s a sequence to be computed
     * @return a result matrix
     */
    int[][] initiate( String s ) {
        DPmatrix = new int[s.length()][s.length()];
        inputArray = s.toCharArray();
        outputArray = new char[inputArray.length];
        Arrays.fill(outputArray, '.' );
        int i = 0;
        int j = 0;
        int l;
        for ( l = 1; l < DPmatrix.length; l++ ) {
            for ( j = l; j < DPmatrix.length; j++ ) {
                i = j - l;
                DPmatrix[i][j] = compute( i, j );
            }
        }
        traceback( i, j - 1 );
        return DPmatrix;
    }

    /**
     * Computational part of Nussinov algorithm.
     *
     * @param i a value i into the DPmatrix.
     * @param j a value j into the DPmatrix.
     * @return the max value computed.
     */
    private int compute( int i, int j ) {
        int a = DPmatrix[i + 1][j];
        int b = DPmatrix[i][j - 1];
        int c = DPmatrix[i + 1][j - 1] + sigma( i, j );
        int k = i + 1;
        int d = 0;
        if( k > 1 && k < j ) {
            d = DPmatrix[i][k] + DPmatrix[k + 1][j];
        }
        int r1 = Math.max( a, b );
        int r2 = Math.max( c, d );
        return Math.max( r1, r2 );
    }

    /**
     * Sigma part of Nussinov algorithm.
     *
     * @param i a value i into the DPmatrix.
     * @param j a value j into the DPmatrix.
     * @return
     */
    private int sigma( int i, int j ) {
        char a = inputArray[i];
        char b = inputArray[j];
        if( ( a == 'C' && b == 'G' ) ||
                ( a == 'G' && b == 'C' ) ||
                ( a == 'A' && b == 'U' ) ||
                ( a == 'U' && b == 'A' ) ||
                ( a == 'G' && b == 'U' ) ||
                ( a == 'U' && b == 'G' ) ) {
            return 1;
        }
        // Outline for a way of adding weigth to scores
        /*if( ( a == 'C' && b == 'G' ) ||
            ( a == 'G' && b == 'C' ) ) {
                return 3;
        }
        else if ( ( a == 'A' && b == 'U' ) ||
                ( a == 'U' && b == 'A' ) ) {
            return 2;
        }
        else if ( ( a == 'G' && b == 'U' ) ||
                ( a == 'U' && b == 'G' ) ) {
            return 1;
        }*/
        return 0;
    }

    /**
     * Traceback part of Nussinov algorithm.
     *
     * @param i a value i into the DPmatrix.
     * @param j a value j into the DPmatrix.
     */
    private void traceback( int i, int j ) {
        if( i < j ) {
            // case 1
            if( DPmatrix[i][j] == DPmatrix[i + 1][j] ) {
                traceback( i + 1, j );
            }

            // case 2
            else if( DPmatrix[i][j] == DPmatrix[i][j - 1] ) {
                traceback( i, j - 1 );
            }

            // case 3
            else if( DPmatrix[i][j] == ( DPmatrix[i + 1][j - 1] + sigma( i, j ) ) ) {
                checkMatch( i, j );
                traceback( i + 1, j - 1 );
            }

            // case 4
            else {
                for ( int k = ( i + 1 ); k <= ( j - 1 ); k++ ) {
                    if( DPmatrix[i][j] == ( DPmatrix[i][k] + DPmatrix[k + 1][j] ) ) {
                        traceback( i, k );
                        traceback( k + 1, j );
                        break;
                    }
                }
            }
        }
    }

    /**
     * Method that uses sigma method to check for letter matches.
     * In addition, a check has been included, to ensure that a match found
     * by the Nussinov algorithm is only added to the list of matches, if
     * the spacing between letters is greater than 3.
     *
     * @param i a value i into the DPmatrix.
     * @param j a value j into the DPmatrix.
     */
    private void checkMatch( int i, int j ) {
        /*if( sigma( i, j ) == 1 ) {
            matches.add( new Tuple( i, j ) );
        }*/
        if( ( sigma( i, j ) > 0 ) && ( j - i ) > 3 ) {
            matches.add( new Tuple( i, j ) );
        }
    }

    /**
     * Retrives and returns the matches found by the Nussinov algorithm.
     * The matches found are returned as a list of tuples, representing
     * entries to the DPmatrix.
     *
     * @return a List of Tuples representing the matches found.
     */
    List<Tuple> getMatches() { return matches; }

    /**
     * Translates the result of the DPmatrix to Dot-Bracket notation and
     * stores the result in a char array.
     */
    private void createDotBracket() {
        for ( Tuple t : matches ) {
            outputArray[t.getI()] = '(';
            outputArray[t.getJ()] = ')';
        }
    }

    /**
     * Retrieves and returns a Dot-Bracket notation of the current sequence.
     *
     * @return a Dot-Bracket notation of the sequence.
     */
    String getDotBracketOutput() {
        createDotBracket();
        return String.valueOf( outputArray );
    }

    /**
     * Helped method for printing the contents of the matrix to the System
     * output.
     *
     * @return a String representation of the matrix
     */
    private String printMatrix() {
        String res = "";
        for ( int i = 0; i < DPmatrix.length; i++ ) {
            for ( int j = 0; j < DPmatrix.length; j++ ) {
                res += DPmatrix[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }

} // end class