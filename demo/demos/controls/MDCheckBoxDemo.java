package demos.controls;

import de.derpeterson.materialdesign.controls.MDCheckBox;
import de.derpeterson.materialdesign.css.CssResources;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MDCheckBoxDemo extends Application {

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
		
		VBox mdCheckBoxBox = new VBox();
		mdCheckBoxBox.setSpacing(10);
		Text mdCheckBoxsText = new Text("MD CheckBoxes");
		mdCheckBoxsText.getStyleClass().add("text-header");
		mdCheckBoxBox.getChildren().add(mdCheckBoxsText);
		HBox componentsBox = new HBox();	
		componentsBox.setAlignment(Pos.CENTER);
		componentsBox.setSpacing(20);
		MDCheckBox mdCheckBox = new MDCheckBox("CHECKBOX");
		componentsBox.getChildren().add(mdCheckBox);
		MDCheckBox coloredMDCheckBox = new MDCheckBox("COLORED");
		coloredMDCheckBox.getStyleClass().add("md-check-box-colored");
		componentsBox.getChildren().add(coloredMDCheckBox);
		MDCheckBox disabledMDCheckBox = new MDCheckBox("DISABLE");
		disabledMDCheckBox.setDisable(true);
		componentsBox.getChildren().add(disabledMDCheckBox);

		mdCheckBoxBox.getChildren().add(componentsBox);
		mainBox.getChildren().add(mdCheckBoxBox);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(mainBox);
		Scene scene = new Scene(pane, 500, 500, true, SceneAntialiasing.BALANCED);
		scene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		scene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		scene.getStylesheets().add(MDCheckBoxDemo.class.getResource("../resources/css/demo.css").toExternalForm());
		stage.setTitle("MDCheckBox Demonstration");
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
