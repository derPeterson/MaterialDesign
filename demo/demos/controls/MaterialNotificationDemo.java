package demos.controls;

import de.derpeterson.materialdesign.controls.MaterialButton;
import de.derpeterson.materialdesign.controls.MaterialNotification;
import de.derpeterson.materialdesign.controls.MaterialNotification.NotificationType;
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

public class MaterialNotificationDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setSpacing(30);
		
		Text materialNotificationText = new Text("Material Notification");
		materialNotificationText.getStyleClass().add("text-header");		
		mainBox.getChildren().add(materialNotificationText);	
		
		MaterialButton buttonInfoNotification = new MaterialButton("Info");
		buttonInfoNotification.getStyleClass().add("material-button-flat-colored");
		buttonInfoNotification.setOnMouseClicked(e -> {
			MaterialNotification materialNotification = new MaterialNotification(NotificationType.INFO);
			materialNotification.setTitle("Information..");
			materialNotification.setMessage("Just a little information...");
			materialNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonInfoNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonInfoNotification);
		
		MaterialButton buttonSuccessNotification = new MaterialButton("Success");
		buttonSuccessNotification.getStyleClass().add("material-button-flat-colored");
		buttonSuccessNotification.setOnMouseClicked(e -> {
			MaterialNotification materialNotification = new MaterialNotification(NotificationType.SUCCESS);
			materialNotification.setTitle("Success..");
			materialNotification.setMessage("Everything is good!");
			materialNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonSuccessNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonSuccessNotification);		
		
		MaterialButton buttonWarningNotification = new MaterialButton("Warning");
		buttonWarningNotification.getStyleClass().add("material-button-flat-colored");
		buttonWarningNotification.setOnMouseClicked(e -> {
			MaterialNotification materialNotification = new MaterialNotification(NotificationType.WARNING);
			materialNotification.setTitle("Warning..");
			materialNotification.setMessage("A little warning...");
			materialNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonWarningNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonWarningNotification);		
		
		MaterialButton buttonErrorNotification = new MaterialButton("Error");
		buttonErrorNotification.getStyleClass().add("material-button-flat-colored");
		buttonErrorNotification.setOnMouseClicked(e -> {
			MaterialNotification materialNotification = new MaterialNotification(NotificationType.ERROR);
			materialNotification.setTitle("Error..");
			materialNotification.setMessage("Something has gone wrong!");
			materialNotification.showAndDismiss(Duration.seconds(5));
		
		});
		buttonErrorNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonErrorNotification);		
		
		MaterialButton buttonCustomNotification = new MaterialButton("Custom");
		buttonCustomNotification.getStyleClass().add("material-button-flat-colored");
		buttonCustomNotification.setOnMouseClicked(e -> {
			MaterialNotification materialNotification = new MaterialNotification(NotificationType.CUSTOM);
			materialNotification.setTitle("Max Mustermann");
			materialNotification.addMessage("Testing a custom notification");
			Label label = new Label("Good or not?");
			materialNotification.addMessage(label);
			label.setStyle("-fx-font-family: 'Roboto Light';");
			materialNotification.setImage(new Image(MaterialNotificationDemo.class.getResource("../resources/images/logo_gmail_48.png").toExternalForm()));
			materialNotification.showAndDismiss(Duration.seconds(5));
		});
		buttonCustomNotification.setStyle("-fx-pref-width: 80px;");
		mainBox.getChildren().add(buttonCustomNotification);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(mainBox);
		Scene scene = new Scene(pane, 500, 500);
		scene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		scene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		scene.getStylesheets().add(MaterialNotificationDemo.class.getResource("../resources/css/demo.css").toExternalForm());
		stage.setTitle("Dialog Demonstration");
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
