package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
 * May 16, 2018
 */

public class View extends BorderPane {

    // Containers
    TabPane mainTabPane;

    // MenuBar, Menus and MenuItems
    MenuBar menuBar;
    Menu fileMenu, viewMenu, helpMenu;
    MenuItem open, exit, about, guide;
    CheckMenuItem showTabHeader, showTabInfoBox;

    // ToolBars
    ToolBar inputToolBar, checkBoxToolBar;

    // Labels
    Label bottomInfoLabel, openLabel, guideLabel, runLabel;

    // Fields
    TextField inputSequence, inputField;

    // Buttons
    Button processButton;

    // CheckBoxes
    CheckBox circleCheckBox, sequenceCheckBox, matrixCheckBox,
             dotBracketCheckBox;

    // ComboBoxes
    ComboBox modeSelector;

    // Tooltips
    Tooltip sequenceTooltip, circleTooltip, bracketTooltip, matrixTooltip, processTooltip, modeTooltip;

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

        Label header = new Label( "Project 78 - Sequence Structure Prediction using Java" );
        header.setPadding( new Insets( 10, 0, 5, 50 ) );
        header.setFont( Font.font( "", FontWeight.BLACK, 22 ) );

        Label subHeader = new Label( "By Dennis Andersen, Marta Massa Gyldenkerne and Arulmolibarman Muthukrishnan" );
        subHeader.setPadding( new Insets( 10, 0, 5, 50 ) );
        subHeader.setFont( Font.font( "", FontWeight.BOLD, 16 ) );

        HBox inputBox = new HBox();
        inputBox.setPadding( new Insets( 10, 0, 10, 0 ) );
        inputField = new TextField();
        inputField.setMinSize( 760, 60 );
        inputField.setMaxSize( 760, 60 );
        inputField.getStyleClass().add( "tab-textfield" );
        inputField.setText( "gggaaaccu" );

        runLabel = new Label( "Try this sequence" );
        runLabel.setMinSize( 220, 60 );
        runLabel.setMaxSize( 220, 60 );
        runLabel.setAlignment( Pos.CENTER );
        runLabel.getStyleClass().add( "tablabel" );
        inputBox.getChildren().addAll( inputField, runLabel );

        HBox hBox = new HBox();
        hBox.setPadding( new Insets( 10, 0, 10, 0 ) );
        hBox.setMinWidth( 980 );
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinWidth( 980 );
        openLabel = new Label( "Open a FASTA file" );
        openLabel.setMinSize( 480, 60 );
        openLabel.setMaxSize( 480, 60 );
        openLabel.setAlignment( Pos.CENTER );
        openLabel.getStyleClass().add( "tablabel" );

        guideLabel = new Label( "Learn more about RNA" );
        guideLabel.setMinSize( 480, 60 );
        guideLabel.setMaxSize( 480, 60 );
        guideLabel.setAlignment( Pos.CENTER );
        guideLabel.getStyleClass().add( "tablabel" );
        AnchorPane.setLeftAnchor( openLabel, 0.0 );
        AnchorPane.setRightAnchor( guideLabel, 0.0 );
        anchorPane.getChildren().addAll( openLabel, guideLabel );
        hBox.getChildren().add( anchorPane );

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
        //contentContainer.setAlignment( Pos.TOP_CENTER );

        Text welcomeText = new Text( LoadTextFile.load( "welcome.txt" ) );
        welcomeText.setWrappingWidth( 980 );
        welcomeText.setFont( Font.font( 16 ) );

        Text infoText = new Text( LoadTextFile.load( "info.txt" ) );
        infoText.setWrappingWidth( 980 );
        infoText.setFont( Font.font( 16 ) );

        Line sectionSep = new Line( 22, 390, 1002, 390 );
        sectionSep.setStroke( Color.valueOf( "2f4f4f" ) );
        sectionSep.setStrokeWidth( 2 );

        Text updateText = new Text( LoadTextFile.load( "updates.txt" ) );
        updateText.setWrappingWidth( 980 );
        updateText.setFont( Font.font( 16 ) );

