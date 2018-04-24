package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This class sets up the view for the sequence visualizer application.
 *
 *
 * @author Dennis Andersen              -- deand17
 * @author Marta Massa Gyldenkerne      -- magyl17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * April 24, 2018
 */

public class View extends BorderPane {

    // Containers
    TabPane mainTabPane;

    // MenuBar, Menus and MenuItems
    MenuBar menuBar;
    Menu fileMenu, viewMenu, helpMenu;
    MenuItem open, exit, about;
    CheckMenuItem showTabHeader, showTabInfoBox;

    // ToolBars
    ToolBar inputToolBar, checkBoxToolBar;

    // Labels
    Label bottomInfoLabel;

    // Fields
    TextField inputSequence;

    // Buttons
    Button processButton;

    // Checkboxes
    CheckBox circleCheckBox, sequenceCheckBox, matrixCheckBox,
             dotBracketCheckBox;

    /**
     * Contructor to initiate the construction of the view.
     */
    public View() {
        createLayout();
    }

    /**
     * Initialize the layout of the application
     */
    private void createLayout() {
        createMenuBar();
        this.setCenter( createCenterPane());
        this.setTop( createTopMenu() );
        this.setBottom( createBottomMenu() );
    }

    /**
     * Creates the center of the UI as a new Tab.
     *
     * @return a TabPane with a 'Welcome' tab.
     */
    private TabPane createCenterPane() {
        mainTabPane = new TabPane();
        mainTabPane.setCache( true );

        Tab tab = new Tab();
        tab.setText( "Welcome" );

        ScrollPane root = new ScrollPane();
        root.setFitToWidth( true );

        VBox mainContainer = new VBox();
        mainContainer.setAlignment( Pos.TOP_CENTER );

        VBox headerContainer = new VBox();
        headerContainer.setPadding( new Insets( 10, 10, 10, 10 ) );
        headerContainer.setMaxWidth( 800 );

        Label header = new Label( "Project 78 - Sequence Structure Prediction using Java." );
        header.setPadding( new Insets( 10, 0, 5, 50 ) );
        header.setFont( Font.font( "", FontWeight.BLACK, 22 ) );

        Label subHeader = new Label( "By Dennis Andersen, Marta Massa Gyldenkerne and Arulmolibarman Muthukrishnan." );
        subHeader.setPadding( new Insets( 10, 0, 5, 50 ) );
        subHeader.setFont( Font.font( "", FontWeight.BOLD, 16 ) );

        Line line = new Line( 122, 100, 902, 100 );
        line.setStroke( Color.valueOf( "2f4f4f" ) );
        line.setStrokeWidth( 3 );

        headerContainer.getChildren().addAll(
                header,
                line,
                subHeader
        );

        VBox contentContainer = new VBox();
        contentContainer.setMaxWidth( 980 );
        contentContainer.setPadding( new Insets( 10, 0, 10, 0 ) );
        contentContainer.setAlignment( Pos.TOP_CENTER );

        Text welcomeText = new Text( LoadTextFile.load( "welcome.txt" ) );
        welcomeText.setWrappingWidth( 980 );
        welcomeText.setFont( Font.font( 16 ) );

        Line sectionSep = new Line( 22, 390, 1002, 390 );
        sectionSep.setStroke( Color.valueOf( "2f4f4f" ) );
        sectionSep.setStrokeWidth( 2 );

        Text updateText = new Text( LoadTextFile.load( "updates.txt" ) );
        updateText.setWrappingWidth( 980 );
        updateText.setFont( Font.font( 16 ) );

        contentContainer.getChildren().addAll( welcomeText, sectionSep, updateText );
        mainContainer.getChildren().addAll( headerContainer, contentContainer );
        root.setContent( mainContainer );

        tab.setContent( root );
        mainTabPane.getTabs().add( tab );

        return mainTabPane;
    }


