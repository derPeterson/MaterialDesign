package demos.controls;

import de.derpeterson.materialdesign.controls.MaterialButton;
import de.derpeterson.materialdesign.controls.MaterialCheckBox;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

public class MaterialCheckBoxDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setMaxWidth(300);
		mainBox.setSpacing(30);
		
		VBox javaCheckBoxBox = new VBox();
		javaCheckBoxBox.setSpacing(10);
		Text javaCheckBoxsText = new Text("Java CheckBoxes");
		javaCheckBoxsText.getStyleClass().add("text-header");
		javaCheckBoxBox.getChildren().add(javaCheckBoxsText);
		HBox javaComponentsBox = new HBox();	
		javaComponentsBox.setAlignment(Pos.CENTER);
		javaComponentsBox.setSpacing(20);
		CheckBox javaCheckBox = new CheckBox("CHECKBOX");
		javaComponentsBox.getChildren().add(javaCheckBox);
		CheckBox javaColoredCheckBox = new CheckBox("COLORED");
		javaColoredCheckBox.getStyleClass().add("java-check-box-colored");
		javaComponentsBox.getChildren().add(javaColoredCheckBox);
		CheckBox javaDisableCheckBox = new CheckBox("DISABLE");
		javaDisableCheckBox.setDisable(true);
		javaComponentsBox.getChildren().add(javaDisableCheckBox);
		
		javaCheckBoxBox.getChildren().add(javaComponentsBox);
		mainBox.getChildren().add(javaCheckBoxBox);
		
		VBox materialCheckBoxBox = new VBox();
		materialCheckBoxBox.setSpacing(10);
		Text materialCheckBoxsText = new Text("Material CheckBoxes");
		materialCheckBoxsText.getStyleClass().add("text-header");
		materialCheckBoxBox.getChildren().add(materialCheckBoxsText);
		HBox materialComponentsBox = new HBox();	
		materialComponentsBox.setAlignment(Pos.CENTER);
		materialComponentsBox.setSpacing(20);
		MaterialCheckBox materialCheckBox = new MaterialCheckBox("CHECKBOX");
		materialComponentsBox.getChildren().add(materialCheckBox);
		MaterialCheckBox coloredMaterialCheckBox = new MaterialCheckBox("COLORED");
		coloredMaterialCheckBox.getStyleClass().add("material-check-box-colored");
		materialComponentsBox.getChildren().add(coloredMaterialCheckBox);
		MaterialCheckBox disabledMaterialCheckBox = new MaterialCheckBox("DISABLE");
		disabledMaterialCheckBox.setDisable(true);
		materialComponentsBox.getChildren().add(disabledMaterialCheckBox);

		materialCheckBoxBox.getChildren().add(materialComponentsBox);
		mainBox.getChildren().add(materialCheckBoxBox);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(mainBox);
		Scene scene = new Scene(pane, 500, 500, true, SceneAntialiasing.BALANCED);
		scene.getStylesheets().add(MaterialCheckBoxDemo.class.getResource("../resources/css/fonts.css").toExternalForm());
		scene.getStylesheets().add(MaterialCheckBoxDemo.class.getResource("../resources/css/demo.css").toExternalForm());
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
