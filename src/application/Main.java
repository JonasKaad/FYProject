package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
 * 2018-04-11
 */

public class Main extends Application {

    private static Stage mainStage;
    private static int tabsCreated = 0;
    private TabPane mainTabPane;
    private ToolBar left, right;

    /** Main method */
    public static void main( String[] args ) { launch( args ); }

    /**
     * Initiates the main window of the application
     */
    @Override
    public void start( Stage stage ) {
        // for FXML - might be useful later in development
        //Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));

        // this can be added to any node to show its border. Helpful to see the area a node covers
        //node.setStyle( "-fx-border-color: black;" );

        mainStage = stage;
        mainStage.setTitle( "StructVis" ); // sets the title of this window

        // makes the mainStage open maximized (i.e. fullscreen)
        //mainStage.setMaximized( true );

        // instantiates the right ToolBar
        // this ToolBar interacts with a CheckMenuItem in the viewMenu
        // which allows the ability to hide or show this ToolBar.
        // No other functionality is bound to or by this object at this time.
        right = createRightToolbar();

        // panes are the contents of scenes
        BorderPane mainLayout = new BorderPane();

        mainLayout.setCenter( createCenterPane() );
        mainLayout.setTop( createTopMenu() );
        mainLayout.setBottom( createBottomMenu() );
        mainLayout.setLeft( createLeftToolbar() );
        mainLayout.setRight( right );

        // scenes are the contents of stages
        Scene mainScene = new Scene( mainLayout, 1024, 768 );

        mainStage.setScene( mainScene );

        // handle close operation
        mainStage.setOnCloseRequest( e -> {
            e.consume();
            closeProgram();
        } );

        mainStage.show();
    }

    /**
     * Method to handle proper termination of the application.
     *
     * However we choose to design the user interface, this
     * particular functionality will be important in the event
     * that we want to process some information before termina-
     * ting the program. This could for example be saving a file,
     * some settings, etc.
     */
    private void closeProgram() {
        boolean answer = CloseBox.display();
        if( answer ) { mainStage.close(); }
    }

    /**
     * Method that creates a vertical ToolBar. This ToolBar is almost iden-
     * tical to the leftToolBar, except that it is even simpler, and populated
     * only with a Label, a Separator and a Text.
     *
     * @return
     */
    private ToolBar createRightToolbar() {
        ToolBar rightToolBar = new ToolBar();
        rightToolBar.setOrientation( Orientation.VERTICAL );
        rightToolBar.setMinWidth( 200 );
        rightToolBar.setMaxWidth( 200 );

        Label label = new Label( "This is just a label" );
        Text text = new Text( "This toolbar is essentially identical to the left toolbar. Maybe it would be useful to have a toolbar here, maybe not." );
        text.setWrappingWidth( 200 );

        rightToolBar.getItems().addAll( label, new Separator(), text );

        return rightToolBar;
    }

    /**
     * Method that creates a vertical ToolBar. The ToolBar is populated with
     * a few CheckBoxes and some text, just to give a sense of what can be
     * achieved with the user interface. Currently none of items displayed by
     * this method have any functionality at all.
     *
     * @return a ToolBar
     */
    private ToolBar createLeftToolbar() {
        // TODO Provides no functionality whatsoever and is therefore entirely
        // TODO for show at this point in development
        ToolBar leftToolBar = new ToolBar();
        leftToolBar.setOrientation( Orientation.VERTICAL );
        leftToolBar.setMinWidth( 200 );
        leftToolBar.setMaxWidth( 200 );

        Label label = new Label( "This is just a label" );
        Label label2 = new Label( "Toggle Representation" );
        Text text = new Text( "Marta suggested that configuration options could go somewhere over here. Ideas for the various visual representations can be found in the accordian below" );
        text.setWrappingWidth( 200 );

        Accordion accordion = new Accordion();

        TitledPane titledPane = new TitledPane();
        titledPane.setText( "Visual Representation" );
        titledPane.setTextAlignment( TextAlignment.CENTER );

        CheckBox matrixCheckBox = new CheckBox();
        matrixCheckBox.setText( "Matrix (Default)" );
        matrixCheckBox.setSelected( true );

        CheckBox dotBracketCheckBox = new CheckBox();
        dotBracketCheckBox.setText( "Dot-Bracket" );
        dotBracketCheckBox.setSelected( true );

        CheckBox convensiontalCheckBox = new CheckBox();
        convensiontalCheckBox.setText( "Conventional" );

        CheckBox circleCheckBox = new CheckBox();
        circleCheckBox.setText( "Circle Plot" );

        CheckBox dotPlotCheckBox = new CheckBox();
        dotPlotCheckBox.setText( "Dot Plot" );

        VBox vBox = new VBox();
        vBox.setPadding( new Insets( 5 ) );
        vBox.getChildren().addAll(
                label2,
                matrixCheckBox,
                dotBracketCheckBox,
                convensiontalCheckBox,
                circleCheckBox,
                dotPlotCheckBox
        );

        titledPane.setContent( vBox );

        accordion.getPanes().add( titledPane );
        accordion.setExpandedPane( titledPane );

        leftToolBar.getItems().addAll(
                label,
                new Separator(),
                text,
                new Separator(),
                accordion
        );

        return leftToolBar;
    }

