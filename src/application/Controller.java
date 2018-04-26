package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This class is the controller for the sequence visualizer application.
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

public class Controller {

    private final View view;
    private final Font monoFont;

    private ObservableList<InteractiveCanvas> canvasList = FXCollections.observableArrayList();
    private ObservableList<MovableContainer> matrixList = FXCollections.observableArrayList();
    private ObservableList<VBox> tabVBoxContainers = FXCollections.observableArrayList();
    private ObservableList<MovableContainer> tabInfoContainers = FXCollections.observableArrayList();

    private int tabsCreated = 0;
    private int sequenceCanvasCount = 0;
    private int circleCanvasCount = 0;
    private int matrixCount = 0;
    private int tabHeaderContainerCount = 0;
    private int tabInfoContainerCount = 0;
    private int tabBracketContainerCount = 0;


    /**
     * Constructor
     *
     * @param view the view for this application
     */
    public Controller( View view ) {
        this.view = view;
        this.monoFont = Visualize.getSystemInfo();
        Main.getPrimaryStage().setOnCloseRequest( event -> {
            event.consume();
            closeProgram();
        } );
        assignButtonEvents();
        assignCheckBoxEvents();
        assignMenuEvents();
        assignShortcutEvents();
    }


    /** Associates functionality with the buttons in the view. */
    private void assignButtonEvents() { view.processButton.setOnAction( event ->
            computeSequence( view.inputSequence.getText(), "" )
        );
    }


    /** Associates functionality with the checkboxes in the view. */
    private void assignCheckBoxEvents() {

        view.sequenceCheckBox.setOnAction( event -> {
            if ( view.sequenceCheckBox.isSelected() ) {
                for ( InteractiveCanvas ic : canvasList ) {
                    if ( ic.getId().startsWith( "Seq" ) ) {
                        ic.setVisible( true );
                    }
                }
            } else {
                for ( InteractiveCanvas ic : canvasList ) {
                    if ( ic.getId().startsWith( "Seq" ) ) {
                        ic.setVisible( false );
                    }
                }
            }
        } );

        view.circleCheckBox.setOnAction( event -> {
            if ( view.circleCheckBox.isSelected() ) {
                for ( InteractiveCanvas ic : canvasList ) {
                    if ( ic.getId().startsWith( "Cir" ) ) {
                        ic.setVisible( true );
                    }
                }
            } else {
                for ( InteractiveCanvas ic : canvasList ) {
                    if ( ic.getId().startsWith( "Cir" ) ) {
                        ic.setVisible( false );
                    }
                }
            }
        } );

        view.matrixCheckBox.setOnAction( event -> {
            if ( view.matrixCheckBox.isSelected() ) {
                for ( MovableContainer mc : matrixList ) {
                    mc.setVisible( true );
                }
            } else {
                for ( MovableContainer mc : matrixList ) {
                    mc.setVisible( false );
                }
            }
        } );

        view.dotBracketCheckBox.setOnAction( event -> {
            if ( view.dotBracketCheckBox.isSelected() ) {
                for ( VBox v : tabVBoxContainers ) {
                    if ( v.getId().startsWith( "TabBrack" ) ) {
                        v.setVisible( true );
                    }
                }
            } else {
                for ( VBox v : tabVBoxContainers ) {
                    if ( v.getId().startsWith( "TabBrack" ) ) {
                        v.setVisible( false );
                    }
                }
            }
        } );

    }


