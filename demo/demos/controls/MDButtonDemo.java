package demos.controls;

import de.derpeterson.materialdesign.controls.MDButton;
import de.derpeterson.materialdesign.css.CssResources;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MDButtonDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setMaxWidth(300);
		mainBox.setSpacing(30);
		
		VBox javaButtonsBox = new VBox();
		javaButtonsBox.setSpacing(10);
		Text javaButtonsText = new Text("Java Buttons");
		javaButtonsText.getStyleClass().add("text-header");
		javaButtonsBox.getChildren().add(javaButtonsText);
		HBox javaComponentsBox = new HBox();	
		javaComponentsBox.setAlignment(Pos.CENTER);
		javaComponentsBox.setSpacing(20);
		Button javaButton = new Button("BUTTON");
		javaComponentsBox.getChildren().add(javaButton);
		Button javaColoredButton = new Button("COLORED");
		javaColoredButton.getStyleClass().add("java-button-colored");
		javaComponentsBox.getChildren().add(javaColoredButton);
		Button javaDisableButton = new Button("DISABLE");
		javaDisableButton.setDisable(true);
		javaComponentsBox.getChildren().add(javaDisableButton);
		
		javaButtonsBox.getChildren().add(javaComponentsBox);
		mainBox.getChildren().add(javaButtonsBox);
		
		VBox flatButtonsBox = new VBox();
		flatButtonsBox.setSpacing(10);
		Text flatButtonsText = new Text("Flat Buttons");
		flatButtonsText.getStyleClass().add("text-header");
		flatButtonsBox.getChildren().add(flatButtonsText);
		HBox flatComponentsBox = new HBox();	
		flatComponentsBox.setAlignment(Pos.CENTER);
		flatComponentsBox.setSpacing(20);
		MDButton flatMDButton = new MDButton("BUTTON");
		flatComponentsBox.getChildren().add(flatMDButton);
		MDButton flatColoredMDButton = new MDButton("COLORED");
		flatColoredMDButton.getStyleClass().add("md-button-flat-colored");
		flatComponentsBox.getChildren().add(flatColoredMDButton);
		MDButton flatDisabledMDButton = new MDButton("DISABLE");
		flatDisabledMDButton.setDisable(true);
		flatComponentsBox.getChildren().add(flatDisabledMDButton);

		flatButtonsBox.getChildren().add(flatComponentsBox);
		mainBox.getChildren().add(flatButtonsBox);
		
		VBox raisedButtonsBox = new VBox();
		raisedButtonsBox.setSpacing(10);
		Text raisedButtonsText = new Text("Raised Buttons");
		raisedButtonsText.getStyleClass().add("text-header");
		raisedButtonsBox.getChildren().add(raisedButtonsText);
		HBox raisedComponentsBox = new HBox();	
		raisedComponentsBox.setAlignment(Pos.CENTER);
		raisedComponentsBox.setSpacing(20);
		MDButton raisedMDButton = new MDButton("BUTTON");
		raisedMDButton.getStyleClass().add("md-button-raised");
		raisedComponentsBox.getChildren().add(raisedMDButton);
		MDButton raisedColoredMDButton = new MDButton("COLORED");
		raisedColoredMDButton.getStyleClass().add("md-button-raised-colored");
		raisedComponentsBox.getChildren().add(raisedColoredMDButton);
		MDButton raisedDisabledMDButton = new MDButton("DISABLE");
		raisedDisabledMDButton.getStyleClass().add("md-button-raised");
		raisedDisabledMDButton.setDisable(true);
		raisedComponentsBox.getChildren().add(raisedDisabledMDButton);

		raisedButtonsBox.getChildren().add(raisedComponentsBox);
		mainBox.getChildren().add(raisedButtonsBox);
		
		VBox customButtonsBox = new VBox();
		customButtonsBox.setSpacing(10);
		Text customButtonsText = new Text("Custom Ripple Fill Button");
		customButtonsText.getStyleClass().add("text-header");
		customButtonsBox.getChildren().add(customButtonsText);
		HBox customComponentsBox = new HBox();	
		customComponentsBox.setAlignment(Pos.CENTER);
		customComponentsBox.setSpacing(20);
		MDButton customMDButton = new MDButton("Custom Rippler Fill");
		customMDButton.getStyleClass().add("md-button-custom-rippler-fill");
		customComponentsBox.getChildren().add(customMDButton);

		customButtonsBox.getChildren().add(customComponentsBox);
		mainBox.getChildren().add(customButtonsBox);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(mainBox);
		Scene scene = new Scene(pane, 500, 500, true, SceneAntialiasing.BALANCED);
		scene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		scene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		scene.getStylesheets().add(MDButtonDemo.class.getResource("../resources/css/demo.css").toExternalForm());
		stage.setTitle("MDButtond Demonstration");
		stage.setResizable(true);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		System.setProperty("prism.lcdtext", "false");
		System.setProperty("prism.text", "t2k");
		launch(args);
	}

}