    /**
     * Creates a small bar at the bottom of the UI.
     *
     * @return a VBox with some contents
     */
    private VBox createBottomMenu() {
        // TODO As of yet, this doesn't really have any purpose other than to show that
        // TODO we can display some information at the bottom of the application window
        HBox hBox = new HBox( 10 );
        hBox.setPadding( new Insets( 3, 5, 3, 5 ) );
        hBox.setAlignment( Pos.CENTER );

        Label bottomInfoLabel = new Label();
        bottomInfoLabel.setAlignment( Pos.CENTER );
        bottomInfoLabel.setText( "This is just an information bar" );

        hBox.getChildren().add(bottomInfoLabel);

        VBox bottomMenu = new VBox();
        bottomMenu.getChildren().addAll( new Separator(), hBox );

        return bottomMenu;
    }

    /**
     * Creates the center of the UI as a new Tab.
     *
     * @return a TabPane with a 'Welcome' tab.
     */
    private TabPane createCenterPane() {
        mainTabPane = new TabPane();

        Tab tab = new Tab();
        tab.setText( "Welcome" );

        HBox hBox = new HBox();
        hBox.setPadding( new Insets( 10, 10, 10, 10 ) );
        hBox.setAlignment( Pos.TOP_LEFT );

        Text text = welcome();

        hBox.getChildren().add( text );
        tab.setContent( hBox );
        mainTabPane.getTabs().add( tab );

        return mainTabPane;
    }

    /**
     * Assembles the top menu for the program.
     *
     * @return a VBox containing the various UI elements represented in the top menu
     */
    private VBox createTopMenu() {
        MenuBar menuBar = createMenuBar();

        ToolBar inputBar = new ToolBar();
        inputBar.setPadding( new Insets( 5, 10, 5, 10 ) );

        TextField input = new TextField();
        HBox.setHgrow( input, Priority.ALWAYS );
        HBox.setMargin( input, new Insets( 0,5,0,0 ) );
        input.setPromptText( "Enter RNA string" );

        Button button = new Button( "Calculate" );
        button.setMinWidth( 80 );
        button.setOnAction( e -> {
            String str = input.getText();
            int i = str.length();
            createMatrix( i, str );
        } );

        inputBar.getItems().addAll( input, button );

        VBox topMenu = new VBox();
        topMenu.getChildren().addAll( menuBar, inputBar );

        return topMenu;
    }

