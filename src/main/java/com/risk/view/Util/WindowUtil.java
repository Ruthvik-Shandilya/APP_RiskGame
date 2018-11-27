package com.risk.view.Util;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Dice;
import com.risk.model.Player;
import com.risk.services.StartUpPhase;
import com.risk.services.saveload.SaveData;
import com.risk.view.controller.DiceController;
import com.risk.view.controller.GamePlayController;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * This class provides all the game utilities as to exit window,enable and disable button.
 *
 * @author Palash Jain
 */
public class WindowUtil extends Observable implements Observer{

    public WindowUtil(GamePlayController gamePlayController) {
        this.addObserver(gamePlayController);
    }

//    public WindowUtil(Player player){
//        player.addObserver(this);
//    }

    public WindowUtil(DiceController diceController){
        diceController.addObserver(this);
    }

    /**
     * This method is used to close the window.
     *
     * @param button button
     */
    public static void exitWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is used to disable a complete pane.
     *
     * @param pane window
     */
    public static void disablePane(Pane pane) {
        pane.setVisible(false);
    }

    /**
     * This method is used to enable a complete pane.
     *
     * @param pane window
     */
    public static void enablePane(Pane pane) {
        pane.setVisible(true);
    }

    /**
     * This method provides a warning window with a message.
     *
     * @param head        Head of the window
     * @param title       Title of the window
     * @param information warning message
     */
    public static void popUpWindow(String head, String title, String information) {
        Alert popUp = new Alert(AlertType.INFORMATION);
        popUp.setHeaderText(head);
        popUp.setTitle(title);
        popUp.setContentText(information);
        popUp.showAndWait();
    }

    /**
     * This method helps to hide a button in the pane.
     *
     * @param controls Any number of inputs
     */
    public static void hideButtonControl(Control... controls) {
        for (Control control : controls) {
            control.setVisible(false);
        }
    }

    /**
     * This method helps to disable button in the pane.
     *
     * @param controls Any number of inputs
     */
    public static void disableButtonControl(Control... controls) {
        for (Control control : controls) {
            control.setDisable(true);
        }
    }

    /**
     * This method helps to enable button in the pane.
     *
     * @param controls Any number of inputs
     */
    public static void enableButtonControl(Control... controls) {
        for (Control control : controls) {
            control.setDisable(false);
        }
    }

    /**
     * Method for making checkbox visible.
     *
     * @param controls
     */

    public static void showCheckBox(Control... controls) {
        for (Control control : controls) {
            control.setVisible(true);
        }
    }

    /**
     * This method helps to create a new pane to include the continents
     * and its countries and show the ownership of that particular country.
     *
     * @param continent Continent Object
     * @return pane
     */
    public static TitledPane createNewPane(Continent continent) {
        VBox hbox = new VBox();
        for (Country country : continent.getListOfCountries()) {
            Label label1 = new Label();
            if (country.getPlayer() != null) {
                label1.setText(
                        country.getName() + ":-" + country.getNoOfArmies() + "-" + country.getPlayer().getName());
            } else {
                label1.setText(country.getName() + ":-" + country.getNoOfArmies());
            }
            hbox.getChildren().add(label1);
        }
        TitledPane pane = new TitledPane(continent.getName(), hbox);

        return pane;
    }

    /**
     * This method helps to select a map file by providing its extension
     *
     * @return Selected file
     */
    public static File showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Map File");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile;
    }

    /**
     * This method helps to provide checkboxes using Java FXML
     *
     * @param checkBoxes checkBoxes
     */
    public static void unCheckBoxes(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setText("");
            checkBox.setSelected(false);
        }
    }

    public static void checkCheckBoxes(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setText("");
            checkBox.setIndeterminate(false);
            checkBox.setSelected(true);
        }
    }

    public static void selectVisibleDice(Control... controls) {
        for (Control control : controls) {
            if (control.isVisible()) {
                ((CheckBox) control).setSelected(true);
            }
        }
    }

    /**
     * This method helps in taking user input by providing a input box
     *
     * @return The value provided by the user
     */
    public static String userInput() {
        TextInputDialog inputBox = new TextInputDialog();
        String numberOfArmies = "0";
        inputBox.setTitle("User Input");
        inputBox.setHeaderText("Please enter number of armies.");
        Optional<String> inputFromUser = inputBox.showAndWait();
        if (inputFromUser.isPresent()) {
            numberOfArmies = inputFromUser.get();
        }
        return numberOfArmies;
    }




    @Override
    public void update(Observable o, Object arg) {
        String information = (String) arg;
//        TextArea terminalWindow;
//        if(o instanceof  GamePlayController)
//            terminalWindow = ((GamePlayController) o).getTerminalWindow();
//        else if(o instanceof Player)
//            terminalWindow = ((Player) o).getTerminalWindow();
//        else
//            terminalWindow = ((DiceController) o).getTerminalWindow();

        if(!(information.equals("Attack")  || information.equals("Fortification") || information.equals("noFortificationMove")
            || information.equals("FirstAttack") || information.equals("placeArmyOnCountry") || information.equals("checkIfFortificationPhaseValid")
                || information.equals("rollDiceComplete"))){
         setChanged();
         notifyObservers(information);
        }


    }
}
