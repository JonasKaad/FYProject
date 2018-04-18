package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This is the preliminary implementation of the visualization methods. Current
 * implementations include:
 *
 *   - drawSequence (Basic functionality, with some shortcomings to be implemented later):
 *     Draws the RNA sequence as line. Does currently NOT connect the nodes, and does NOT
 *     draw matches, however this should be a fairly straightforward implementation.
 *
 *   - drawCircleRep (Basic functionality, with minor shortcomings to be improved upon):
 *     Draws the circle representation of the RNA sequence. This draws both connections
 *     between nodes, and draw matches. However, in some cases, the lines of matches over-
 *     lap, which is not intended.
 *
 *
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * 2018-04-18
 */

public class Visualize {

    /** tries to set the monospace font to the system default */
    private static Font monoFont;

    /**
     * Visualize the RNA sequence as a line.
     *
     * @param sequence the RNA string.
     * @param matches  a list of tuples containing the matches found.
     */
    static void drawSequence( GraphicsContext gc, String sequence, List<Tuple> matches ) {
        getSystemInfo();

        double xShift, x1, y1, x2, y2, x3, y3;
        char[] chars = sequence.toCharArray();
        int length = chars.length;

        Tuple[] nodeCoordinates = new Tuple[length + 1];
        Image[] images = loadImageResources( sequence.length() );

        double xOffset = images[0].getWidth() / 2;
        double yOffset = images[0].getWidth() / 2;

        //Canvas canvas = new Canvas( 1500, 500 );
        //GraphicsContext gc = canvas.getGraphicsContext2D();

        // creates shadow effect on canvas
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius( 4.0 );
        dropShadow.setOffsetX( 2.0 );
        dropShadow.setOffsetY( 2.0 );
        dropShadow.setColor( Color.color( 0.4, 0.4, 0.4 ) );
        gc.setFont( monoFont );
        gc.setEffect( dropShadow );

        // scale distance between image resources
        if ( length < 25 ) {
            xShift = 40;
            gc.setLineWidth( 1.3 );
        }
        else if ( length < 50 ) {
            xShift = 30;
            gc.setFont( Font.font( 10 ) );
            gc.setLineWidth( 1.1 );
        }
        else {
            xShift = 15;
            gc.setFont( Font.font( 8 ) );
            gc.setLineWidth( 0.9 );
        }

        // compute coordinates to represent string sequence
        double x = xShift;
        for ( int i = 0; i < length + 1; i++ ) {
            if ( i == 0 ) {
                nodeCoordinates[i] = new Tuple( x, 100 );
            }
            else {
                nodeCoordinates[i] = new Tuple( x, 100 );
                if ( i < length ) {
                    gc.strokeLine( nodeCoordinates[i - 1].getX(), nodeCoordinates[i - 1].getY(),
                            nodeCoordinates[i].getX(), nodeCoordinates[i].getY() );
                }
            }
            x += xShift;
            if ( i == length ) {
                for (Tuple t : matches) {
                    // draw lines between matches
                    x1 = nodeCoordinates[t.getI()].getX();
                    y1 = nodeCoordinates[t.getI()].getY();
                    x2 = nodeCoordinates[t.getJ()].getX();
                    y2 = nodeCoordinates[t.getJ()].getY();
                    x3 = x1 + ( ( x2 - x1 ) / 2 );

                    // TODO adjust scaling of control point for quadratic curve
                    gc.setLineWidth( 1.5 );
                    gc.beginPath();
                    gc.moveTo(x1, y1);
                    gc.quadraticCurveTo( x3, 100 + ( x3 / 4 ), x2, y2 );
                    gc.stroke();
                    gc.closePath();
                }
            }
        }

        // add image resources to canvas
        for ( int i = 0; i < length; i++ ) {
            if( chars[i] == 'A' ) {
                gc.drawImage( images[0], nodeCoordinates[i].getX() - xOffset, 100 - yOffset );
            }
            else if( chars[i] == 'C' ) {
                gc.drawImage( images[1], nodeCoordinates[i].getX() - xOffset, 100 - yOffset );
            }
            else if( chars[i] == 'G' ) {
                gc.drawImage( images[2], nodeCoordinates[i].getX() - xOffset, 100 - yOffset );
            }
            else if( chars[i] == 'U' ) {
                gc.drawImage( images[3], nodeCoordinates[i].getX() - xOffset, 100 - yOffset );
            }
            // write indexes onto canvas
            gc.fillText( String.valueOf( i + 1 ), nodeCoordinates[i].getX() - 5, 90 - yOffset );
        }
        //return canvas;
    }


