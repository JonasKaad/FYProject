package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This class expands upon the functionality of the Canvas node, to allow
 * the user to have some interaction with the Canvas. This class has been
 * adapted from code referred to by the following source:
 *
 * Source: https://stackoverflow.com/questions/29506156/javafx-8-zooming-relative-to-mouse-pointer/29530135
 * Accessed April 21, 2018
 *
 * Dennis Andersen              -- deand17
 * Marta Massa Gyldenkerne      -- magyl17
 * Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * 2018-04-21
 */

public class InteractiveCanvas extends Canvas {

    private DoubleProperty canvasScale = new SimpleDoubleProperty( 1.0 );
    private double originX, originY;
    private double translateOriginX, translateOriginY;


    public InteractiveCanvas( double width, double height ) {
        this.setWidth( width );
        this.setHeight( height );
        scaleXProperty().bind( canvasScale );
        scaleYProperty().bind( canvasScale );
        setOnMousePressed( canvasOnMousePressedEventHandler );
        setOnMouseDragged( canvasOnMouseDraggedEventHandler );
        setOnScroll( canvasOnScrollEventHandler );
    }

    public void setPivot( double x, double y ) {
        setTranslateX( getTranslateX() - x );
        setTranslateY( getTranslateY() - y );
    }


    EventHandler<MouseEvent> canvasOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle( MouseEvent event ) {
            originX = event.getSceneX();
            originY = event.getSceneY();
            translateOriginX = ( ( Canvas )event.getSource() ).getTranslateX();
            translateOriginY = ( ( Canvas )event.getSource() ).getTranslateY();
        }

    };


    EventHandler<MouseEvent> canvasOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle( MouseEvent event ) {
            double offsetX = event.getSceneX() - originX;
            double offsetY = event.getSceneY() - originY;
            double newTranslateX = translateOriginX + offsetX;
            double newTranslateY = translateOriginY + offsetY;

            ( ( Canvas )event.getSource() ).setTranslateX( newTranslateX );
            ( ( Canvas )event.getSource() ).setTranslateY( newTranslateY );
        }

    };


    EventHandler<ScrollEvent> canvasOnScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle( ScrollEvent event ) {

            final double MAX_SCALE = 10.0d;
            final double MIN_SCALE = .1d;

            double delta = 1.2;

            double scale = canvasScale.get(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if ( event.getDeltaY() < 0 ) {
                scale /= delta;
            } else {
                scale *= delta;
            }

            scale = clamp( scale, MIN_SCALE, MAX_SCALE );

            double f = ( scale / oldScale ) - 1;

            double dx = ( event.getSceneX() - ( getBoundsInParent().getWidth() / 2 + getBoundsInParent().getMinX() ) );
            double dy = ( event.getSceneY() - ( getBoundsInParent().getHeight() / 2 + getBoundsInParent().getMinY() ) );

            canvasScale.set( scale );

            // note: pivot value must be untransformed, i.e. without scaling
            setPivot(f*dx, f*dy );

            event.consume();
        }
    };


    static double clamp( double value, double min, double max ) {

        if( Double.compare( value, min ) < 0)
            return min;

        if( Double.compare( value, max ) > 0)
            return max;

        return value;
    }

}
