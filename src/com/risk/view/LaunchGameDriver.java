package com.risk.view;

import com.risk.model.Continent;
import com.risk.services.MapEditor;
import com.risk.services.MapValidate;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;



public class LaunchGameDriver extends Application {

    Button loadMap, createMap;

    public static void main(String[] args) {

        System.out.println("sdc");

        launch(args);

//        System.out.println("sdc");
//
//        Continent n = new Continent("anem", 5);
//        Continent m = new Continent("anem", 6);
//
//        MapEditor me = new MapEditor();
//        me.createNewMap();

        MapValidate mv = new MapValidate();
        mv.validateMapFile("C:\\Users\\Palash\\Desktop\\Africa.map");

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Conquest - World (Build 1)");


        Label gameLabel = new Label("Risk Game");
        gameLabel.setStyle("-fx-font-weight: bold");
        gameLabel.setFont(new Font("Arial",30));
        gameLabel.setTextFill(Color.RED);

        Label optionLabel = new Label("Please select below options");
        optionLabel.setFont(new Font("Arial",20));
        optionLabel.setTextFill(Color.RED);

        loadMap = new Button();
        loadMap.setText("Load Map File");

        createMap = new Button();
        createMap.setText("Create a New Map");

        loadMap.setMaxWidth(150);
        createMap.setMaxWidth(150);

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: black");
        layout.setSpacing(20);
        //layout.setPadding(new Insets(0,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(gameLabel, optionLabel, loadMap,createMap);

        Scene scene = new Scene(layout,400,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
