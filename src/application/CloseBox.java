package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This is a part of the initial outline for the application that is to be
 * designed in the first year project at IMADA 2018.
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * April 21, 2018
 */

public class CloseBox {

    private static boolean answer;

    /**
     * Creates a small popup window, displaying a confirmation
     * of termination message to the user. This window provides two
     * buttons, a yes and a no button, and depending on which but-
     * ton the user presses a boolean value of true or false re-
     * spectively is returned to the caller.
     *
     * @return boolean value of user input: true if yes, false if no.
     */
    public static boolean display() {
        Stage stage = new Stage();

        stage.initModality( Modality.APPLICATION_MODAL );
        stage.setTitle( "Confirm Exit" );
        stage.setMinWidth( 280 );
        stage.setMinHeight( 120 );

        Label label = new Label();
        label.setText( "Are you sure you want to exit the program?" );
        Button yesButton = new Button( "Yes" );
        Button noButton = new Button( "No" );
        noButton.setCancelButton( true );
        yesButton.setMinSize( 75, 25 );
        noButton.setMinSize( 75, 25 );

        yesButton.setOnKeyPressed( event -> {
            if ( event.getCode().equals( KeyCode.ENTER ) ) {
                answer = true;
                stage.close();
            }
        } );
        yesButton.setOnAction( event -> {
            answer = true;
            stage.close();
        } );

        noButton.setOnAction( event -> {
            answer = false;
            stage.close();
        } );

        VBox layout = new VBox( 20 );
        HBox buttons = new HBox( 20 );
        buttons.getChildren().addAll( yesButton, noButton );
        layout.getChildren().addAll( label, buttons );
        layout.setAlignment( Pos.CENTER );
        buttons.setAlignment( Pos.CENTER );

        Scene scene = new Scene( layout );
        stage.setScene( scene );
        stage.setResizable( false );
        stage.showAndWait();

        return answer;
    }

} // end class