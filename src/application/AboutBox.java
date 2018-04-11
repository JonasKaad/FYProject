package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
 * 2018-04-11
 */

public class AboutBox {

    /**
     * Creates a small popup window, displaying information on the
     * to the user.
     */
    public static void display() {
        Stage stage = new Stage();

        stage.initModality( Modality.APPLICATION_MODAL );
        stage.setTitle( "About StructVis" );
        stage.setMinWidth( 250 );
        stage.setMinHeight( 380 );

        Label headerLabel = new Label( "Version:" );
        headerLabel.setAlignment( Pos.CENTER );
        Label versionLabel = new Label( "Whatever comes before pre-alpha" );
        versionLabel.setAlignment( Pos.CENTER );

        Button okButton = new Button( "OK" );
        okButton.setMinSize( 75, 25 );

        okButton.setOnAction( e -> stage.close() );

        VBox layout = new VBox( 10 );
        layout.setPadding( new Insets( 5 ) );

        HBox hBox = new HBox();
        hBox.setPadding( new Insets( 5 ) );
        hBox.getChildren().add( okButton );
        hBox.setAlignment( Pos.CENTER );

        Text text = new Text();
        text.setText( LoadTextFile.load( "help.txt" ) );
        text.setWrappingWidth( 250 );

        layout.setAlignment( Pos.CENTER );
        layout.getChildren().addAll( text, headerLabel, versionLabel, hBox );

        Scene scene = new Scene( layout );
        stage.setScene( scene );
        stage.setResizable( false );
        stage.showAndWait();
    }

} // end class