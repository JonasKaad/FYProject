package application;

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
 * This class contains the visualization methods currently implemented, which
 * include:
 *
 *   - drawSequence: Draws the RNA sequence as line.
 *
 *   - drawCircleRep: Draws the circle representation of the RNA sequence.
 *
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * April 22, 2018
 */

public class Visualize {

    /**
     * Visualize the RNA sequence as a line.
     *
     * @param sequence the RNA string.
     * @param matches  a list of tuples containing the matches found.
     */
    static void drawSequence( GraphicsContext gc, String sequence, List<Tuple> matches, double width, double height ) {
        // try to retrieve system information, to set a monospace font
        getSystemInfo();

        double xShift, x1, y1, x2, y2, x3, y3;
        char[] chars = sequence.toCharArray();
        int length = chars.length;

        Tuple[] nodeCoordinates = new Tuple[length + 1];
        Image[] images = loadImageResources( sequence.length() );

        double xOffset = images[0].getWidth() / 2;
        double yOffset = images[0].getWidth() / 2;
        double lineWidth;

        // create shadow effect on canvas
        DropShadow dropShadow = createShadowEffect();
        Font monoFont = getSystemInfo();
        gc.setFont( monoFont );
        gc.setEffect( dropShadow );

        // scale distance between image resources
        if ( length < 25 ) {
            xShift = 40;
            lineWidth = 1.3;
            gc.setLineWidth( lineWidth );
        }
        else if ( length < 50 ) {
            xShift = 30;
            gc.setFont( Font.font( 10 ) );
            lineWidth = 1.1;
            gc.setLineWidth( lineWidth );
        }
        else {
            xShift = 15;
            gc.setFont( Font.font( 8 ) );
            lineWidth = 0.9;
            gc.setLineWidth( lineWidth );
        }

        // compute coordinates to represent string sequence
        double x = xShift;
        for ( int i = 0; i < length + 1; i++ ) {
            if ( i == 0 ) {
                nodeCoordinates[i] = new Tuple( x, 40 );
            } else {
                nodeCoordinates[i] = new Tuple( x, 40 );
                if ( i < length ) {
                    gc.strokeLine( nodeCoordinates[i - 1].getX(), nodeCoordinates[i - 1].getY(),
                            nodeCoordinates[i].getX(), nodeCoordinates[i].getY() );
                }
            }
            x += xShift;

            // on last iteration of loop, we compute and draw lines for matches
            if ( i == length ) {
                int z = 0;
                for ( Tuple t : matches ) {
                    // draw lines between matches
                    x1 = nodeCoordinates[t.getI()].getX();
                    y1 = nodeCoordinates[t.getI()].getY();
                    x2 = nodeCoordinates[t.getJ()].getX();
                    y2 = nodeCoordinates[t.getJ()].getY();
                    x3 = x1 + ( ( x2 - x1 ) / 2 );
                    if ( chars[t.getI()] == 'A' || chars[t.getI()] == 'U' ) {
                        gc.setStroke( Color.valueOf( "00aeef" ) );
                        gc.setLineWidth( gc.getLineWidth() + 0.3 );
                    } else if ( chars[t.getI()] == 'G' || chars[t.getI()] == 'C' ) {
                        gc.setStroke( Color.valueOf( "5c5cf9" ) );
                        gc.setLineWidth( gc.getLineWidth() + 1 );
                    }
                    gc.setLineWidth( 1.5 );
                    gc.beginPath();
                    gc.moveTo(x1, y1);
                    gc.quadraticCurveTo( x3, ( 40 + ( length * 10 ) ) - z, x2, y2 );
                    gc.stroke();
                    gc.closePath();
                    gc.setLineWidth( lineWidth );
                    z += 30;
                }
            }
        }
        gc.setStroke( Color.BLACK );
        // when all lines have been drawn, we can add images to represent the nucleotides on top
        for ( int i = 0; i < length; i++ ) {
            // write indexes onto canvas
            gc.fillText( String.valueOf( i + 1 ), nodeCoordinates[i].getX() - 5, 30 - yOffset );

            if( chars[i] == 'A' ) {
                gc.drawImage( images[1], nodeCoordinates[i].getX() - xOffset, 40 - yOffset );
            }
            else if( chars[i] == 'C' ) {
                gc.drawImage( images[2], nodeCoordinates[i].getX() - xOffset, 40 - yOffset );
            }
            else if( chars[i] == 'G' ) {
                gc.drawImage( images[3], nodeCoordinates[i].getX() - xOffset, 40 - yOffset );
            }
            else if( chars[i] == 'U' ) {
                gc.drawImage( images[4], nodeCoordinates[i].getX() - xOffset, 40 - yOffset );
            }
        }
        // draw helper lines at corners, to help identify borders of visualization
        drawCornerHelpers( gc, width, height );
    }