    /** Associates functionality with the menuitems in the view. */
    private void assignMenuEvents() {
        /* ****************** assign functionality to fileMenuItems ******************* */
        view.open.setAccelerator( new KeyCodeCombination(
                KeyCode.O,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN
        ) );

        view.open.setOnAction( event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle( "Open File" );

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                    "FASTA files (*.FASTA, *.fasta)", "*.FASTA", "*.fasta" );
            fileChooser.getExtensionFilters().add( extensionFilter );

            File file = fileChooser.showOpenDialog( Main.getPrimaryStage() );

            if ( file != null ) {
                StringBuilder stringBuilder = new StringBuilder();
                String line, sequenceBuilt;
                int i = 0;
                List<String> fileHeaders = new ArrayList<>();

                // try-with-resources to auto-close stream
                try (InputStream inputStream = new FileInputStream( file );
                     BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) ) ) {
                    while ( ( line = bufferedReader.readLine() ) != null ) {
                        if ( line.startsWith( ">" ) ) {
                            fileHeaders.add( line.substring( 1 ) );
                            sequenceBuilt = stringBuilder.toString();
                            if ( !sequenceBuilt.isEmpty() ) {
                                computeSequence( sequenceBuilt, fileHeaders.get( i ) );
                                stringBuilder.setLength( 0 );
                                i++;
                            }
                        } else {
                            stringBuilder.append( line );
                        }
                    }
                    sequenceBuilt = stringBuilder.toString();
                    computeSequence( sequenceBuilt, fileHeaders.get( i ) );
                } catch ( IOException ex ) {
                    ex.printStackTrace();
                }
            }
        } );


        /* ****************** assign functionality to viewMenuItems ******************* */
        view.showTabHeader.setOnAction(event -> {
            if ( view.showTabHeader.isSelected() ) {
                for ( VBox v : tabVBoxContainers ) {
                    if ( v.getId().startsWith( "TabHead" ) ) {
                        v.setVisible( true );
                        for ( VBox vb : tabVBoxContainers ) {
                            if ( vb.getId().startsWith( "TabBrack" ) ) {
                                if ( view.dotBracketCheckBox.isSelected() ) {
                                    vb.setVisible( true );
                                    vb.setTranslateY( 43 );
                                }
                            }
                        }
                    }
                }
            } else {
                for ( VBox v : tabVBoxContainers ) {
                    if ( v.getId().startsWith( "TabHead" ) ) {
                        v.setVisible( false );
                        for ( VBox vb : tabVBoxContainers ) {
                            if ( vb.getId().startsWith( "TabBrack" ) ) {
                                if ( view.dotBracketCheckBox.isSelected() ) {
                                    vb.setVisible( true );
                                    vb.setTranslateY( 0 );
                                }
                            }
                        }
                    }
                }
            }
        } );

        view.showTabInfoBox.setOnAction( event -> {
            if ( view.showTabInfoBox.isSelected() ) {
                for ( MovableContainer mc : tabInfoContainers ) {
                    mc.setVisible( true );
                }
            } else {
                for ( MovableContainer mc : tabInfoContainers ) {
                    mc.setVisible( false );
                }
            }
        } );

        view.exit.setOnAction( event -> closeProgram() );


        /* ****************** assign functionality to helpMenuItems ******************* */
        view.about.setOnAction( event -> AboutBox.display() );

    }


    /** Associates other functionality */
    private void assignShortcutEvents() {
        view.inputSequence.setOnKeyPressed( event -> {
            if ( event.getCode().equals( KeyCode.ENTER ) ) {
                computeSequence( view.inputSequence.getText(), "" );
            }
        } );
    }


    /**
     * Initiate the computation of the sequence entered in the inputTextField
     * in the view. The sequence is scanned and trimmed before the computation
     * starts. This means that the sequence is set to be all upper case, and any
     * illegal characters are removed as well. This means that the user need not
     * worry about case sensitivity, or having accidentally entered in a wrong
     * character.
     *
     * @param str    the RNA sequence to be computed.
     * @param header the file header if applicable.
     */
    private void computeSequence( String str, String header ) {
        long executionStart = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();

        // check length of sequence. If too long, the canvas textures generated become too large
        // for JavaFX to handle. This check shows an error to the user, if the input sequence is
        // too large.
        boolean flag = checkSequenceLength( str );
        if ( !flag ) {
            return;
        }

        // trim the input sequence
        str = str.toUpperCase();
        for (int i = 0; i < str.length(); i++) {
            if ( str.charAt(i) == 'A' || str.charAt(i) == 'C' || str.charAt(i) == 'G' ) {
                stringBuilder.append( str.charAt(i) );
            } else if ( str.charAt(i) == 'U' || str.charAt(i) == 'T' ) {
                stringBuilder.append( 'U' );
            }
        }
        str = stringBuilder.toString();
        char[] chars = str.toCharArray();

        // we check whether the trimmed input sequence is empty. If it is empty we return and do
        // nothing, otherwise the computation begins.
        if ( str.isEmpty() ) { return; }

        // start an instance of the Nussinov Algorithm to compute the input sequence
        Nussinov nussinov = new Nussinov();
        int[][] matrix = nussinov.initiate( str );

        // create visual representations
        StackPane displayVisualization = new StackPane();
        if ( view.sequenceCheckBox.isSelected() ) {
            InteractiveCanvas interactiveCanvas = createSequenceCanvas( str, nussinov.getMatches() );
            interactiveCanvas.setId( "SequenceCanvas" + ++sequenceCanvasCount );
            canvasList.add( interactiveCanvas );
            displayVisualization.getChildren().addAll( interactiveCanvas );
        }
        if ( view.circleCheckBox.isSelected() ) {
            InteractiveCanvas interactiveCanvas = createCircleCanvas( str, nussinov.getMatches() );
            interactiveCanvas.setId( "CircleCanvas" + ++circleCanvasCount );
            canvasList.add( interactiveCanvas );
            displayVisualization.getChildren().add( interactiveCanvas );
        }
        if ( view.matrixCheckBox.isSelected() ) {
            MovableContainer matrixGrid = createMatrix( str.length(), matrix, chars );
            matrixGrid.setId( "Matrix" + ++matrixCount );
            matrixList.add( matrixGrid );
            displayVisualization.getChildren().add( matrixGrid );
        }
        VBox tabHeaderContainer = createTabHeader( header );
        tabHeaderContainer.setId( "TabHead" + ++tabHeaderContainerCount );
        tabVBoxContainers.add( tabHeaderContainer );
        if ( !view.showTabHeader.isSelected() ) {
            tabHeaderContainer.setVisible( false );
        }

        // create the infoBox
        MovableContainer tabInfoContainer = createTabInfoBox( nussinov.getMatches(), chars );
        tabInfoContainer.setId( "TabInfoBox" + ++tabInfoContainerCount );
        tabInfoContainers.add( tabInfoContainer );
        if ( !view.showTabInfoBox.isSelected() ) {
            tabInfoContainer.setVisible( false );
        }

        // create dot-bracket bar
        VBox dotBracketContainer = createDotBracket( str, nussinov.getDotBracketOutput() );
        dotBracketContainer.setId( "TabBrack" + ++tabBracketContainerCount);
        tabVBoxContainers.add( dotBracketContainer );

        // assemble results
        StackPane resultTabStackPane = new StackPane();
        resultTabStackPane.setAlignment( Pos.TOP_LEFT );
        resultTabStackPane.setStyle( "-fx-background-color: whitesmoke" );
        resultTabStackPane.getChildren().addAll( displayVisualization, tabInfoContainer, tabHeaderContainer );
        if ( view.dotBracketCheckBox.isSelected() ) {
            resultTabStackPane.getChildren().add( dotBracketContainer );
        }

        VBox vBox = new VBox();
        vBox.getChildren().addAll( resultTabStackPane );

        // put results in a tab
        Tab tab = new Tab();
        tab.setText( "New " + ++tabsCreated );
        tab.setContent( vBox );

        // show tab in view
        view.mainTabPane.getTabs().add( tab );
        view.mainTabPane.getSelectionModel().select( tab );

        long executionEnd = System.currentTimeMillis() - executionStart;
        view.bottomInfoLabel.setText( "Computed sequence in " + ( executionEnd / 1000d ) + " s." );
    }

    /**
     * Institutes a check of the input sequence length.
     * If the sequence length is less than 250 characters, everything can be
     * rendered, and we return true.
     * If the sequence length is between 250 and 500 characters, only the
     * sequence visualization and the dot-bracket output can be rendered. If
     * the circle or matrix representations are selected, the application will
     * show an error.
     * If the sequence length is longer than 500 characters, only the list of
     * results computed can be shown. However,
     *
     * @param str the input sequence
     * @return true if the sequence can be computed, and false otherwise.
     */
    private boolean checkSequenceLength( String str ) {

        if ( str.length() <= 250 ) {
            return true;
        } else if ( str.length() <= 500 ) {
            if ( view.circleCheckBox.isSelected() || view.matrixCheckBox.isSelected() ) {
                Alert alert = new Alert( Alert.AlertType.ERROR );
                alert.setTitle( "Sequence Length Error" );
                alert.setHeaderText( "Sequence too large" );
                alert.setContentText( "The circle, matrix and dot-bracket options cannot be visualized with input sequences greater than 250 characters. Try selecting only the 'Sequence' option, or try a shorter sequence." );
                alert.showAndWait();
                return false;
            } else {
                return true;
            }
        } else if ( str.length() <= 10000 ) {
            if ( !view.sequenceCheckBox.isSelected()   &&
                 !view.circleCheckBox.isSelected()     &&
                 !view.dotBracketCheckBox.isSelected() &&
                 !view.matrixCheckBox.isSelected() ) {
                return true;
            } else {
                Alert alert = new Alert( Alert.AlertType.ERROR );
                alert.setTitle( "Sequence Length Error" );
                alert.setHeaderText( "Sequence too large" );
                alert.setContentText( "Unfortunately, the sequence length is too large to be visualized. However, if you turn the visualization options off, you can still get some info on the sequence." );
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert( Alert.AlertType.ERROR );
            alert.setTitle( "Sequence Length Error" );
            alert.setHeaderText( "Sequence too large" );
            alert.setContentText( "Unfortunately, the sequence length is too large to be computed" );
            alert.showAndWait();
        }
        return false;
    }


    /**
     * Creates an interactive canvas for the circle representation to be drawn upon.
     *
     * @param sequence an RNA sequence.
     * @param matches  a List of matches
     * @return an Interactive Canvas.
     */
    private InteractiveCanvas createSequenceCanvas( String sequence, List<Tuple> matches ) {
        int length = sequence.length();
        double width, height;

        // sets a scalar to adjust the size of the radius based on the input length of the string
        if ( length < 10 ) {
            width = ( ( length * 40 ) + 36 );
            height = ( 30 + ( length * 10 ) + 36 ) * 0.7;
        } else if ( length < 25 ) {
            width = ( ( length * 40 ) + 36 );
            height = ( 30 + ( length * 10 ) + 36 ) * 0.6;
        } else if ( length < 50 ) {
            width = ( ( length * 30 ) + 24 );
            height = ( 30 + ( length * 10 ) + 24 ) * 0.6;
        } else if ( length < 100 ){
            width = ( ( length * 15 ) + 24 );
            height = ( 30 + ( length * 10 ) + 24 ) * 0.55;
        } else if ( length < 200 ) {
            width = ( ( length * 15 ) + 12 );
            height = ( 30 + ( length * 10 ) + 12 ) * 0.55;
        } else {
            width = ( ( length * 15 ) + 12 );
            height = ( 30 + ( length * 10 ) + 12 ) * 0.55;
        }
        InteractiveCanvas interactiveCanvas = new InteractiveCanvas( width, height );
        GraphicsContext gc = interactiveCanvas.getGraphicsContext2D();
        Visualize.drawSequence( gc, sequence, matches, width, height );
        return interactiveCanvas;
    }


    /**
     * Creates an interactive canvas for the circle representation to be drawn upon.
     *
     * @param sequence an RNA sequence.
     * @param matches  a List of matches
     * @return an Interactive Canvas.
     */
    private InteractiveCanvas createCircleCanvas( String sequence, List<Tuple> matches ) {
        int length = sequence.length();
        double size;

        // sets a scalar to adjust the size of the radius based on the input length of the string
        if ( length < 10 ) {
            size = ( ( length * 30 ) + 36 );
        } else if ( length < 25 ) {
            size = ( ( length * 30 ) + 36 ) * 0.75;
        } else if ( length < 50 ) {
            size = ( ( length * 40 ) + 24 ) * 0.45;
        } else if ( length < 100 ){
            size = ( ( length * 45 ) + 12 ) * 0.26;
        } else if ( length < 200 ) {
            size = ( ( length * 45 ) + 12 ) * 0.24;
        } else {
            size = ( ( length * 45 ) + 12 ) * 0.22;
        }
        InteractiveCanvas interactiveCanvas = new InteractiveCanvas( size, size );
        GraphicsContext gc = interactiveCanvas.getGraphicsContext2D();
        Visualize.drawCircleRep( gc, sequence, matches, size, size );
        return interactiveCanvas;
    }

    /**
     * Creates a matrix representation of the Nussinov algorithm.
     * The matrix representation is organized in a MovableContainer,
     * which can be moved around by dragging it with the mouse.
     *
     * @param size      the sequence length.
     * @param matrix    an int[][] computed by the Nussinov algorithm.
     * @param charArray a character array containing the input sequence.
     * @return a MovableContainer with the matrix representation.
     */
    private MovableContainer createMatrix( int size, int[][] matrix, char[] charArray ) {

        MovableContainer root = new MovableContainer();
        root.setPadding( new Insets( 5 ) );

        DropShadow dropShadow = Visualize.createShadowEffect();
        root.setEffect( dropShadow );
        root.setStyle( "-fx-border-color: lightgrey; -fx-background-color: whitesmoke" );

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPadding( new Insets( 0,0,5,0 ) );
        Label matrixLabel = new Label( "Matrix" );
        Label closeLabel = new Label( "X" );
        closeLabel.setStyle( "-fx-text-fill: gray" );
        closeLabel.setOnMouseClicked( event -> root.setVisible( false ) );
        closeLabel.setOnMouseEntered( event -> closeLabel.setStyle( "-fx-text-fill: steelblue" ) );
        closeLabel.setOnMouseExited( event -> closeLabel.setStyle( "-fx-text-fill: gray" ) );

        AnchorPane.setLeftAnchor( matrixLabel, 0.0 );
        AnchorPane.setRightAnchor( closeLabel, 0.0 );

        anchorPane.getChildren().addAll( matrixLabel, closeLabel );

        GridPane grid = new GridPane();
        grid.setAlignment( Pos.CENTER );

        // double loop which creates a matrix representation
        for ( int y = 0; y < size + 1; y++ ) {
            for ( int x = 0; x < size + 1; x++ ) {

                // create a new Label in each iteration
                Label label = new Label();
                label.setMinHeight( 15 );
                label.setMinWidth( 15 );
                label.setFont( monoFont );
                label.setFont( Font.font( 12 ) );
                label.setAlignment( Pos.CENTER );
                if( y == 0 && x == 0 ) {
                    label.setText( "" );
                } else if( y == 0 ) {
                    label.setText( String.valueOf( charArray[x - 1] ).toUpperCase() );
                } else if( x == 0 ) {
                    label.setText( String.valueOf( charArray[y - 1] ).toUpperCase() );
                } else {
                    label.setText( String.valueOf( matrix[y - 1][x - 1] ) );
                }

                // iterate the index using the loops
                grid.setRowIndex( label, y );
                grid.setColumnIndex( label, x );
                grid.getChildren().add( label );
            }
        }
        // Shows the matrix representation in a grid.
        grid.setGridLinesVisible( true );

        root.setMaxSize( grid.getWidth(), grid.getHeight() );
        root.getChildren().addAll( anchorPane, grid );

        return root;
    }


    /**
     * Create a header that can be displayed in the newly created tab.
     * The information to be displayed in the header can be passed as
     * a parameter, which is what will normally happen when loading a
     * file. If the parameter contains an empty string, the method
     * will just write "Custom Sequence" in the header returned.
     *
     * @param header a file header if applicable.
     * @return a VBox set up to display the header.
     */
    private VBox createTabHeader( String header ) {

        VBox root = new VBox();
        root.setMaxHeight( 40 );
        root.setPrefSize( 1920, 40 );

        Label tabHeader = new Label();
        tabHeader.setPadding( new Insets( 5, 10, 5, 10 ) );
        tabHeader.setPrefWidth( 1920 );
        tabHeader.setFont( Font.font( "", FontWeight.BLACK, 22 ) );
        tabHeader.setStyle( "-fx-background-color: whitesmoke; -fx-font-smoothing-type: gray" ); // -fx-border-color: black;

        if ( !header.isEmpty() ) {
            tabHeader.setText( header );
        } else {
            tabHeader.setText( "Custom Sequence" );
        }

        root.getChildren().addAll( tabHeader, new Separator() );

        return root;
    }


    /**
     * Create a movable box in which information can be displayed.
     * Creates a MovableContainer, with the match information found
     * by the Nussinov algorithm. As implied by the name, the box
     * can be moved around by dragging it with the mouse.
     *
     * @param matches a list of matches found by the Nussinov algorithm.
     * @param chars   a character array containing the input sequence.
     * @return a MovableContainer with results organized in a ListView.
     */
    private MovableContainer createTabInfoBox( List<Tuple> matches, char[] chars ) {

        ListView<GridPane> matchesListView = new ListView<>();
        ObservableList<GridPane> matchesListViewItems = FXCollections.observableArrayList();

        Image[] images = new Image[]{
                new Image( "adenine-small.png" ),
                new Image( "cytosine-small.png" ),
                new Image( "guanine-small.png" ),
                new Image( "urasil-small.png" ),
                new Image( "double-binding-small.png" ),
                new Image( "triple-binding-small.png" )
        };

        DropShadow dropShadow = Visualize.createShadowEffect();

        MovableContainer root = new MovableContainer();
        root.setPadding( new Insets( 5 ) );
        root.setTranslateX( 10 );
        root.setTranslateY( 85 );
        root.setAlignment( Pos.TOP_LEFT );
        root.setMinWidth( 190 );
        root.setMaxWidth( 190 );
        root.setPrefHeight( 480 );
        root.setMaxHeight( 480 );
        root.setStyle( "-fx-border-color: lightgrey; -fx-background-color: whitesmoke" );
        root.setEffect( dropShadow );

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPadding( new Insets( 0,0,5,0 ) );
        Label resultsLabel = new Label( "Results" );
        Label closeLabel = new Label( "X" );
        closeLabel.setStyle( "-fx-text-fill: gray" );
        closeLabel.setOnMouseClicked( event -> root.setVisible( false ) );
        closeLabel.setOnMouseEntered( event -> closeLabel.setStyle( "-fx-text-fill: steelblue" ) );
        closeLabel.setOnMouseExited( event -> closeLabel.setStyle( "-fx-text-fill: gray" ) );

        AnchorPane.setLeftAnchor( resultsLabel, 0.0 );
        AnchorPane.setRightAnchor( closeLabel, 0.0 );

        anchorPane.getChildren().addAll( resultsLabel, closeLabel );

        VBox overviewContainer = new VBox();

        GridPane overview = new GridPane();

        Label sequenceLengthLabel = new Label( "Sequence Length:" );
        sequenceLengthLabel.setFont( Font.font( 14 ) );
        //tabInfoLabel.setStyle( "-fx-background-color: whitesmoke" ); // -fx-border-color: lightgrey;

        Label sequenceLengthCountLabel = new Label(  String.valueOf( chars.length ) );
        sequenceLengthCountLabel.setMinWidth( 45 );
        sequenceLengthCountLabel.setFont( Font.font( 16 ) );
        sequenceLengthCountLabel.setAlignment( Pos.CENTER_RIGHT );

        Label matchesLabel = new Label( "Number of Matches:" );
        matchesLabel.setFont( Font.font( 14 ) );

        Label matchesCountLabel = new Label(  String.valueOf( matches.size() ) );
        matchesCountLabel.setMinWidth( 45 );
        matchesCountLabel.setFont( Font.font( 16 ) );
        matchesCountLabel.setAlignment( Pos.CENTER_RIGHT );

        overview.add( sequenceLengthLabel, 0, 0 );
        overview.add( sequenceLengthCountLabel, 1, 0 );
        overview.add( matchesLabel, 0, 1 );
        overview.add( matchesCountLabel, 1, 1 );

        Label listViewHeaderLabel = new Label( "Matches Found" );
        listViewHeaderLabel.setMinWidth( 180 );
        listViewHeaderLabel.setMinHeight( 40 );
        listViewHeaderLabel.setAlignment( Pos.BOTTOM_CENTER );

        matchesListView.setPrefSize( 180, 450 );
        VBox.setVgrow( matchesListView, Priority.SOMETIMES );

        int i, j;
        int dbindings = 0;
        int tbindings = 0;
        for ( Tuple t : matches ) {
            GridPane g = new GridPane();
            i = t.getI();
            j = t.getJ();

            Label leftGraphicsLabel = new Label();
            Label middleGraphicsLabel = new Label();
            Label rightGraphicsLabel = new Label();
            Label matchLabel = new Label( "(" + ( i + 1 ) + "," + ( j + 1 ) + ")" );
            matchLabel.setFont( Font.font( 16 ) );
            matchLabel.setAlignment( Pos.CENTER );
            matchLabel.setPrefWidth( 75 );

            if ( chars[i] == 'A' ) {
                leftGraphicsLabel.setGraphic( new ImageView( images[0] ) );
                middleGraphicsLabel.setGraphic( new ImageView( images[4] ) );
                rightGraphicsLabel.setGraphic( new ImageView( images[3] ) );
                dbindings++;
            } else if ( chars[i] == 'C' ) {
                leftGraphicsLabel.setGraphic( new ImageView( images[1] ) );
                middleGraphicsLabel.setGraphic( new ImageView( images[5] ) );
                rightGraphicsLabel.setGraphic( new ImageView( images[2] ) );
                tbindings++;
            } else if ( chars[i] == 'G' ) {
                leftGraphicsLabel.setGraphic( new ImageView( images[2] ) );
                middleGraphicsLabel.setGraphic( new ImageView( images[5] ) );
                rightGraphicsLabel.setGraphic( new ImageView( images[1] ) );
                tbindings++;
            } else if ( chars[i] == 'U' ) {
                leftGraphicsLabel.setGraphic( new ImageView( images[3] ) );
                middleGraphicsLabel.setGraphic( new ImageView( images[4] ) );
                rightGraphicsLabel.setGraphic( new ImageView( images[0] ) );
                dbindings++;
            }
            g.add( leftGraphicsLabel, 0 , 0 );
            g.add( middleGraphicsLabel, 1 , 0 );
            g.add( rightGraphicsLabel, 2 , 0 );
            g.add( matchLabel, 3, 0 );

            matchesListViewItems.add( g );
        }

        Label dbindingsLabel = new Label( "Double Bindings:" );
        dbindingsLabel.setFont( Font.font( 14 ) );

        Label dbindingsCountLabel = new Label(  String.valueOf( dbindings ) );
        dbindingsCountLabel.setMinWidth( 45 );
        dbindingsCountLabel.setFont( Font.font( 16 ) );
        dbindingsCountLabel.setAlignment( Pos.CENTER_RIGHT );

        Label tbindingsLabel = new Label( "Triple Bindings:" );
        tbindingsLabel.setFont( Font.font( 14 ) );

        Label tbindingsCountLabel = new Label(  String.valueOf( tbindings ) );
        tbindingsCountLabel.setMinWidth( 45 );
        tbindingsCountLabel.setFont( Font.font( 16 ) );
        tbindingsCountLabel.setAlignment( Pos.CENTER_RIGHT );

        overview.add( dbindingsLabel, 0, 2 );
        overview.add( dbindingsCountLabel, 1, 2 );
        overview.add( tbindingsLabel, 0, 3 );
        overview.add( tbindingsCountLabel, 1, 3 );

        overviewContainer.getChildren().add( overview );

        matchesListView.setItems( matchesListViewItems );

        root.getChildren().addAll( anchorPane, overviewContainer, listViewHeaderLabel, matchesListView );

        return root;
    }

    /**
     * Create a container that can be displayed in the newly created tab,
     * to show the dot-bracket output of a given sequence.
     *
     * @param sequence an RNA sequence.
     * @param brackets a dot-bracket sequence.
     * @return a VBox set up to display the header.
     */
    private VBox createDotBracket( String sequence, String brackets ) {

        VBox root = new VBox();
        root.setMaxHeight( 30 );
        root.setPrefSize( 1920, 30 );
        root.setTranslateY( 43 );
        root.setStyle( "-fx-background-color: whitesmoke; -fx-font-smoothing-type: gray" ); // ; -fx-border-color: lightgrey

        Label stringLabel = new Label( "SEQUENCE" );
        stringLabel.setPadding( new Insets( 0, 10, 0, 10 ) );
        stringLabel.setMinWidth( 95 );
        stringLabel.setFont( Font.font( 10 ) );

        Label notationLabel = new Label( "DOT-BRACKET" );
        notationLabel.setPadding( new Insets( 0, 10, 0, 10 ) );
        notationLabel.setMinWidth( 95 );
        notationLabel.setFont( Font.font( 10 ) );

        Label sequenceLabel = new Label( sequence );
        sequenceLabel.setFont( Font.font( java.awt.Font.MONOSPACED, 11 ) );
        sequenceLabel.setAlignment( Pos.CENTER_LEFT );
        HBox.setHgrow( sequenceLabel, Priority.ALWAYS );

        Label dotBracketLabel = new Label( brackets );
        dotBracketLabel.setFont( Font.font( java.awt.Font.MONOSPACED, 11 ) );
        dotBracketLabel.setAlignment( Pos.CENTER_LEFT );
        HBox.setHgrow( dotBracketLabel, Priority.ALWAYS );

        HBox sequenceContainer = new HBox();
        HBox dotBracketContainer = new HBox();
        sequenceContainer.getChildren().addAll( stringLabel, sequenceLabel );
        dotBracketContainer.getChildren().addAll( notationLabel, dotBracketLabel );

        Label bottomBorder = new Label();
        bottomBorder.setMinSize( 1920, 1 );
        bottomBorder.setStyle( "-fx-border-color: lightgrey" );

        root.getChildren().addAll( sequenceContainer, dotBracketContainer, bottomBorder );

        return root;
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
        if ( answer ) { Main.getPrimaryStage().close(); }
    }

} // end class
