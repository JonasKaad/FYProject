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
 * Matching. SIAM J Appl Math 35, 68-81.
 *
 * This particular implementation is based on pseudo-code from slides by
 * Oliver Kohlbacher (2014), Eberhard Karls Universität Tubringen,
 * slides for BIOINF 4120 - Bioinformatics 2 - Structures and Systems.
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
 * May 16, 2018
 */

public class Nussinov {

    /** The RNA sequence. */
    final private String THE_SEQUENCE;

    /** Determines which variant of the Nussinov algorithm to use. */
    final private int MODE;

    /** Dynamically programmed matrix. */
    private int[][] DPmatrix;

    /** char arrays to represent input and outputArray sequences. */
    private char[] inputArray;

    /** ArrayList of tuples to hold the entries to DPmatrix of the secondaryStructure found. */
    private ArrayList<Tuple> secondaryStructure = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param sequence a String representing the RNA sequence.
     * @param mode     an integer to identify which variant of the algorithm should be used.
     */
    public Nussinov( String sequence, int mode ) {
        this.THE_SEQUENCE = sequence;
        this.MODE = mode;
        inputArray = sequence.toCharArray();
    }

    /**
     * Starts the Nussinov algorithm.
     *
     * Calls the compute method to fill the DPmatrix from the provided sequence
     * of characters.
     *
     * @return a dynamically programmed matrix.
     */
    public int[][] initiate() {
        return compute( THE_SEQUENCE );
    }

    /**
     * Computes a DPmatrix using a dynamic programming approach. Once the entries
     * of the matrix has been computed, a traceback procedure is initiated
     * to find any secondary structure of the sequence.
     *
     * @param sequence an RNA sequence
     * @return a dynamically programmed matrix.
     */
    private int[][] compute( String sequence ) {
        DPmatrix = new int[sequence.length()][sequence.length()];

        int i = 0;
        int j = 0;
        int l, case1, case2, case3;
        for ( l = 4; l < DPmatrix.length; l++ ) {
            for ( j = l; j < DPmatrix.length; j++ ) {
                i = j - l;
                case1 = DPmatrix[i + 1][j];
                case2 = DPmatrix[i][j - 1];

                if ( MODE == 0 ) { // if Basic Nussinov is used, number of matches is maximized
                    case3 = DPmatrix[i + 1][j - 1] + delta( i, j );

                    DPmatrix[i][j] = Math.max( Math.max( case1, case2 ), case3 );

                    for ( int k = i + 1; k < j; k++ ) {
                        if ( DPmatrix[i][k] + DPmatrix[k+1][j] > DPmatrix[i][j] ) {
                            DPmatrix[i][j] = DPmatrix[i][k] + DPmatrix[k+1][j];
                        }
                    }
                } else { // if Energy Nussinov is used, free energy is minimized
                    case3 = DPmatrix[i + 1][j - 1] + energy( i, j );

                    DPmatrix[i][j] = Math.min( Math.min( case1, case2 ), case3 );

                    for ( int k = i + 1; k < j; k++ ) {
                        if ( DPmatrix[i][k] + DPmatrix[k+1][j] < DPmatrix[i][j] ) {
                            DPmatrix[i][j] = DPmatrix[i][k] + DPmatrix[k+1][j];
                        }
                    }
                }
            }
        }
        traceback( secondaryStructure, DPmatrix, i, j - 1 );
        return DPmatrix;
    }

    /**
     * Delta part of Nussinov algorithm.
     *
     * @param i a value i into the DPmatrix.
     * @param j a value j into the DPmatrix.
     * @return 1 if base pairs are a match and 0 otherwise.
     */
    private int delta( int i, int j ) {
        char a = inputArray[i];
        char b = inputArray[j];
        if( ( a == 'C' && b == 'G' ) ||
            ( a == 'G' && b == 'C' ) ||
            ( a == 'A' && b == 'U' ) ||
            ( a == 'U' && b == 'A' ) ) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Energy function for the Nussinov algorithm.
     *
     * @param i a value i into the DPmatrix.
     * @param j a value j into the DPmatrix.
     * @return the lowest energy value of any of the base pairs G-C,
     *         A-U or U-G, or 0 otherwise.
     */
    private int energy( int i, int j ) {
        char a = inputArray[i];
        char b = inputArray[j];
        if( ( a == 'C' && b == 'G' ) ||
            ( a == 'G' && b == 'C' ) ) {
            return -12;
        }
        else if ( ( a == 'A' && b == 'U' ) ||
                  ( a == 'U' && b == 'A' ) ) {
            return -8;
        }
        else if ( ( a == 'G' && b == 'U' ) ||
                  ( a == 'U' && b == 'G' ) ) {
            return -4;
        } else {
            return 0;
        }
    }


    /**
     * Traceback part of Nussinov algorithm.
     *
     * @param i a value i into the DPmatrix.
     * @param j a value j into the DPmatrix.
     */
    private void traceback( ArrayList<Tuple> matches, int[][] DPmatrix, int i, int j ) {
        if( i < j ) {
            // case 1
            if( DPmatrix[i][j] == DPmatrix[i + 1][j] ) {
                traceback( matches, DPmatrix , i + 1, j );
            }

            // case 2
            else if ( DPmatrix[i][j] == DPmatrix[i][j - 1] ) {
                traceback( matches, DPmatrix, i, j - 1 );
            }

            // case 3
            else if ( DPmatrix[i][j] == ( DPmatrix[i + 1][j - 1] + delta( i, j ) ) ||
                      DPmatrix[i][j] == ( DPmatrix[i + 1][j - 1] + energy( i, j ) ) ) {
                matches.add( new Tuple( i, j ) );
                traceback(matches, DPmatrix, i + 1, j - 1);
            }

            // case 4
            else {
                for ( int k = i + 1; k <= j - 1; k++ ) {
                    if( DPmatrix[i][j] == ( DPmatrix[i][k] + DPmatrix[k + 1][j] ) ) {
                        traceback( matches, DPmatrix, i, k );
                        traceback( matches, DPmatrix,k + 1, j );
                        break;
                    }
                }
            }
        }
    }


    /**
     * Retrives and returns the secondaryStructure found by the Nussinov algorithm.
     * The secondaryStructure found are returned as a list of tuples, representing
     * entries to the DPmatrix.
     *
     * @return a List of Tuples representing the secondaryStructure found.
     */
    List<Tuple> getSecondaryStructure() { return secondaryStructure; }


    /**
     * Translates the result of the DPmatrix to Dot-Bracket notation and
     * stores the result in a char array.
     *
     * @return the Dot-Bracket notation in a char array.
     */
    private char[] createDotBracket() {
        char[] outputArray = new char[inputArray.length];
        Arrays.fill( outputArray, '.' );
        for ( Tuple t : secondaryStructure) {
            outputArray[t.getI()] = '(';
            outputArray[t.getJ()] = ')';
        }
        return outputArray;
    }


    /**
     * Retrieves and returns a Dot-Bracket notation of the current sequence.
     *
     * @return a Dot-Bracket notation of the sequence.
     */
    String getDotBracketOutput() {
        return String.valueOf( createDotBracket() );
    }


    /**
     * Helper method for printing the contents of the matrix to the System output.
     *
     * @return a String representation of the matrix.
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