package com.risk.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class WelcomeRiskGame extends Application {

    Button loadMap, createMap;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Conquest - World (Build 1)");


        Label label1 = new Label("Risk Game");
        label1.setStyle("-fx-font-weight: bold");
        label1.setFont(new Font("Arial",30));
        label1.setTextFill(Color.RED);

        Label label2 = new Label("Please select below options");
        label2.setFont(new Font("Arial",20));
        label2.setTextFill(Color.RED);


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
        layout.getChildren().addAll(label1, label2, loadMap,createMap);

        Scene scene = new Scene(layout,400,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
