package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
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
 * April 22, 2018
 */

public class AboutBox {

    /**
     * Creates a small popup window, displaying information on the
     * to the user.
     */
    public static void display() {
        Stage stage = new Stage();

        stage.initModality( Modality.APPLICATION_MODAL );
        stage.setTitle( "About VSeq" );
        stage.setMaxWidth( 250 );
        stage.setMinHeight( 300 );

        Label versionHeaderLabel = new Label( "Version:" );
        versionHeaderLabel.setAlignment( Pos.CENTER );
        Label versionLabel = new Label( "0.3" );
        versionLabel.setAlignment( Pos.CENTER );

        Button okButton = new Button( "OK" );
        okButton.setMinSize( 75, 25 );

        okButton.setCancelButton( true );
        okButton.setOnAction( event -> stage.close() );
        okButton.setOnKeyPressed( event -> {
            if ( event.getCode().equals( KeyCode.ENTER ) ) {
                stage.close();
            }
        } );

        VBox layout = new VBox( 10 );
        layout.setPadding( new Insets( 5 ) );

        VBox vBox = new VBox( 10 );
        vBox.setAlignment( Pos.CENTER );

        Label authorHeaderLabel = new Label( "Authors" );
        authorHeaderLabel.setPadding( new Insets( 10, 5, 5, 5 ) );
        authorHeaderLabel.setAlignment( Pos.CENTER );

        vBox.getChildren().add( authorHeaderLabel );

        GridPane g = new GridPane();
        g.setPadding( new Insets( 0,0,20,0 ) );
        Label firstAuthorLabel = new Label( "Dennis Andersen" );
        firstAuthorLabel.setPadding( new Insets( 0,0,5,5 ) );
        Label firstAuthorLabelExtra = new Label( "deand17" );
        firstAuthorLabelExtra.setPadding( new Insets( 0,0,5,5 ) );
        Label secondAuthorLabel = new Label( "Marta Masse Gyldenkerne" );
        secondAuthorLabel.setPadding( new Insets( 0,0,5,5 ) );
        Label secondAuthorLabelExtra = new Label( "magyl17" );
        secondAuthorLabelExtra.setPadding( new Insets( 0,0,5,5 ) );
        Label thirdAuthorLabel = new Label( "Arulmolibarman Muthukrishnan" );
        thirdAuthorLabel.setPadding( new Insets( 0,0,5,5 ) );
        Label thirdAuthorLabelExtra = new Label( "armut13" );
        thirdAuthorLabelExtra.setPadding( new Insets( 0,0,5,5 ) );

        g.add( firstAuthorLabel, 0, 0 );
        g.add( firstAuthorLabelExtra, 1, 0 );
        g.add( secondAuthorLabel, 0, 1 );
        g.add( secondAuthorLabelExtra, 1, 1 );
        g.add( thirdAuthorLabel, 0, 2 );
        g.add( thirdAuthorLabelExtra, 1, 2 );

        vBox.getChildren().add( g );

        //Text text = new Text();
        //text.setText( LoadTextFile.load( "help.txt" ) );
        //text.setWrappingWidth( 250 );

        HBox hBox = new HBox();
        hBox.setPadding( new Insets( 5 ) );
        hBox.getChildren().add( okButton );
        hBox.setAlignment( Pos.CENTER );

        layout.setAlignment( Pos.CENTER );
        layout.getChildren().addAll( vBox, versionHeaderLabel, versionLabel, hBox );

        Scene scene = new Scene( layout );
        stage.setScene( scene );
        stage.setResizable( false );
        stage.showAndWait();
    }

} // end class