package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
 * 2018-04-16
 */

public class VisualizationAttempt extends Application {

    /** Main method */
    public static void main( String[] args ) { launch( args ); }

    /**
     * Initiates the window
     */
    @Override
    public void start( Stage stage ) throws Exception {

        Stage mainStage = stage;
        mainStage.setTitle( "StructVis" ); // sets the title of this window

        HBox mainLayout = new HBox();
        mainLayout.setAlignment( Pos.CENTER );

        Scene mainScene = new Scene( mainLayout, 800, 800 );
        Canvas canvas = new Canvas( 800, 800 );

        mainLayout.getChildren().add( canvas );
        GraphicsContext gc = canvas.getGraphicsContext2D();
        /*
        double diameter, radius, theta, p0x, p0y, px, py;
        String sequence = "GGGAAACCUG";
        String vsequence = sequence;
        char[] chars = vsequence.toCharArray();
        int length = chars.length;
        double sequenceInPixels = ( vsequence.length() * 30 ) + 24;
        diameter = ( sequenceInPixels / Math.PI ); // the multiplier at the end is a scaling factor
        radius = diameter / 2;
        gc.setLineWidth( 1.5 );
        gc.strokeArc( 400 - radius, 400 - radius, diameter, diameter, 0, 45, ArcType.OPEN );
        */
        drawCircleRep( gc );

        //drawLargeSequence( chars, gc );
        //drawSmallSequence( chars, gc );

        mainStage.setScene( mainScene );

        mainStage.show();
    }

    void drawLargeSequence( char[] chars, GraphicsContext gc ) {
        Image adenine = new Image( "adenine.png" );
        Image cytosine = new Image( "cytosine.png" );
        Image guanine = new Image( "guanine.png" );
        Image urasil = new Image( "urasil.png" );

        Font mono = Font.font( java.awt.Font.MONOSPACED, 13 );
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        gc.setFont( mono );
        gc.setEffect( dropShadow );

        int index = 0;
        int indexCountXshift = 0;
        int nucleotideXshift = 42;

        for ( char ch : chars ) {
            gc.fillText( String.valueOf( index + 1 ), 22 + indexCountXshift, 285 );
            if( ch == 'A' ) {
                gc.drawImage( adenine, 10 + ( index * nucleotideXshift ), 300 );
            } else if( ch == 'C' ) {
                gc.drawImage( cytosine, 10 + ( index * nucleotideXshift ), 300 );
            } else if( ch == 'G' ) {
                gc.drawImage( guanine, 10 + ( index * nucleotideXshift ), 300 );
            } else if( ch == 'U' ) {
                gc.drawImage( urasil, 10 + ( index * nucleotideXshift ), 300 );
            }
            index++;
            if( index == 8 ) {
                indexCountXshift += 44;
            } else if( index == 9 ) {
                indexCountXshift += 38;
            } else {
                indexCountXshift += 42;
            }
        }
    }

    void drawSmallSequence( char[] chars, GraphicsContext gc ) {

        Image adenine = new Image( "adenine-small.png" );
        Image cytosine = new Image( "cytosine-small.png" );
        Image guanine = new Image( "guanine-small.png" );
        Image urasil = new Image( "urasil-small.png" );

        Font mono = Font.font( java.awt.Font.MONOSPACED, 11 );
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        gc.setFont( mono );
        gc.setEffect( dropShadow );

        int index = 0;
        int indexCountXshift = 0;
        int nucleotideXshift = 30;

        for ( char ch : chars ) {
            gc.fillText( String.valueOf( index + 1 ), 18 + indexCountXshift, 285 );
            if( ch == 'A' ) {
                gc.drawImage( adenine, 10 + ( index * nucleotideXshift ), 300 );
            } else if( ch == 'C' ) {
                gc.drawImage( cytosine, 10 + ( index * nucleotideXshift ), 300 );
            } else if( ch == 'G' ) {
                gc.drawImage( guanine, 10 + ( index * nucleotideXshift ), 300 );
            } else if( ch == 'U' ) {
                gc.drawImage( urasil, 10 + ( index * nucleotideXshift ), 300 );
            }
            index++;
            if( index == 8 ) {
                indexCountXshift += 31;
            } else if( index == 9 ) {
                indexCountXshift += 26;
            } else {
                indexCountXshift += 30;
            }
        }
    }

    /*
    The following method is part of the first draft for the circle representation of RNA sequences.
     */
    void drawCircleRep( GraphicsContext gc ) {
        double diameter, radius, theta, p0x, p0y, px, py;

        // load in images
        //Image adenine = new Image( "adenine.png" );
        //Image cytosine = new Image( "cytosine.png" );
        //Image guanine = new Image( "guanine.png" );
        //Image urasil = new Image( "urasil.png" );
        Image blank = new Image( "blank-small.png" );
        Image adenine = new Image( "adenine-small.png" );
        Image cytosine = new Image( "cytosine-small.png" );
        Image guanine = new Image( "guanine-small.png" );
        Image urasil = new Image( "urasil-small.png" );


        String sequence = "GGGAAACCUGGGAAACCUGGGAAACCUGGGAAACCU";
        //String vsequence = sequence;
        char[] chars = sequence.toCharArray();
        int length = chars.length;
        double sequenceInPixels = ( sequence.length() * 30 ) + adenine.getWidth();

        // compute the diameter of the circle
        diameter = ( sequenceInPixels / Math.PI ); // the multiplier at the end is a scaling factor
        radius = diameter / 2;


        // compute the angle theta between two points P0 and P, where P0 is located at ( 0, sin( 3*PI / 2 ) ),
        // and P is a variable point that we wish to calculate the coordinates of, which requires the angle theta.
        theta = Math.toRadians( 360 / length );
        // TODO Either a better way of calculating the angle theta is needed or we need to find a better way
        // TODO of figuring out from where and how we can start drawing the circle, as for some inputs, the
        // TODO images get spaced weirdly.
        double thetaToDegrees = 360 / length;

        gc.setLineWidth( 1.5 );
        gc.strokeArc( 400 - radius, 400 - radius, diameter, diameter, 270 + ( thetaToDegrees * 1.5 ), 360 - ( thetaToDegrees * 1.5 ), ArcType.OPEN );

        // compute coordinates of P0
        p0x = 400 - ( adenine.getWidth() / 2 );
        p0y = 400 + radius - ( adenine.getWidth() / 2 );

        for ( int i = 0; i < length; i++ ) {
            px = p0x - radius * Math.sin( theta * i );
            py = p0y - radius * ( 1 - Math.cos( theta * i ) );
            if ( chars[i] == 'B' ) {
                gc.drawImage( blank, px, py );
            }
            else if ( chars[i] == 'A' ) {
                gc.drawImage( adenine, px, py );
            }
            else if ( chars[i] == 'C' ) {
                gc.drawImage( cytosine, px, py );
            }
            else if ( chars[i] == 'G' ) {
                gc.drawImage( guanine, px, py );
            }
            else if ( chars[i] == 'U' ) {
                gc.drawImage( urasil, px, py );
            }
        }
    }

}
