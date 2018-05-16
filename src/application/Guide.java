package application;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * FF501 First Year Project - 78 - Sequence Structure Prediction Using Java
 *
 * Department of Mathematics and Computer Science (IMADA),
 * University of Southern Denmark, Campusvej 55, 5230 Odense, Denmark
 *
 * This class contains the info-graphic guide, which gives some basic
 * information on protein synthesis.
 *
 * Main author:
 * @author Marta Massa Gyldenkerne      -- magyl17
 *
 * Contributive authors:
 * @author Dennis Andersen              -- deand17
 * @author Arulmolibarman Muthukrishnan -- armut13
 *
 * Supervisor: Philipp Weber, Ph.D. Student, Computer Science
 *
 * May 16, 2018
 */

public class Guide {

    public static StackPane create() {

        StackPane root = new StackPane();

        Label background = new Label();
        background.setGraphic( new ImageView( "baggrund copia.png" ) );
        background.setMaxSize(1024,876);

        //Protein

        Label label1 = new Label();
        label1.setFont( Font.font( 16 ) );
        label1.setMinWidth( 230 );
        label1.setMinHeight( 180 );
        label1.setTranslateX( 340 );
        label1.setTranslateY( 240 );

        Label protein = new Label();
        protein.setGraphic(new ImageView("Protest.png"));
        protein.setWrapText( true );
        protein.setMaxSize(160,160);
        protein.setStyle( "-fx-border-color: black" );
        protein.setVisible( false );
        protein.setTranslateX( -100 );
        protein.setTranslateY( 0 );
        protein.setOnMouseEntered( e -> { protein.setVisible( true ); } );

        // Base pairs

        Label outcg = new Label();
        outcg.setFont(Font.font(16));
        outcg.setMinHeight(30);
        outcg.setMinWidth(80);
        outcg.setTranslateX(-424);
        outcg.setTranslateY(275);

        Label cg = new Label();
        cg.setGraphic(new ImageView("Guanine-cytosine.png"));
        cg.setWrapText( true );
        cg.setMaxSize(160,160);
        cg.setStyle( "-fx-background-color: whitesmoke" );
        cg.setVisible( false );
        cg.setTranslateX( -100 );
        cg.setTranslateY( 50 );

        Label outau = new Label();
        outau.setFont(Font.font(16));
        outau.setMinHeight(30);
        outau.setMinWidth(80);
        outau.setTranslateX(-424);
        outau.setTranslateY(245);

        Label au = new Label();
        au.setGraphic(new ImageView("adenine-uracil.png"));
        au.setWrapText( true );
        au.setMaxSize(160,160);
        au.setMinWidth(160);
        au.setMaxWidth(160);
        au.setStyle( "-fx-background-color: whitesmoke" );
        au.setVisible( false );
        au.setTranslateX( -300 );
        au.setTranslateY( 50 );

        Label outcgup = new Label();
        outcgup.setFont(Font.font(16));
        outcgup.setMinHeight(30);
        outcgup.setMinWidth(80);
        outcgup.setTranslateX(-424);
        outcgup.setTranslateY(160);

        Label cgup = new Label();
        cgup.setGraphic(new ImageView("Guanine-cytosine.png"));
        cgup.setWrapText( true );
        cgup.setMaxSize(160,160);
        cgup.setStyle( "-fx-background-color: whitesmoke" );
        cgup.setVisible( false );
        cgup.setTranslateX( -100 );
        cgup.setTranslateY( 50 );

        Label outat = new Label();
        outat.setFont(Font.font(16));
        outat.setMinHeight(30);
        outat.setMinWidth(80);
        outat.setTranslateX(-424);
        outat.setTranslateY(120);

        Label at = new Label();
        at.setGraphic(new ImageView("thymine-adenine.png"));
        at.setWrapText( true );
        at.setMaxSize(160,160);
        at.setStyle( "-fx-background-color: whitesmoke" );
        at.setVisible( false );
        at.setTranslateX( -100 );
        at.setTranslateY( 50 );


        //DNA

        Label dnastruc = new Label();
        dnastruc.setFont(Font.font(16));
        dnastruc.setMinHeight(225);
        dnastruc.setMinWidth(200);
        dnastruc.setTranslateX(-390);
        dnastruc.setTranslateY(-120);

        Label dnastructurepic = new Label();
        dnastructurepic.setGraphic(new ImageView("dna structure.png"));
        dnastructurepic.setWrapText( true );
        dnastructurepic.setStyle( "-fx-background-color: whitesmoke" );
        dnastructurepic.setVisible( false );
        dnastructurepic.setTranslateX( 150 );
        dnastructurepic.setTranslateY( 50 );

        Label dnastruc1 = new Label();
        dnastruc1.setFont(Font.font(16));
        dnastruc1.setMinHeight(130);
        dnastruc1.setMinWidth(90);
        dnastruc1.setTranslateX(-280);
        dnastruc1.setTranslateY(-230);

        Label dnastructurepic1 = new Label();
        dnastructurepic1.setGraphic(new ImageView("dna structure.png"));
        dnastructurepic1.setWrapText( true );
        dnastructurepic1.setStyle( "-fx-background-color: whitesmoke" );
        dnastructurepic1.setVisible( false );
        dnastructurepic1.setTranslateX( 150 );
        dnastructurepic1.setTranslateY( 50 );

        Label dnaexp1 = new Label();
        dnaexp1.setFont(Font.font(16));
        dnaexp1.setMinHeight(50);
        dnaexp1.setMinWidth(145);
        dnaexp1.setTranslateX(-405);
        dnaexp1.setTranslateY(-265);

        Label dnaexp = new Label();
        dnaexp.setGraphic(new ImageView("DNAexp.png"));
        dnaexp.setWrapText( true );
        dnaexp.setStyle( "-fx-background-color: whitesmoke" );
        dnaexp.setMaxWidth(160);
        dnaexp.setVisible( false );
        dnaexp.setTranslateX( 0 );
        dnaexp.setTranslateY(0);

        //RNA

        Label outrna = new Label();
        outrna.setFont(Font.font(16));
        outrna.setMinHeight(185);
        outrna.setMinWidth(360);
        outrna.setTranslateX(-140);
        outrna.setTranslateY(100);

        Label rnaes = new Label();
        rnaes.setGraphic(new ImageView("RNA structure1.png"));
        rnaes.setWrapText( true );
        rnaes.setStyle( "-fx-background-color: whitesmoke" );
        rnaes.setMaxWidth(200);
        rnaes.setVisible( false );
        rnaes.setTranslateX( 280);
        rnaes.setTranslateY( 50);

        Label outrna1 = new Label();
        outrna1.setFont(Font.font(16));
        outrna1.setMinHeight(50);
        outrna1.setMinWidth(115);
        outrna1.setTranslateX(-15);
        outrna1.setTranslateY(0);

        Label rnaes1 = new Label();
        rnaes1.setGraphic(new ImageView("RNA structure1.png"));
        rnaes1.setWrapText( true );
        rnaes1.setStyle( "-fx-background-color: whitesmoke" );
        rnaes1.setMaxWidth(200);
        rnaes1.setVisible( false );
        rnaes1.setTranslateX( 280);
        rnaes1.setTranslateY( 50);

        //RNA 3structures

        Label rnaexp = new Label();
        rnaexp.setFont(Font.font(16));
        rnaexp.setMinHeight(180);
        rnaexp.setMinWidth(260);
        rnaexp.setTranslateX(240);
        rnaexp.setTranslateY(0);

        Label inrnaexp = new Label();
        inrnaexp.setGraphic(new ImageView("3dstructure.jpg"));
        inrnaexp.setWrapText( true );
        inrnaexp.setStyle( "-fx-background-color: whitesmoke" );
        inrnaexp.setMaxWidth(200);
        inrnaexp.setVisible( false );
        inrnaexp.setTranslateX( -200);
        inrnaexp.setTranslateY( -50);

        // rna explanation

        Label outrna3d = new Label();
        outrna3d.setFont(Font.font(16));
        outrna3d.setMinHeight(50);
        outrna3d.setMinWidth(120);
        outrna3d.setTranslateX(-187);
        outrna3d.setTranslateY(-10);

        Label rnaes3d = new Label();
        rnaes3d.setGraphic(new ImageView("RNAexp.png"));
        rnaes3d.setWrapText( true );
        rnaes3d.setStyle( "-fx-background-color: whitesmoke" );
        rnaes3d.setMaxWidth(200);
        rnaes3d.setVisible( false );
        rnaes3d.setTranslateX( 120);
        rnaes3d.setTranslateY( 0);

        //translation

        Label translation = new Label();
        translation.setFont(Font.font(16));
        translation.setMinHeight(50);
        translation.setMinWidth(190);
        translation.setTranslateX(-60);
        translation.setTranslateY(310);


        Label translationin = new Label();
        translationin.setGraphic(new ImageView("Translation.png"));
        translationin.setWrapText( true );
        translationin.setStyle( "-fx-background-color: whitesmoke" );
        translationin.setMaxWidth(180);
        translationin.setVisible( false );
        translationin.setTranslateX( 0);
        translationin.setTranslateY( 0);

        //transcription


        Label transcription = new Label();
        transcription.setFont(Font.font(16));
        transcription.setMinHeight(50);
        transcription.setMinWidth(205);
        transcription.setTranslateX(40);
        transcription.setTranslateY(-180);

        Label transcriptionin = new Label();
        transcriptionin.setGraphic(new ImageView("Transcription.png"));
        transcriptionin.setWrapText( true );
        transcriptionin.setStyle( "-fx-background-color: whitesmoke" );
        transcriptionin.setMinWidth(200);
        transcriptionin.setMaxWidth(200);
        transcriptionin.setVisible( false );
        transcriptionin.setTranslateX( -40);
        transcriptionin.setTranslateY( 80);

        //protein synthesis

        Label outprotsyn = new Label();
        outprotsyn.setFont(Font.font(16));
        outprotsyn.setMinHeight(130);
        outprotsyn.setMinWidth(410);
        outprotsyn.setTranslateX(-30);
        outprotsyn.setTranslateY(-290);

        Label protsyn = new Label();
        protsyn.setGraphic(new ImageView("Protsyn.png"));
        protsyn.setWrapText( true );
        protsyn.setStyle( "-fx-background-color: whitesmoke" );
        protsyn.setMinWidth(200);
        protsyn.setMaxWidth(200);
        protsyn.setVisible( false );
        protsyn.setTranslateX( -100);
        protsyn.setTranslateY( 0);

        //types of rna

        Label types = new Label();
        types.setFont(Font.font(16));
        types.setMinHeight(200);
        types.setMinWidth(240);
        types.setTranslateX(375);
        types.setTranslateY(-250);

        Label typesin = new Label();
        typesin.setGraphic(new ImageView("typesofRNA.png"));
        typesin.setWrapText( true );
        typesin.setStyle( "-fx-background-color: whitesmoke" );
        typesin.setVisible( false );
        typesin.setTranslateX( -50);
        typesin.setTranslateY( 0);


        // protein explanation

        Label protexout = new Label();
        protexout.setFont(Font.font(16));
        protexout.setMinHeight(50);
        protexout.setMinWidth(120);
        protexout.setTranslateX(335);
        protexout.setTranslateY(120);

        Label protex = new Label();
        protex.setGraphic(new ImageView("proteinexp.png"));
        protex.setWrapText( true );
        protex.setVisible( false );
        protex.setTranslateX( 0);
        protex.setTranslateY( 0);



        root.getChildren().add( background );
        root.getChildren().add( label1 );
        root.getChildren().add( protein );
        root.getChildren().add(outcg);
        root.getChildren().add(cg);
        root.getChildren().add(outau);
        root.getChildren().add(au);
        root.getChildren().add(outcgup);
        root.getChildren().add(cgup);
        root.getChildren().add(outat);
        root.getChildren().add(at);
        root.getChildren().add(dnastruc);
        root.getChildren().add(dnastructurepic);
        root.getChildren().add(dnaexp1);
        root.getChildren().add(dnaexp);
        root.getChildren().add(dnastruc1);
        root.getChildren().add(dnastructurepic1);
        root.getChildren().add(outrna);
        root.getChildren().add(rnaes);
        root.getChildren().add(outrna1);
        root.getChildren().add(rnaes1);
        root.getChildren().add(outrna3d);
        root.getChildren().add(rnaes3d);
        root.getChildren().add(translation);
        root.getChildren().add(translationin);
        root.getChildren().add(transcription);
        root.getChildren().add(transcriptionin);
        root.getChildren().add(outprotsyn);
        root.getChildren().add(protsyn);
        root.getChildren().add(types);
        root.getChildren().add(typesin);
        root.getChildren().add(rnaexp);
        root.getChildren().add(inrnaexp);
        root.getChildren().add(protexout);
        root.getChildren().add(protex);





        label1.setOnMouseEntered( e -> { protein.setVisible( true ); } );
        label1.setOnMouseExited( e -> { protein.setVisible( false ); } );

        outcg.setOnMouseEntered( e -> { cg.setVisible( true ); } );
        outcg.setOnMouseExited( e -> { cg.setVisible( false ); } );

        outau.setOnMouseEntered( e -> { au.setVisible( true ); } );
        outau.setOnMouseExited( e -> { au.setVisible( false ); } );

        outcgup.setOnMouseEntered( e -> { cgup.setVisible( true ); } );
        outcgup.setOnMouseExited( e -> { cgup.setVisible( false ); } );

        outat.setOnMouseEntered( e -> { at.setVisible( true ); } );
        outat.setOnMouseExited( e -> { at.setVisible( false ); } );

        dnastruc.setOnMouseEntered( e -> { dnastructurepic.setVisible( true ); } );
        dnastruc.setOnMouseExited( e -> { dnastructurepic.setVisible( false ); } );

        dnaexp1.setOnMouseEntered( e -> { dnaexp.setVisible( true ); } );
        dnaexp1.setOnMouseExited( e -> { dnaexp.setVisible( false ); } );

        dnastruc1.setOnMouseEntered( e -> { dnastructurepic1.setVisible( true ); } );
        dnastruc1.setOnMouseExited( e -> { dnastructurepic1.setVisible( false ); } );

        outrna.setOnMouseEntered( e -> { rnaes.setVisible( true ); } );
        outrna.setOnMouseExited( e -> { rnaes.setVisible( false ); } );

        outrna1.setOnMouseEntered( e -> { rnaes1.setVisible( true ); } );
        outrna1.setOnMouseExited( e -> { rnaes1.setVisible( false ); } );

        outrna3d.setOnMouseEntered( e -> { rnaes3d.setVisible( true ); } );
        outrna3d.setOnMouseExited( e -> { rnaes3d.setVisible( false ); } );

        translation.setOnMouseEntered( e -> { translationin.setVisible( true ); } );
        translation.setOnMouseExited( e -> { translationin.setVisible( false ); } );

        transcription.setOnMouseEntered( e -> { transcriptionin.setVisible( true ); } );
        transcription.setOnMouseExited( e -> { transcriptionin.setVisible( false ); } );

        outprotsyn.setOnMouseEntered( e -> { protsyn.setVisible( true ); } );
        outprotsyn.setOnMouseExited( e -> { protsyn.setVisible( false ); } );

        types.setOnMouseEntered( e -> { typesin.setVisible( true ); } );
        types.setOnMouseExited( e -> { typesin.setVisible( false ); } );

        rnaexp.setOnMouseEntered( e -> { inrnaexp.setVisible( true ); } );
        rnaexp.setOnMouseExited( e -> { inrnaexp.setVisible( false ); } );

        protexout.setOnMouseEntered( e -> { protex.setVisible( true ); } );
        protexout.setOnMouseExited( e -> { protex.setVisible( false ); } );

        return root;
    }
}
