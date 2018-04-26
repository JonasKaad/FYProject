package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Marta extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        StackPane stackPane = new StackPane();

        Label background = new Label();
        //background.setGraphic( new ImageView( "RNA_Nucleotides.png" ) ); // TODO <- Insert your background image here :)
        Label label1 = new Label();
        label1.setStyle( "-fx-border-color: black" );
        label1.setFont( Font.font( 16 ) );
        label1.setMinWidth( 150 );
        label1.setMinHeight( 150 );
        label1.setTranslateX( 250 );
        label1.setTranslateY( 110 );

        Label label2 = new Label( "Guanine is a Nucleotide...Guanine is a Nucleotide...Guanine is a Nucleotide...Guanine is a Nucleotide...Guanine is a Nucleotide...Guanine is a Nucleotide..." );
        label2.setWrapText( true );
        label2.setMaxWidth( 100 );
        label2.setStyle( "-fx-background-color: whitesmoke" );
        label2.setVisible( false );
        label2.setTranslateX( 230 );
        label2.setTranslateY( 60 );
        label2.setOnMouseEntered( e -> { label2.setVisible( true ); } );
        label2.setOnMouseExited( e -> { label2.setVisible( false ); } );

        stackPane.getChildren().add( background );
        stackPane.getChildren().add( label1 );
        stackPane.getChildren().add( label2 );

        label1.setOnMouseEntered( e -> { label2.setVisible( true ); } );
        label1.setOnMouseExited( e -> { label2.setVisible( false ); } );

        Scene scene = new Scene( stackPane, 700, 700 );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
}
