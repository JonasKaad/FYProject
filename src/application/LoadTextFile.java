package application;

import java.io.*;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This is the initial outline for the application that is to be designed in the
 * first year project at IMADA 2018.
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * 2018-04-11
 */

public class LoadTextFile {

    /**
     * Method to load and read the contents of a text file. The method reads the
     * entire text file and stores the contents in a String, which is then returned
     * to the caller.
     *
     * @param string the name of the file to be loaded, including its file extension
     *               ( i.e. .txt for example )
     * @return a String containing the contents of the loaded txt file
     */
    static String load( String string ) {
        File file = new File( string );

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        // try-with-resources to auto-close stream
        try (InputStream inputStream = new FileInputStream( file );
             BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) ) ) {
            while ( ( line = bufferedReader.readLine() ) != null ) {
                stringBuilder.append( line ).append( "\n" );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

} // end class