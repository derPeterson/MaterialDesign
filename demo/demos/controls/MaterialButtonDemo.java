package demos.controls;

import de.derpeterson.materialdesign.controls.MaterialButton;
import de.derpeterson.materialdesign.css.CssResources;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MaterialButtonDemo extends Application {

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
		MaterialButton flatMaterialButton = new MaterialButton("BUTTON");
		flatComponentsBox.getChildren().add(flatMaterialButton);
		MaterialButton flatColoredMaterialButton = new MaterialButton("COLORED");
		flatColoredMaterialButton.getStyleClass().add("material-button-flat-colored");
		flatComponentsBox.getChildren().add(flatColoredMaterialButton);
		MaterialButton flatDisabledMaterialButton = new MaterialButton("DISABLE");
		flatDisabledMaterialButton.setDisable(true);
		flatComponentsBox.getChildren().add(flatDisabledMaterialButton);

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
		MaterialButton raisedMaterialButton = new MaterialButton("BUTTON");
		raisedMaterialButton.getStyleClass().add("material-button-raised");
		raisedComponentsBox.getChildren().add(raisedMaterialButton);
		MaterialButton raisedColoredMaterialButton = new MaterialButton("COLORED");
		raisedColoredMaterialButton.getStyleClass().add("material-button-raised-colored");
		raisedComponentsBox.getChildren().add(raisedColoredMaterialButton);
		MaterialButton raisedDisabledMaterialButton = new MaterialButton("DISABLE");
		raisedDisabledMaterialButton.getStyleClass().add("material-button-raised");
		raisedDisabledMaterialButton.setDisable(true);
		raisedComponentsBox.getChildren().add(raisedDisabledMaterialButton);

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
		MaterialButton customMaterialButton = new MaterialButton("Custom Rippler Fill");
		customMaterialButton.getStyleClass().add("material-button-custom-rippler-fill");
		customComponentsBox.getChildren().add(customMaterialButton);

		customButtonsBox.getChildren().add(customComponentsBox);
		mainBox.getChildren().add(customButtonsBox);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(mainBox);
		Scene scene = new Scene(pane, 500, 500, true, SceneAntialiasing.BALANCED);
		scene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		scene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		scene.getStylesheets().add(MaterialButtonDemo.class.getResource("../resources/css/demo.css").toExternalForm());
		stage.setTitle("MaterialTextField Demonstration");
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
