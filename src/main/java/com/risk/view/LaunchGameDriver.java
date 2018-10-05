package com.risk.view;

import java.io.File;

import com.risk.services.MapIO;
import com.risk.services.MapValidate;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Launches the main window and provides the view for the user, either to load
 * or create a new map.
 *
 * @author karandeep
 * @author ruthvik
 */

public class LaunchGameDriver extends Application {

	/**
	 * Declare buttons for loading and creating a map file.
	 */
	Button loadMapButton, createMapButton;

	MapIO readMap;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Conquest - World (Build 1)");

		Label gameLabel = new Label("Risk Game");
		gameLabel.setStyle("-fx-font-weight: bold");
		gameLabel.setFont(new Font("Arial", 30));
		gameLabel.setTextFill(Color.RED);

		Label optionLabel = new Label("Please select below options");
		optionLabel.setFont(new Font("Arial", 20));
		optionLabel.setTextFill(Color.RED);

		loadMapButton = new Button();
		loadMapButton.setText("Load Map File");
		loadMapButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a Map File");
			fileChooser.getExtensionFilters()
					.add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				loadMapButton.getScene().getWindow().hide();
				String fileName = selectedFile.getAbsolutePath();
				System.out.println("File location: " + fileName);
				MapValidate mapValidate = new MapValidate();
				if (mapValidate.validateMapFile(fileName)) {
					// readMap = new MapIO(mapValidate);
					// readMap.readFile();
					// new MapLoader().validateAndLoadMapFile(fileName);
				}
			}
		});

		createMapButton = new Button();
		createMapButton.setText("Create a New Map");

		loadMapButton.setMaxWidth(150);
		createMapButton.setMaxWidth(150);

		VBox layout = new VBox();
		layout.setStyle("-fx-background-color: black");
		layout.setSpacing(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(gameLabel, optionLabel, loadMapButton, createMapButton);

		Scene scene = new Scene(layout, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
