package demos.controls;


import de.derpeterson.materialdesign.controls.MaterialButton;
import de.derpeterson.materialdesign.controls.MaterialNotification;
import de.derpeterson.materialdesign.controls.MaterialNotification.NotificationType;
import de.derpeterson.materialdesign.css.CssResources;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MaterialNotificationDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setSpacing(30);
		
		Text materialNotificationText = new Text("Material Notification");
		materialNotificationText.getStyleClass().add("text-header");		
		mainBox.getChildren().add(materialNotificationText);	
		
		MaterialButton buttonNotification = new MaterialButton("Click!");
		buttonNotification.getStyleClass().add("material-button-flat-colored");
		buttonNotification.setOnMouseClicked(e -> {
			new MaterialNotification(NotificationType.INFO);
		});
		mainBox.getChildren().add(buttonNotification);		
		
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