    /**
     * Creates the menu bar for the top menu.
     *
     * @return a menu bar
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // configures a background - this can then be added to a node
        //Background background = new Background( new BackgroundFill( Paint.valueOf( "#2E2E2E" ), null, null ) );
        // haven't found a way to alter textColor of menuBar yet

        // adds background to menuBar
        //menuBar.setBackground( background );

        // creates various panes for the menuBar
        Menu fileMenu = new Menu( "File" );
        Menu editMenu = new Menu( "Edit" );
        Menu viewMenu = new Menu( "View" );
        Menu helpMenu = new Menu( "Help" );

        // populates the various menu panes of the menuBar
        for ( MenuItem item : fileMenuItems() ) {
            fileMenu.getItems().add( item );
        }
        for ( MenuItem item : viewMenuItems() ) {
            viewMenu.getItems().add( item );
        }
        for ( MenuItem item : helpMenuItems() ) {
            helpMenu.getItems().add( item );
        }

        menuBar.getMenus().addAll( fileMenu, editMenu, viewMenu, helpMenu );

        return menuBar;
    }

    /**
     * Creates a list of items to populate the fileMenu
     *
     * @return a List of MenuItems
     */
    private List<MenuItem> fileMenuItems() {
        List<MenuItem> items = new ArrayList<>();

        // creates the contents, i.e. items in a menu
        MenuItem open = new MenuItem("Open");
        MenuItem separator = new SeparatorMenuItem();
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction( e -> closeProgram() );

        items.add( open );
        items.add( separator );
        items.add( exit );

        return items;
    }

    /**
     * Creates a list of items to populate the helpMenu
     *
     * @return
     */
    private List<MenuItem> helpMenuItems() {
        List<MenuItem> items = new ArrayList<>();

        // creates the contents, i.e. items in a menu
        MenuItem about = new MenuItem("About");
        about.setOnAction( e -> AboutBox.display() );

        items.add( about );

        return items;
    }

    private List<MenuItem> viewMenuItems() {
        List<MenuItem> items = new ArrayList<>();

        // creates the contents, i.e. items in a menu
        CheckMenuItem showRightToolbar = new CheckMenuItem("Show Right Toolbar");
        showRightToolbar.setSelected( true );

        // configures the toggle action of the menuItem
        showRightToolbar.setOnAction( e -> {
            if ( showRightToolbar.isSelected() ) {
                right.setVisible( true );
            } else {
                right.setVisible( false );
            }
        } );
        items.add( showRightToolbar );

        return items;
    }

    /**
     * Places the result computed by the createMatrix method into a new Tab.
     *
     * @param borderPane
     */
    private void resultToTab( BorderPane borderPane ) {
        tabsCreated++;

        Tab tab = new Tab();
        tab.setText( "New " + tabsCreated );
        tab.setContent( borderPane );
        mainTabPane.getTabs().add( tab );
        mainTabPane.getSelectionModel().select( tab );
    }

    /**
     * Creates an instance of the Nussinov algorithm, and computes the
     * matrix representation of the ACGU input sequence. The matrix re-
     * presentation is then inserted into a GridPane, such that it can
     * be visually presented in the UI.
     *
     * The method ends with a call to the resultToTab method, which puts
     * the final visual representation into a tab, which the resultToTab
     * method then adds to the UI.
     *
     * @param size     the length of the ACGU sequence
     * @param sequence the ACGU input sequence
     */
    private void createMatrix( int size, String sequence ) {
        // create a new instance of the Nussinov class, to run the algorithm
        Nussinov nussinov = new Nussinov();
        int[][] matrix = nussinov.initiate( sequence );

        // store the string sequence in a char array
        char[] charArray = sequence.toCharArray();

        GridPane root = new GridPane();
        root.setAlignment( Pos.CENTER );

        // double loop which creates a matrix representation
        for ( int y = 0; y < size + 1; y++ ) {
            for ( int x = 0; x < size + 1; x++ ) {

                // create a new Label in each iteration
                Label label = new Label();
                label.setPrefHeight( 20 );
                label.setPrefWidth( 20 );
                label.setAlignment( Pos.CENTER );
                if( y == 0 && x == 0 ) {
                    label.setText( "" );
                    // if we want to color to top left corner (cell). Otherwise it is empty / blank
                    //Background background = new Background( new BackgroundFill( Paint.valueOf( "#2E2E2E" ), null, null ) );
                    //l.setBackground( background );
                }
                else if( y == 0 ) {
                    label.setText( String.valueOf( charArray[x - 1] ).toUpperCase() );
                }
                else if( x == 0 ) {
                    label.setText( String.valueOf( charArray[y - 1] ).toUpperCase() );
                } else {
                    label.setText( String.valueOf( matrix[y - 1][x - 1] ) );
                }

                // iterate the index using the loops
                root.setRowIndex( label, y );
                root.setColumnIndex( label, x );
                //labelGrid.add( label );
                root.getChildren().add( label );
            }
        }
        // shows the matrix representation in a grid
        // this could perhaps be a toggle-able option?
        //root.setGridLinesVisible( true );

        // retrieve the matches the Nussinov algorithm found as a List of Tuples
        List<Tuple> matches = nussinov.getMatches();

        // retrieve a Dot-Bracket notation output computed by the Nussinov algorithm
        String dotBracket = nussinov.getDotBracketOutput();

        // pass the above to the listMatches method, to organize the results found
        VBox listedResults = listMatches( sequence, dotBracket, matches );

        // this is to put the result in a ScrollPane, however:
        // TODO There are currently some problems with horizontal expansion of the ScrollPane
        // TODO When the contents in the ScrollPane grow beyond its size, the pane expands only
        // TODO vertically, but not horizontally.
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth( true );
        scrollPane.setFitToHeight( true );
        //scrollPane.setPannable( true ); // <- enables panning, by clicking and holding mouse button

        scrollPane.setContent( root );

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter( scrollPane );
        borderPane.setBottom( listedResults );

        resultToTab( borderPane );
    }

