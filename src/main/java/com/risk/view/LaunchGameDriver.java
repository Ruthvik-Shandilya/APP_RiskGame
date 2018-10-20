package com.risk.view;

import java.io.File;
import java.util.Scanner;

import com.risk.services.MapIO;
import com.risk.services.MapValidate;
import com.risk.services.MapEditor;
import com.risk.services.StartUpPhase;
import com.risk.services.gameplay.ReinforcementPhase;
import com.risk.services.gameplay.FortificationPhase;
import com.risk.services.gameplay.RoundRobin;
import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * Launches the Game and provides the main window and the view for the user, 
 * either to load or create a new map.
 *
 * @author Karandeep Singh
 * @author Ruthvik Shandilya
 */
public class LaunchGameDriver extends Application implements EventHandler<ActionEvent>{

	/**
	 * Declare buttons for loading and creating a map file.
	 */
	Button loadMapButton, createMapButton;

	/**
	 * readMap a MapIO Object
	 */
	MapIO readMap;

	public static boolean status = false;

	/**
	 * The Main Method which Launches the Game and drives by providing
	 * the options. 
	 *  
	 * @param args main arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * 
	 * The main entry point for all JavaFX applications.The start method is called after the
	 *  init method has returned,and after the system is ready for the application to begin running. 
	 *  NOTE: This method is called on the JavaFX Application Thread.
	 *  
	 *  @param primaryStage the primary stage for this application, onto which the application scene 
	 *  can be set. The primary stage will be embedded in the browser if the application was launched
	 *  as an applet.Applications may create other stages, if needed, but they will not be primary 
	 *  stages and will not be embedded in the browser.
	 *  
	 *  
	 *  
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
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
		loadMapButton.setOnAction(this);

		createMapButton = new Button();
		createMapButton.setText("Create a New Map");
		createMapButton.setOnAction(this);

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

	/**
	 * Invoked when a specific event of the type for which this handler
	 * is registered happens.
	 * 
	 * @param  event the event which occurred
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==loadMapButton) {
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
					readMap = new MapIO(mapValidate);
					new MapEditor(readMap.readFile()).editExistingMap();
				}
			}
		}
		else {
			createMapButton.getScene().getWindow().hide();
			MapEditor mapEditor = new MapEditor();
			mapEditor.createNewMap();
			readMap = mapEditor.getMapIO();
		}

		if(status) {
			System.out.println("Do you want to play the game?(true or false)");
			Scanner scan = new Scanner(System.in);
			if(Boolean.parseBoolean(scan.nextLine())) {
				gamePlay(readMap);
			}
		}
	}

	/**
	 * This gamePlay method begins the startup phase and depending on the no of the turns,
	 * enters into the reinforcement phase and assigns the armies based on the input value.
	 * 
	 * @param mapIO Object
	 * 
	 */
	private void gamePlay(MapIO mapIO) {
		Scanner scan = new Scanner(System.in);

		System.out.println("\nBeginning Startup Phase.\n");
		StartUpPhase startUpPhase = new StartUpPhase(mapIO);
		startUpPhase.countryAllocation();
		startUpPhase.armyAllocationToPlayers();
		startUpPhase.initialArmyAllocationToCountries();
		startUpPhase.balanceArmyAllocationToCountries();

		int turn = 1;
		RoundRobin roundRobin = new RoundRobin(startUpPhase.getListOfPlayers());

		while(turn <= 4) {
			Player player = roundRobin.next();
			System.out.println("Beginning Reinforcement phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Reinforcement phase? (Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				Continent continent = player.getMyCountries().get(player.getMyCountries().size()-1).getPartOfContinent();
				int balanceArmyCount = (new ReinforcementPhase()).findNoOfArmies(player, continent);
				System.out.println("Armies received for reinforcement: " + balanceArmyCount);
				player.setArmyCount(player.getArmyCount() + balanceArmyCount);
				for(Country country: player.getMyCountries()) {
					if(player.getArmyCount()>0) {
						System.out.println("Number of armies currently assigned to country " + country.getName() + " :" + country.getNoOfArmies());
						System.out.println("Total number of armies available:" + player.getArmyCount());
						System.out.println("Enter number of armies to assign to country " + country.getName() + " :");
						try {
							int assignArmies = Integer.parseInt(scan.nextLine());
							player.addArmiesToCountry(country, assignArmies);
						}catch(NumberFormatException e) {
							System.out.println("Invalid number of armies.");
						}
					}
					else {
						System.out.println("You do not have sufficient number of armies available.");
						break;
					}
				}
			}

			System.out.println("Beginning Fortification phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Fortification phase? (Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				if(player.getMyCountries().size()>=2) {
					boolean flag = true;
					String giverCountry = "";
					String receiverCountry = "";

					System.out.println("List of countries owned by you and current army assigned are: ");
					for(Country ownedCountry: player.getMyCountries()) {
						System.out.println(ownedCountry.getName() + ":" + ownedCountry.getNoOfArmies());
					}
					do {
						flag=true;
						System.out.println("Enter the name of country from which you want to move some armies :");
						giverCountry = scan.nextLine();
						System.out.println("Enter the name of country to which you want to move some armies, from country " + giverCountry);
						receiverCountry = scan.nextLine();
						if(!mapIO.getMapGraph().getCountrySet().containsKey(giverCountry.trim()) || !mapIO.getMapGraph().getCountrySet().containsKey(receiverCountry.trim())) {
							flag=false;
							System.out.println("Please enter correct country name.");
						}
						Country givingCountry = mapIO.getMapGraph().getCountrySet().get(giverCountry.trim());
						Country receviningCountry = mapIO.getMapGraph().getCountrySet().get(giverCountry.trim());
						if(player.getMyCountries().contains(givingCountry) && player.getMyCountries().contains(receviningCountry)){
							flag = true;
						}
						else {
							System.out.println("Player does not own these country, please enter country names again");
							flag = false;
						}

					}while(flag==false);


					int countOfArmies = 0;
					do {
						flag=true;
						System.out.println("Enter the number of armies to move from " + giverCountry + " to " + receiverCountry);
						try {
							countOfArmies = Integer.parseInt(scan.nextLine());
							if(countOfArmies > mapIO.getMapGraph().getCountrySet().get(giverCountry).getNoOfArmies()) {
								System.out.println("Sufficient number of armies is not available.");
								flag=false;
							}

						}catch(NumberFormatException e) {
							System.out.println("Invalid number of armies.");
						}
					}while(flag==false);

					(new FortificationPhase()).moveArmies(mapIO.getMapGraph().getCountrySet().get(giverCountry), mapIO.getMapGraph().getCountrySet().get(receiverCountry), countOfArmies);
				}
				else {
					System.out.println("You don't have sufficient number of countries to choose from.");
				}
			}
			turn++;
		}
	}
}
