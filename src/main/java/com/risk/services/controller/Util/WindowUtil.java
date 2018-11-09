package com.risk.services.controller.Util;

import com.risk.model.Continent;
import com.risk.model.Country;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class WindowUtil {

    public static void exitWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public static void disablePane(Pane pane) {
        pane.setVisible(false);
    }

    public static void enablePane(Pane pane) {
        pane.setVisible(true);
    }

    public static void popUpWindow(String head, String title, String information) {
        Alert popUp = new Alert(AlertType.INFORMATION);
        popUp.setHeaderText(head);
        popUp.setTitle(title);
        popUp.setContentText(information);
        popUp.showAndWait();
    }

    public static void hideButtonControl(Control... controls) {
        for (Control control : controls) {
            control.setVisible(false);
        }
    }

    public static void disableButtonControl(Control... controls) {
        for (Control control : controls) {
            control.setDisable(true);
        }
    }

    public static void enableButtonControl(Control... controls) {
        for (Control control : controls) {
            control.setDisable(false);
        }
    }

    public static void showCheckBox(Control... controls) {
        for (Control control : controls) {
            control.setVisible(true);
        }
    }

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



    public static File showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Map File");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile;
    }

    public static void unCheckBoxes(CheckBox... checkBoxes) {
        for (CheckBox checkBox: checkBoxes) {
            checkBox.setText("");
            checkBox.setSelected(false);
        }
    }

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

    public static void updateterminalWindow(String information, TextArea terminalWindow) {

        Platform.runLater( new Runnable(){
            @Override public void run() {
                terminalWindow.appendText(information);
            }
        });

    }

    public static void updateterminalWindow(String information) {

        Platform.runLater( new Runnable(){
            @Override public void run() {
                System.out.println(information);
            }
        });



    }



}
