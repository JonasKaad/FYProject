package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This class is the initializer for the sequence visualizer application.
 *
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * May 16, 2018
 */

public class Main extends Application {

    private static Stage primaryStage;

    /** Main method */
    public static void main( final String[] args ) { launch(); }

    /** If applicable, run preloader */
    @Override
    public void init() {}

    /** Start the application */
    @Override
    public void start( Stage stage ) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle( "Strucvis" );

        View view = new View();
        Scene scene = new Scene( view, 1024, 768 );
        scene.getStylesheets().add( "style.css" );
        Controller controller = new Controller( view );

        primaryStage.setScene( scene );
        primaryStage.show();
    }

    /**
     * Returns this stage.
     *
     * @return this stage.
     */
    static Stage getPrimaryStage() {
        return primaryStage;
    }

}