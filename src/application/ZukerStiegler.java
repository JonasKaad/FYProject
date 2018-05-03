package application;

import java.util.ArrayList;
import java.util.List;

public class ZukerStiegler {

    /**
     * Dynamically programmed matrix for the minimum folding free energy of all
     * non-empty foldings of a subsequence si, ..., sj for all i < j.
     */
    private double[][] W;

    /**
     * Dynamically programmed matrix for the minimum folding free energy of all
     * non-empty foldings of a subsequence si, ..., sj containing a base pair (i, j).
     */
    private double[][] V;


    private double[] hairpinLoopEnergies = new double[]{0, 0, 0, 4.1, 4.9, 4.4, 4.7, 5.0, 5.1, 5.2, 5.3, 5.4, 5.5, 5.6, 5.7, 5.8, 5.8, 5.9, 5.9, 6.0, 6.1, 6.1, 6.2, 6.2, 6.3, 6.3, 6.3, 6.4, 6.4, 6.5, 6.5};
    private double[] bulgeLoopEnergies = new double[]{0, 3.9, 3.1, 3.5, 4.2, 4.8, 5.0, 5.2, 5.3, 5.4, 5.5, 5.7, 5.7, 5.8, 5.9, 6.0, 6.1, 6.1, 6.2, 6.2, 6.3, 6.3, 6.4, 6.4, 6.5, 6.5, 6.5, 6.6, 6.7, 6.7, 6.7};
    private double[] interiorLoopEnergies = new double[]{0, 0, 4.1, 5.1, 4.9, 5.3, 5.7, 5.9, 6.0, 6.1, 6.3, 6.4, 6.4, 6.5, 6.6, 6.7, 6.8, 6.8, 6.9, 6.9, 7.0, 7.1, 7.1, 7.1, 7.2, 7.2, 7.3, 7.3, 7.4, 7.4, 7.4};


    private char[] inputArray;

    /** List of tuples to hold the matches found */
    private List<Tuple> matches = new ArrayList<>();


