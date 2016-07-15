package demos.controls;

import de.derpeterson.materialdesign.controls.MDButton;
import de.derpeterson.materialdesign.controls.MDNotification;
import de.derpeterson.materialdesign.controls.MDNotification.NotificationType;
import de.derpeterson.materialdesign.css.CssResources;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MDNotificationDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setSpacing(30);
		
		Text mdNotificationText = new Text("MD Notification");
		mdNotificationText.getStyleClass().add("text-header");		
		mainBox.getChildren().add(mdNotificationText);	
		
		MDButton buttonInfoNotification = new MDButton("Info");
		buttonInfoNotification.getStyleClass().add("md-button-flat-colored");
		buttonInfoNotification.setOnMouseClicked(e -> {
			MDNotification mdNotification = new MDNotification(NotificationType.INFO);
			mdNotification.setTitle("Information..");
			mdNotification.setMessage("Just a little information...");
			mdNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonInfoNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonInfoNotification);
		
		MDButton buttonSuccessNotification = new MDButton("Success");
		buttonSuccessNotification.getStyleClass().add("md-button-flat-colored");
		buttonSuccessNotification.setOnMouseClicked(e -> {
			MDNotification mdNotification = new MDNotification(NotificationType.SUCCESS);
			mdNotification.setTitle("Success..");
			mdNotification.setMessage("Everything is good!");
			mdNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonSuccessNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonSuccessNotification);		
		
		MDButton buttonWarningNotification = new MDButton("Warning");
		buttonWarningNotification.getStyleClass().add("md-button-flat-colored");
		buttonWarningNotification.setOnMouseClicked(e -> {
			MDNotification mdNotification = new MDNotification(NotificationType.WARNING);
			mdNotification.setTitle("Warning..");
			mdNotification.setMessage("A little warning...");
			mdNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonWarningNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonWarningNotification);		
		
		MDButton buttonErrorNotification = new MDButton("Error");
		buttonErrorNotification.getStyleClass().add("md-button-flat-colored");
		buttonErrorNotification.setOnMouseClicked(e -> {
			MDNotification mdNotification = new MDNotification(NotificationType.ERROR);
			mdNotification.setTitle("Error..");
			mdNotification.setMessage("Something has gone wrong!");
			mdNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonErrorNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonErrorNotification);		
		
		MDButton buttonCustomNotification = new MDButton("Custom");
		buttonCustomNotification.getStyleClass().add("md-button-flat-colored");
		buttonCustomNotification.setOnMouseClicked(e -> {
			MDNotification mdNotification = new MDNotification(NotificationType.CUSTOM);
			mdNotification.setTitle("Max Mustermann");
			mdNotification.addMessage("Testing a custom notification");
			Label label = new Label("Good or not?");
			mdNotification.addMessage(label);
			label.setStyle("-fx-font-family: 'Roboto Light';");
			mdNotification.setImage(new Image(MDNotificationDemo.class.getResource("../resources/images/logo_gmail_48.png").toExternalForm()));
			mdNotification.showAndDismiss(Duration.seconds(5));
		});
		buttonCustomNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonCustomNotification);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(mainBox);
		Scene scene = new Scene(pane, 500, 500);
		scene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		scene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		scene.getStylesheets().add(MDNotificationDemo.class.getResource("../resources/css/demo.css").toExternalForm());
		stage.setTitle("MDNotification Demonstration");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		System.setProperty("prism.lcdtext", "false");
		System.setProperty("prism.text", "t2k");
		launch(args);
	}

}
