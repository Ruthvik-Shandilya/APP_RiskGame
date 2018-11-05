package com.risk.services.controller.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
	
	public static void userInfo(String head, String title, String information){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(head);
		alert.setTitle(title);
		alert.setContentText(information);
		alert.showAndWait();
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
	
	
}