    double[][] initiate( String s ) {
        W = new double[s.length()][s.length()];
        V = new double[s.length()][s.length()];
        inputArray = s.toCharArray();

        // initialize the matrices
        for ( int i = 0; i < W.length; i++ ) {
            for ( int j = 0; j < W.length; j++ ) {
                if ( ( j - 4 ) < i && i < j ) {
                    W[i][j] = Integer.MAX_VALUE;
                    V[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        //System.out.println( printMatrix() );

        for ( int i = 0; i < W.length; i++ ) {
            for ( int j = 0; j < W.length; j++ ) {
                if ( i < j ) {
                    W[i][j] = compute( i, j );
                    System.out.println( "W[" + i + "][" + j + "] = " + W[i][j] );
                }
            }
        }

        return W;
    }

    double compute( int i, int j ) {
        double a = W[i + 1][j];
        double b = W[i][j - 1];
        double c = computeEnergies( i, j );
        int k = i + 1;
        double d = Integer.MAX_VALUE;
        double e = Integer.MAX_VALUE;
        double f = Integer.MAX_VALUE;
        if( k > j && j > i ) {
            d = W[i][k] + W[k + 1][j];
        }
        for ( int l = k; l < W.length; l++ ) {
            if( k > j && j > i ) {
                e = W[i][k] + W[k + 1][j];
            }
            f = Math.min( d, e );
        }
        return Math.min( Math.min( a, b ), Math.min( c, f ) );
    }


    double computeEnergies( int i, int j ) {

        //System.out.println( "(" + i + "," + j + ")" );

        // compute energy for hairpin loop
        double energyHairpinLoop;
        if ( j < 30 ) {
            energyHairpinLoop = hairpinLoopEnergies[(j - i) - 1];
        } else {
            energyHairpinLoop = hairpinLoopEnergies[29];
        }

        // compute energy for stacked pairs
        double energyStackedPair = stackedPairEnergies( i, j ) + V[i + 1][j - 1];



        // bulge loop if k - i > 1 or j - l > 1, but not both,
        // interior loop if k - i > 1 and j - l > 1
        // i < k < l < j
        // k - i + j - l > 2
        //int k = i + 1;

        // compute energy for bulge loop / interior loop
        int k = i + 1;
        int l = j - 1;
        double energyBulgeLoop = Integer.MAX_VALUE;
        double energyInteriorLoop = Integer.MAX_VALUE;
        double energyBI = Integer.MAX_VALUE;
        double energyMultiLoop = Integer.MAX_VALUE;

        for ( int m = k; m < l; m++ ) {
            for ( int n = l; n > 0; n-- ) {
                if ( ( k - i ) > 1 ^ ( j - l ) > 1 ) { // bulge
                    energyBulgeLoop = bulgeLoopEnergies[k - i] + V[k][l];
                }
                if ( ( k - i ) > 1 && ( j - l ) > 1 ) { // interior loop
                    energyInteriorLoop = interiorLoopEnergies[(k - i) + (j - l)] + V[k][l];
                }
                energyBI = Math.min( energyBulgeLoop, energyInteriorLoop );
                V[i][j] = energyBI;
            }
        }

        // compute energy for multi loop
        int pairedBases = 1;
        int unpairedBases = 1;
        k = i + 1;
        if ( i < k && k < ( j - 1 ) ) {
            double temp1 = W[i + 1][k] + W[k + 1][j - 1] + ( 4.6 + ( 0.4 * unpairedBases ) + ( 0.1 * pairedBases ) );
            //for ( int m = k; m < ( j - 1 ); m++ ) {
                // TODO find a way to count number of paired and unpaired bases
            //}
            double temp2;
            for ( int m = k; m < ( j - 1 ); m++ ) {
                temp2 = W[i + 1][k] + W[k + 1][j - 1] + ( 4.6 + ( 0.4 * unpairedBases ) + ( 0.1 * pairedBases ) );
                energyMultiLoop = Math.min( temp1, temp2 );
                V[i][j] = energyMultiLoop;
            }
        }

        V[i][j] = Math.min( Math.min( energyHairpinLoop, energyStackedPair ), Math.min( energyBI, energyMultiLoop ) );
        return V[i][j];
    }


    double stackedPairEnergies( int i, int j ) {
        double[][] energiesStackedPair = new double[][]{
               // A/U,  C/G,  G/C,  U/A,  G/U,  U/G
                {-0.9, -1,8, -2.3, -1.1, -1.1, -0.8},  // A/U
                {-1.7, -2,9, -3.4, -2.3, -2.1, -1.4},  // C/G
                {-2.1, -2,0, -2.9, -1.8, -1.9, -1.2},  // G/C
                {-0.9, -1,7, -2.1, -0.9, -1.0, -0.5},  // U/A
                {-0.5, -1,2, -1.4, -0.8, -0.4, -0.2},  // G/U
                {-1.0, -1,9, -2.1, -1.1, -1.5, -0.4},  // U/G
        };

        if ( inputArray[i] == 'A' && inputArray[j] == 'U' ) {
            if ( inputArray[i + 1] == 'A' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[0][0];
            } else if ( inputArray[i + 1] == 'C' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[0][1];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'C' ) {
                return energiesStackedPair[0][2];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'A' ) {
                return energiesStackedPair[0][3];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[0][4];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[0][1];
            }
        } else if ( inputArray[i] == 'C' && inputArray[j] == 'G' ) {
            if ( inputArray[i + 1] == 'A' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[1][0];
            } else if ( inputArray[i + 1] == 'C' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[1][1];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'C' ) {
                return energiesStackedPair[1][2];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'A' ) {
                return energiesStackedPair[1][3];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[1][4];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[1][1];
            }
        } else if ( inputArray[i] == 'G' && inputArray[j] == 'C' ) {
            if ( inputArray[i + 1] == 'A' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[2][0];
            } else if ( inputArray[i + 1] == 'C' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[2][1];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'C' ) {
                return energiesStackedPair[2][2];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'A' ) {
                return energiesStackedPair[2][3];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[2][4];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[2][1];
            }
        } else if ( inputArray[i] == 'U' && inputArray[j] == 'A' ) {
            if ( inputArray[i + 1] == 'A' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[3][0];
            } else if ( inputArray[i + 1] == 'C' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[3][1];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'C' ) {
                return energiesStackedPair[3][2];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'A' ) {
                return energiesStackedPair[3][3];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[3][4];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[3][1];
            }
        } else if ( inputArray[i] == 'G' && inputArray[j] == 'U' ) {
            if ( inputArray[i + 1] == 'A' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[4][0];
            } else if ( inputArray[i + 1] == 'C' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[4][1];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'C' ) {
                return energiesStackedPair[4][2];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'A' ) {
                return energiesStackedPair[4][3];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[4][4];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[4][1];
            }
        } else if ( inputArray[i] == 'U' && inputArray[j] == 'G' ) {
            if ( inputArray[i + 1] == 'A' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[5][0];
            } else if ( inputArray[i + 1] == 'C' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[5][1];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'C' ) {
                return energiesStackedPair[5][2];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'A' ) {
                return energiesStackedPair[5][3];
            } else if ( inputArray[i + 1] == 'G' && inputArray[j - 1] == 'U' ) {
                return energiesStackedPair[5][4];
            } else if ( inputArray[i + 1] == 'U' && inputArray[j - 1] == 'G' ) {
                return energiesStackedPair[5][1];
            }
        }
        return Integer.MAX_VALUE;
    }




    public static void main( String[] args ) {

        ZukerStiegler zk = new ZukerStiegler();

        // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21
        // A A A C A U G A G  G  A  U  U  A  C  C  C  A  U  G  U
        String s = "AAACAUGAGGAUUACCCAUGU";

        zk.initiate( s );

        System.out.println( zk.printMatrix() );

        //System.out.println( Arrays.deepToString( zk.energyStackedPair ) );

    }


    /**
     * Helped method for printing the contents of the matrix to the System
     * output.
     *
     * @return a String representation of the matrix
     */
    private String printMatrix() {
        StringBuilder stringBuilder = new StringBuilder();
        String res = "";
        for ( int i = 0; i < W.length; i++ ) {
            for ( int j = 0; j < W.length; j++ ) {
                if ( W[i][j] >= 0 ) {
                    stringBuilder.append( " " ).append( W[i][j] ).append( " " );
                    //res += " ";
                    //res += W[i][j] + " ";
                } else {
                    //res += W[i][j] + " ";
                    stringBuilder.append( W[i][j] ).append( " " );
                }
            }
            stringBuilder.append( "\n" );
            //res += "\n";
        }
        return stringBuilder.toString();
        //return res;
    }

}