    /**
     * Builds a VBox based on the results found by a call to the createMatrix method
     * The VBox consists of 3 HBoxes, with the first one displaying the matches found,
     * the second displaying the input string or letter sequence and the third display-
     * ing the dot-bracket representation of the sequence.
     *
     * @param inputSequence    the ACGU sequence
     * @param dotBracketOutput the ACGU sequence translated to dot-bracket notation
     * @param matches          a List of Tuples, with each Tuple containing an i, j
     *                         index to a match in the matrix computed by the Nussi-
     *                         nov algorithm.
     * @return a VBox organizing some results computed by the createMatrix method.
     */
    private VBox listMatches( String inputSequence, String dotBracketOutput, List<Tuple> matches ) {
        // we build a string of matches from the tuples found
        StringBuilder stringBuilder = new StringBuilder();
        for ( Tuple t : matches ) {
            stringBuilder.append( " (" ).append( t.getI() ).append( "," ).append( t.getJ() ).append( ") " );
        }

        Label headerLabel = new Label( "Matches Found" );
        headerLabel.setMinWidth( 140 );
        Label stringLabel = new Label( "Input Sequence" );
        stringLabel.setMinWidth( 140 );
        Label notationLabel = new Label( "Dot-Bracket Notation" );
        notationLabel.setMinWidth( 140 );
        Label matchesFoundLabel = new Label( stringBuilder.toString() );

        Label sequenceLabel = new Label( inputSequence );
        sequenceLabel.setFont( new Font( "Consolas", 12 ) );
        HBox.setHgrow( sequenceLabel, Priority.ALWAYS );

        Label dotBracketLabel = new Label( dotBracketOutput );
        HBox.setHgrow( dotBracketLabel, Priority.ALWAYS );
        dotBracketLabel.setFont( new Font( "Consolas", 12 ) );

        VBox vBox = new VBox();
        vBox.setPadding( new Insets( 5 ) );
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        hBox1.getChildren().addAll( headerLabel, matchesFoundLabel );
        hBox2.getChildren().addAll( stringLabel, sequenceLabel );
        hBox3.getChildren().addAll( notationLabel, dotBracketLabel );

        vBox.getChildren().addAll(
                hBox1,
                new Separator(),
                hBox2,
                hBox3
        );

        return vBox;
    }

    /**
     * Loads a textfile 'welcome.txt' which is read and then converted to a
     * String using a StringBuilder. The final String is then put in a Text
     * object, which is returned.
     *
     * @return a Text object
     */
    private Text welcome() {
        return new Text( LoadTextFile.load( "welcome.txt" ) );
    }

} // class