    /**
     * Visualize the RNA sequence as a circle.
     *
     * @param gc       Canvas GraphicsContext.
     * @param sequence an RNA sequence.
     * @param matches  a list of tuples containing matches found.
     * @param width    width of the Canvas.
     * @param height   height of the Canvas.
     */
    static void drawCircleRep( GraphicsContext gc, String sequence, List<Tuple> matches, double width, double height ) {
        getSystemInfo();

        Image[] images = loadImageResources( sequence.length() );

        double diameter, radius, theta, thetaInDegrees, radiusScalar;
        double p0x, p0y, px, py, qx, qy, q0x, q0y;
        double x1, y1, x2, y2, x3, y3;
        double xOffset = images[0].getWidth() / 2;
        double yOffset = images[0].getWidth() / 2;
        double sequenceInPixels;

        char[] chars = sequence.toCharArray();
        int length = chars.length;

        Tuple[] points = new Tuple[length * 2];
        Tuple[] nodeCoordinates = new Tuple[length * 2];

        sequenceInPixels = ( sequence.length() * 30 ) + images[0].getWidth();

        // Scaling canvas
        //Canvas canvas = new Canvas( sequenceInPixels / 2, sequenceInPixels / 2 );

        // Fixed size
        //Canvas canvas = new Canvas( 1024, 768 );
        //GraphicsContext gc = canvas.getGraphicsContext2D();

        // sets a scaler to adjust the size of the radius based on the input length of the string
        if ( length < 10 ) {
            radiusScalar = 2;
            gc.setLineWidth( 1.5 );
        }
        else if ( length < 25 ) {
            radiusScalar = 1.5;
            gc.setLineWidth( 1.3 );
        }
        else if ( length < 50 ) {
            radiusScalar = 1;
            gc.setLineWidth( 1.1 );
        }
        else {
            radiusScalar = 0.5;
            gc.setLineWidth( 0.9 );
        }

        // compute the diameter of the circle
        diameter = ( sequenceInPixels / Math.PI ) * radiusScalar; // the multiplier at the end is a scaling factor
        radius = diameter / 2;


        // compute the angle theta between two points P0 and P, where P0 is located at ( 0, sin( 3*PI / 2 ) ),
        // and P is a variable point that we wish to calculate the coordinates of, which requires the angle theta.
        theta = Math.toRadians( 360 / length );
        //thetaInDegrees = 360 / length;

        //double scalar = ( length * Math.pow( 2.1, -1 ) ) / 100;

        // compute coordinates of P0. First calc is for scaling canvas, second for fixed canvas
        //p0x = sequenceInPixels / 4;
        //p0y = ( sequenceInPixels / 4 ) + radius;
        //p0x = 512;
        //p0y = 384 + radius;
        p0x = width;
        p0y = height + radius;

        // compute coordinates of Q0. First calc is for scaling canvas, second for fixed canvas
        //q0x = sequenceInPixels / 4;
        //q0y = ( sequenceInPixels / 4 ) + radius + 30;
        //q0x = 512;
        //q0y = 384 + radius + 30;
        q0x = width;
        q0y = height + radius + 30;

        int j = 0;
        for ( int i = 0; i < length * 2; i++ ) {
            // compute coordinates on the circle periphery
            px = p0x - radius * Math.sin( ( theta / 2 ) * i );
            py = p0y - radius * ( 1 - Math.cos( ( theta / 2 ) * i ) );
            points[i] = new Tuple( px, py );

            // a separate array with coordinates for the image resources is kept
            // TODO This can probably be optimized
            if ( i == 0 ) {
                nodeCoordinates[j] = new Tuple( px, py );
                j++;
            }
            if ( i % 2 == 0 && i > 1 && i < ( length * 2 ) ) {
                // a separate array with coordinates for the image resources is kept
                nodeCoordinates[j] = new Tuple(px, py);
                j++;

                // extract points to help draw quadratic curve between image resources
                x1 = points[i - 2].getX();
                y1 = points[i - 2].getY();
                x2 = points[i].getX();
                y2 = points[i].getY();
                x3 = points[i - 1].getX();
                y3 = points[i - 1].getY();

                // draw quadratic curve between image resources
                gc.setLineWidth( 1.5 );
                gc.beginPath();
                gc.moveTo( x1, y1 );
                gc.quadraticCurveTo( x3, y3, x2, y2 );
                gc.stroke();
                gc.closePath();
            }
            if ( i == ( ( length * 2 ) - 1 ) ) {
                // draw lines between matches
                for (Tuple t : matches) {
                    x1 = nodeCoordinates[t.getI()].getX();
                    y1 = nodeCoordinates[t.getI()].getY();
                    x2 = nodeCoordinates[t.getJ()].getX();
                    y2 = nodeCoordinates[t.getJ()].getY();
                    gc.setLineWidth(1.5);
                    gc.beginPath();
                    gc.moveTo(x1, y1);
                    //gc.quadraticCurveTo( 512, 384, x2, y2);
                    gc.quadraticCurveTo( width, height, x2, y2);
                    gc.stroke();
                    gc.closePath();
                }
            }
        }
        for ( int i = 0; i < length; i++ ) {
            // compute coordinates at which indexes can be displayed
            qx = q0x - ( radius + 30 ) * Math.sin( theta * i );
            qy = q0y - ( radius + 30 ) * ( 1 - Math.cos( theta * i ) );

            // creates shadow effect on canvas
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius( 4.0 );
            dropShadow.setOffsetX( 2.0 );
            dropShadow.setOffsetY( 2.0 );
            dropShadow.setColor( Color.color( 0.4, 0.4, 0.4 ) );

            // write indexes onto canvas
            gc.setFont( monoFont );
            gc.setEffect( dropShadow );
            gc.fillText( String.valueOf( i + 1 ), qx - 5, qy + 5 );

            // add image resources to canvas
            if ( chars[i] == 'A' ) {
                gc.drawImage( images[0], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            }
            else if ( chars[i] == 'C' ) {
                gc.drawImage( images[1], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            }
            else if ( chars[i] == 'G' ) {
                gc.drawImage( images[2], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            }
            else if ( chars[i] == 'U' ) {
                gc.drawImage( images[3], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            }
        }
        //return canvas;
    }

    /**
     * Load a number of image resources into a list. The image resources loaded depend
     * on the length of the RNA sequence that is to be represented. If the length of the
     * string is large, then smaller images are loaded.
     *
     * @param length the length in characters of the RNA sequence.
     * @return an Image array, containing the four image resources, adenine, cytosine, guanine
     *         and urasil, indexed in that order. The size of the images returned in the array
     *         depend on the input parameter length.
     */
    private static Image[] loadImageResources( int length ) {
        Image[] images = new Image[4];

        // load in images
        if ( length < 25 ) { // retrieve full size images
            images[0] = new Image( "adenine.png" );
            images[1] = new Image( "cytosine.png" );
            images[2] = new Image( "guanine.png" );
            images[3] = new Image( "urasil.png" );
            return images;
        }
        else if ( length < 50 ) { // retrieve small size images
            images[0] = new Image( "adenine-small.png" );
            images[1] = new Image( "cytosine-small.png" );
            images[2] = new Image( "guanine-small.png" );
            images[3] = new Image( "urasil-small.png" );
            return images;
        } else { // retrieve tiny size images
            images[0] = new Image( "adenine-tiny.png" );
            images[1] = new Image( "cytosine-tiny.png" );
            images[2] = new Image( "guanine-tiny.png" );
            images[3] = new Image( "urasil-tiny.png" );
            return images;
        }
    }

    /**
     * Retrieve information about the operating system, such that configurations
     * can be adapted to the specific system. In this case we just set the monospace
     * font to the system default.
     */
    private static void getSystemInfo() {
        String OS = System.getProperty("os.name");
        if ( OS.contains( "Win" ) ) {
            monoFont = Font.font( "Consolas", 11 );
        } else if ( OS.contains( "Ma" ) ) {
            monoFont = Font.font( "Courier", 11 );
        } else if ( OS.contains( "ix" ) ) {
            monoFont = Font.font( "Ubuntu Monospace", 11 );
        } else {
            monoFont = Font.font( java.awt.Font.MONOSPACED, 11 );
        }
    }

}