    /**
     * Visualize the RNA sequence as a circle.
     *
     * @param gc            Canvas GraphicsContext.
     * @param inputSequence an RNA sequence.
     * @param matches       a list of tuples containing matches found.
     * @param width         width of the Canvas.
     * @param height        height of the Canvas.
     */
    static void drawCircleRep( GraphicsContext gc, String inputSequence, List<Tuple> matches, double width, double height ) {
        // try to retrieve system information, to set a monospace font
        getSystemInfo();

        String sequence = inputSequence;
        Image[] images = loadImageResources( sequence.length() );

        double diameter, radius, theta, thetaInDegrees, radiusScalar;
        double p0x, p0y, px, py, qx, qy, q0x, q0y;
        double x1, y1, x2, y2, x3, y3;
        double xOffset = images[0].getWidth() / 2;
        double yOffset = images[0].getWidth() / 2;
        double sequenceInPixels, lineWidth;

        char[] chars;
        int length;

        // scale distance between image resources
        // B's added to the sequence represent 'blank' spaces. We do this to create a more
        // uniform separation between the beginning and end of the circle representation
        if ( sequence.length() < 10 ) {
            sequence += "B";
            chars = sequence.toCharArray();
            length = chars.length;
            sequenceInPixels = ( length * 30 ) + images[0].getWidth();
            radiusScalar = 2;
            lineWidth = 1.5;
            gc.setLineWidth( lineWidth );
        }
        else if ( sequence.length() < 25 ) {
            sequence += "B";
            chars = sequence.toCharArray();
            length = chars.length;
            sequenceInPixels = ( length * 30 ) + images[0].getWidth();
            radiusScalar = 1.5;
            lineWidth = 1.3;
            gc.setLineWidth( lineWidth );
            gc.setFont( Font.font( 10 ) );
        }
        else if ( sequence.length() < 50 ) {
            sequence += "BB";
            chars = sequence.toCharArray();
            length = chars.length;
            sequenceInPixels = ( length * 40 ) + images[0].getWidth();
            radiusScalar = 1;
            lineWidth = 1.1;
            gc.setLineWidth( lineWidth );
            gc.setFont( Font.font( 10 ) );
        }
        else {
            sequence += "BBB";
            chars = sequence.toCharArray();
            length = chars.length;
            sequenceInPixels = ( length * 45 ) + images[0].getWidth();
            radiusScalar = 0.6;
            lineWidth = 0.9;
            gc.setLineWidth( lineWidth );
            gc.setFont( Font.font( 8 ) );
        }

        // create shadow effect on canvas
        DropShadow dropShadow = createShadowEffect();
        gc.setEffect( dropShadow );

        Tuple[] points = new Tuple[length * 2];
        Tuple[] nodeCoordinates = new Tuple[length];


        // compute the diameter of the circle
        diameter = ( sequenceInPixels / Math.PI ) * radiusScalar; // the multiplier at the end is a scaling factor
        radius = diameter / 2;

        // compute the angle theta between two points P0 and P, where P0 is located at ( 0, sin( 3*PI / 2 ) ),
        // and P is a variable point that we wish to calculate the coordinates of, which requires the angle theta.
        theta = Math.toRadians( 360d / length );
        thetaInDegrees = 360d / length;

        // compute coordinates of P0
        p0x = width / 2;
        p0y = ( height / 2 ) + radius;

        // compute coordinates of Q0
        q0x = width / 2;
        q0y = ( height / 2 ) + radius + 30;

        int j = 0;
        int k = 0;
        for ( int i = 0; i < length * 2; i++ ) {
            // compute coordinates on the circle periphery
            px = p0x - radius * Math.sin( ( theta / 2 ) * i );
            py = p0y - radius * ( 1 - Math.cos( ( theta / 2 ) * i ) );
            points[i] = new Tuple( px, py );
            if ( i % 2 == 0 ) { k++; }
            // a separate array with coordinates for the image resources is kept
            // TODO This can probably be optimized
            if ( i == 0 ) {
                nodeCoordinates[j] = new Tuple( px, py );
                j++;
            }
            if ( i % 2 == 0 && i > 1 && i < ( length * 2 ) ) {
                // a separate array with coordinates for the image resources is kept
                nodeCoordinates[j] = new Tuple( px, py );
                j++;

                // extract points to help draw quadratic curve between image resources
                x1 = points[i - 2].getX();
                y1 = points[i - 2].getY();
                x2 = points[i].getX();
                y2 = points[i].getY();
                x3 = points[i - 1].getX();
                y3 = points[i - 1].getY();

                if ( chars[k - 1] != 'B' ) {
                    // draw quadratic curve between image resources
                    gc.beginPath();
                    gc.moveTo( x1, y1 );
                    gc.quadraticCurveTo( x3, y3, x2, y2 );
                    gc.stroke();
                    gc.closePath();
                }
            }

            // on last iteration of loop, we compute and draw lines for matches
            if ( i == ( ( length * 2 ) - 1 ) ) {
                for ( Tuple t : matches ) {
                    x1 = nodeCoordinates[t.getI()].getX();
                    y1 = nodeCoordinates[t.getI()].getY();
                    x2 = nodeCoordinates[t.getJ()].getX();
                    y2 = nodeCoordinates[t.getJ()].getY();
                    if ( chars[t.getI()] == 'A' || chars[t.getI()] == 'U' ) {
                        gc.setStroke( Color.valueOf( "00aeef" ) );
                        gc.setLineWidth( gc.getLineWidth() + 0.3 );
                    } else if ( chars[t.getI()] == 'G' || chars[t.getI()] == 'C' ) {
                        gc.setStroke( Color.valueOf( "5c5cf9" ) );
                        gc.setLineWidth( gc.getLineWidth() + 1 );
                    }
                    gc.beginPath();
                    gc.moveTo(x1, y1);
                    gc.quadraticCurveTo( width / 2, height / 2, x2, y2 );
                    gc.stroke();
                    gc.closePath();
                    gc.setLineWidth( lineWidth );
                }
            }
        }
        gc.setStroke( Color.BLACK );
        // when all lines have been drawn, we can add images to represent the nucleotides on top
        for ( int i = 0; i < length; i++ ) {
            // compute coordinates at which indexes can be displayed
            qx = q0x - ( radius + 30 ) * Math.sin( theta * i );
            qy = q0y - ( radius + 30 ) * ( 1 - Math.cos( theta * i ) );

            // write indexes onto canvas
            Font monoFont = getSystemInfo();
            gc.setFont( monoFont );
            if ( chars[i] != 'B' ) {
                gc.fillText( String.valueOf( i + 1 ), qx - 5, qy + 5 );
            }

            // draw image resources onto canvas
            if ( chars[i] == 'B' ) {
                gc.drawImage( images[0], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            } else if ( chars[i] == 'A' ) {
                gc.drawImage( images[1], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            } else if ( chars[i] == 'C' ) {
                gc.drawImage( images[2], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            } else if ( chars[i] == 'G' ) {
                gc.drawImage( images[3], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            } else if ( chars[i] == 'U' ) {
                gc.drawImage( images[4], nodeCoordinates[i].getX() - xOffset, nodeCoordinates[i].getY() - yOffset );
            }
        }
        // draw helper lines at corners, to help identify borders of visualization
        drawCornerHelpers( gc, width, height );
    }


    /**
     * Draw visual aids on canvas to help the user identify the borders of the
     * visualization drawn. The methods draws two lines at a 90 degree angle at
     * each of the corners.
     *
     * @param gc     the GraphicsContext of this canvas.
     * @param width  the width of this canvas.
     * @param height the height of this canvas.
     */
    private static void drawCornerHelpers( GraphicsContext gc, double width, double height ) {
        // corner helpers can be a different color.
        //gc.setStroke( Color.RED );
        gc.setLineWidth( 1 );

        // top left corner
        gc.strokeLine( 2, 2, 12, 2 );
        gc.strokeLine( 2, 2, 2, 12 );

        // top right corner
        gc.strokeLine( width - 12, 2, width - 2, 2 );
        gc.strokeLine( width - 2, 2, width - 2, 12 );

        // bottom left corner
        gc.strokeLine( 2, height - 12, 2, height - 2 );
        gc.strokeLine( 2, height - 2, 12, height - 2 );

        // bottom right corner
        gc.strokeLine( width - 2, height - 12, width - 2, height - 2 );
        gc.strokeLine( width - 12, height - 2, width - 2, height - 2 );
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
        Image[] images = new Image[5];

        // load in images
        if ( length < 25 ) { // retrieve full size images
            images[0] = new Image( "blank.png" );
            images[1] = new Image( "adenine.png" );
            images[2] = new Image( "cytosine.png" );
            images[3] = new Image( "guanine.png" );
            images[4] = new Image( "urasil.png" );
            return images;
        }
        else if ( length < 50 ) { // retrieve small size images
            images[0] = new Image( "blank-small.png" );
            images[1] = new Image( "adenine-small.png" );
            images[2] = new Image( "cytosine-small.png" );
            images[3] = new Image( "guanine-small.png" );
            images[4] = new Image( "urasil-small.png" );
            return images;
        } else { // retrieve tiny size images
            images[0] = new Image( "blank-tiny.png" );
            images[1] = new Image( "adenine-tiny.png" );
            images[2] = new Image( "cytosine-tiny.png" );
            images[3] = new Image( "guanine-tiny.png" );
            images[4] = new Image( "urasil-tiny.png" );
            return images;
        }
    }


    static DropShadow createShadowEffect() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius( 4.0 );
        dropShadow.setOffsetX( 2.0 );
        dropShadow.setOffsetY( 2.0 );
        dropShadow.setColor( Color.color( 0.4, 0.4, 0.4 ) );
        return dropShadow;
    }

    /**
     * Retrieve information about the operating system, such that configurations
     * can be adapted to the specific system. In this case we just set the monospace
     * font to the system default.
     */
    static Font getSystemInfo() {
        String OS = System.getProperty("os.name");
        if ( OS.contains( "Win" ) ) {
            return Font.font( "Consolas", 11 );
        } else if ( OS.contains( "Ma" ) ) {
            return Font.font( "Courier", 11 );
        } else if ( OS.contains( "nix" ) ) {
            return Font.font( "Ubuntu Monospace", 11 );
        } else {
            return Font.font( java.awt.Font.MONOSPACED, 11 );
        }
    }

}
