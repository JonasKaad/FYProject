package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This is a helper class, which extends VBox, to make a movable container
 * that can be clicked and dragged around using the mouse.
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * April 22, 2018
 */

public class MovableContainer extends VBox {

    private double originX, originY;
    private double translateOriginX, translateOriginY;

    public MovableContainer() {
        setOnMousePressed( containerOnMousePressedEventHandler );
        setOnMouseDragged( containerOnMouseDraggedEventHandler );
    }

    EventHandler<MouseEvent> containerOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle( MouseEvent event ) {
            originX = event.getSceneX();
            originY = event.getSceneY();
            translateOriginX = ( ( VBox )event.getSource() ).getTranslateX();
            translateOriginY = ( ( VBox )event.getSource() ).getTranslateY();
        }

    };

    EventHandler<MouseEvent> containerOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            double offsetX = event.getSceneX() - originX;
            double offsetY = event.getSceneY() - originY;
            double newTranslateX = translateOriginX + offsetX;
            double newTranslateY = translateOriginY + offsetY;

            ( ( VBox )event.getSource() ).setTranslateX( newTranslateX );
            ( ( VBox )event.getSource() ).setTranslateY( newTranslateY );
        }

    };

} // end class