    /**
     * Assembles the top menu for the program.
     *
     * @return a VBox containing the various UI elements represented in the top menu
     */
    private VBox createTopMenu() {
        VBox topMenu = new VBox();

        inputToolBar = new ToolBar();
        inputToolBar.setPadding( new Insets( 5, 10, 5, 10 ) );

        checkBoxToolBar = new ToolBar();
        checkBoxToolBar.setPadding( new Insets( 5, 10, 5, 10 ) );

        inputSequence = new TextField();
        HBox.setHgrow( inputSequence, Priority.ALWAYS );

        // the input bar can be enlarged by using for example the following code
        //inputToolBar.setMinHeight( 45 );
        //input.setFont( Font.font( "Verdana", FontWeight.BOLD, 20 ) );
        //input.setMinHeight( 35 );
        //Button button = new Button( ">" );
        //button.setFont( Font.font( "Verdana", FontWeight.BOLD, 20 ) );
        //button.setMinWidth( 35 );
        //button.setMinHeight( 35 );

        HBox.setMargin( inputSequence, new Insets( 0,5,0,0 ) );
        inputSequence.setPromptText( "Enter RNA string" );

        processButton = new Button( "Process" );
        processButton.setMinWidth( 80 );

        sequenceCheckBox = new CheckBox();
        sequenceCheckBox.setMinWidth( 120 );
        sequenceCheckBox.setText( "Sequence" );
        sequenceCheckBox.setSelected( true );

        circleCheckBox = new CheckBox();
        circleCheckBox.setMinWidth( 120 );
        circleCheckBox.setText( "Circle Plot" );
        circleCheckBox.setSelected( true );

        matrixCheckBox = new CheckBox();
        matrixCheckBox.setMinWidth( 120 );
        matrixCheckBox.setText( "Matrix" );

        dotBracketCheckBox = new CheckBox();
        dotBracketCheckBox.setMinWidth( 120 );
        dotBracketCheckBox.setText( "Dot-Bracket" );

        inputToolBar.getItems().addAll( inputSequence, processButton );
        checkBoxToolBar.getItems().addAll( sequenceCheckBox, circleCheckBox, dotBracketCheckBox, matrixCheckBox );

        topMenu.getChildren().addAll( menuBar, inputToolBar, checkBoxToolBar );

        return topMenu;
    }

    /**
     * Creates the menu bar for the top menu.
     */
    private void createMenuBar() {
        menuBar = new MenuBar();

        // creates various panes for the menuBar
        fileMenu = new Menu( "_File" );
        viewMenu = new Menu( "_View" );
        helpMenu = new Menu( "_Help" );

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

        menuBar.getMenus().addAll( fileMenu, viewMenu, helpMenu );
    }

    /**
     * Creates a list of items to populate the fileMenu
     *
     * @return a List of MenuItems
     */
    private List<MenuItem> fileMenuItems() {
        List<MenuItem> items = new ArrayList<>();

        // creates the contents, i.e. items in a menu
        open = new MenuItem("_Open");
        MenuItem separator = new SeparatorMenuItem();
        exit = new MenuItem("E_xit");

        items.add( open );
        items.add( separator );
        items.add( exit );

        return items;
    }

    /**
     * Creates a list of items to populate the helpMenu
     *
     * @return a List of MenuItems
     */
    private List<MenuItem> helpMenuItems() {
        List<MenuItem> items = new ArrayList<>();

        // creates the contents, i.e. items in a menu
        about = new MenuItem("_About");

        items.add( about );

        return items;
    }

    /**
     * Creates a list of items to populate the viewMenu
     *
     * @return a List of MenuItems
     */
    private List<MenuItem> viewMenuItems() {
        List<MenuItem> items = new ArrayList<>();

        // creates the contents, i.e. items in a menu
        showTabHeader = new CheckMenuItem("Show Seqence _Name");
        showTabHeader.setSelected( true );

        showTabInfoBox = new CheckMenuItem("Show Seqence _Description");
        showTabInfoBox.setSelected( true );

        items.add(showTabHeader);
        items.add(showTabInfoBox);

        return items;
    }


    /**
     * Creates a small bar at the bottom of the UI.
     *
     * @return a VBox with some contents
     */
    private VBox createBottomMenu() {
        HBox bottomBar = new HBox( 10 );
        bottomBar.setPadding( new Insets( 0, 5, 2, 5 ) );
        bottomBar.setAlignment( Pos.CENTER );

        bottomInfoLabel = new Label();
        bottomInfoLabel.setAlignment( Pos.CENTER );

        bottomBar.getChildren().add( bottomInfoLabel );

        VBox bottomMenu = new VBox();
        bottomMenu.getChildren().addAll( new Separator(), bottomBar );

        return bottomMenu;
    }

} // end class