        contentContainer.getChildren().addAll(
                welcomeText,
                inputBox,
                hBox,
                infoText,
                sectionSep,
                updateText );
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
        // top-left, top-right, bottom-right, and bottom-left corners, in that order.
        inputSequence = new TextField();
        //inputSequence.setStyle( "-fx-border-color: lightgrey; -fx-border-radius: 3 0 0 3; -fx-background-color: white; -fx-background-radius: 3 0 0 3;" );
        inputSequence.getStyleClass().add( "top-textfield" );

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius( 1.0 );
        dropShadow.setOffsetY( 1.0 );
        dropShadow.setColor( Color.color( 1.0, 1.0, 1.0 ) );
        inputSequence.setEffect( dropShadow );

        HBox.setHgrow( inputSequence, Priority.ALWAYS );

        modeSelector = new ComboBox();
        //modeSelector.setStyle( "-fx-border-color: lightgrey; -fx-border-radius: 0 3 3 0; -fx-background-color: white; -fx-background-radius: 0 3 3 0;" );
        modeSelector.getStyleClass().add( "combo-box" );
        modeSelector.setMinSize( 135, 27 );
        modeSelector.setMaxSize( 135, 27 );
        modeSelector.getItems().addAll("Basic Nussinov", "Energy Nussinov" );
        modeSelector.setValue( "Basic Nussinov" );
        modeSelector.setEditable( false );
        modeTooltip = new Tooltip();
        modeTooltip.setText( "Select which algorithm to use" );
        modeTooltip.getStyleClass().add( "tooltip" );
        modeSelector.setTooltip( modeTooltip );

        HBox.setMargin( modeSelector, new Insets( 0,5,0,-5 ) );
        inputSequence.setPromptText( "Enter RNA string" );

        processButton = new Button( "Process" );
        processButton.setMinWidth( 80 );
        processButton.setMinHeight( 27 );
        processTooltip = new Tooltip();
        processTooltip.setText( "Process the RNA sequence to visualize it" );
        processTooltip.getStyleClass().add( "tooltip" );
        processButton.setTooltip( processTooltip );
        //Image img = new Image( "run.png" );
        //processButton = new Button();
        //processButton.setMinSize( 54, 27 );
        //processButton.setMaxSize( 54, 27 );
        //processButton.setGraphic( new ImageView( img ) );

        sequenceCheckBox = new CheckBox();
        sequenceCheckBox.setMinWidth( 120 );
        sequenceCheckBox.setText( "Sequence" );
        sequenceCheckBox.setSelected( true );
        sequenceTooltip = new Tooltip();
        sequenceTooltip.setText( "Visualize the RNA sequence as a line" );
        sequenceTooltip.getStyleClass().add( "tooltip" );
        sequenceCheckBox.setTooltip( sequenceTooltip );

        circleCheckBox = new CheckBox();
        circleCheckBox.setMinWidth( 120 );
        circleCheckBox.setText( "Circle Plot" );
        circleCheckBox.setSelected( true );
        circleTooltip = new Tooltip();
        circleTooltip.setText( "Visualize the RNA sequence as a circle" );
        circleTooltip.getStyleClass().add( "tooltip" );
        circleCheckBox.setTooltip( circleTooltip );

        matrixCheckBox = new CheckBox();
        matrixCheckBox.setMinWidth( 120 );
        matrixCheckBox.setText( "Matrix" );
        matrixTooltip = new Tooltip();
        matrixTooltip.setText( "Show the dynamically programmed matrix of the RNA sequence" );
        matrixTooltip.getStyleClass().add( "tooltip" );
        matrixCheckBox.setTooltip( matrixTooltip );

        dotBracketCheckBox = new CheckBox();
        dotBracketCheckBox.setMinWidth( 120 );
        dotBracketCheckBox.setText( "Dot-Bracket" );
        bracketTooltip = new Tooltip();
        bracketTooltip.setText( "Show Dot-Bracket notation for the RNA sequence" );
        bracketTooltip.getStyleClass().add( "tooltip" );
        dotBracketCheckBox.setTooltip( bracketTooltip );

        inputToolBar.getItems().addAll( inputSequence, modeSelector, processButton );
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
        guide = new MenuItem( "_Learn About RNA" );
        about = new MenuItem("_About");

        items.add( guide );
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
