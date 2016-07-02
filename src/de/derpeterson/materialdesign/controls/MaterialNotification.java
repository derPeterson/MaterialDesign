package de.derpeterson.materialdesign.controls;

import de.derpeterson.materialdesign.css.CssResources;
import de.derpeterson.materialdesign.images.ImageResources;
import demos.controls.MaterialNotificationDemo;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MaterialNotification {	
	
	private static final double maxWidth = 500;
	private static final double minWidth = 360;
	private static final double minHeight = 60;
	
	public MaterialNotification() {
		Stage notificationStage = new Stage(StageStyle.UNDECORATED);
		notificationStage.setResizable(false);
		notificationStage.setAlwaysOnTop(true);
		
		BorderPane notificationPane = new BorderPane();	
		notificationPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#212121"), CornerRadii.EMPTY, new Insets(0))));
		notificationPane.setBorder(new Border(new BorderStroke(Color.valueOf("#424242"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		notificationPane.setMaxWidth(MaterialNotification.maxWidth);
		notificationPane.setMinWidth(MaterialNotification.minWidth);
		notificationPane.setMinHeight(MaterialNotification.minHeight);
		
		HBox leftBox = new HBox();
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.setPadding(new Insets(12, 15, 0, 15));
		ImageView notificationImage = new ImageView(new Image(ImageResources.class.getResource("info_36.png").toExternalForm()));
		leftBox.getChildren().add(notificationImage);
		
		notificationPane.setLeft(leftBox);
		
		VBox bodyBox = new VBox();
		bodyBox.setAlignment(Pos.CENTER_LEFT);
		Label headerLabel = new Label("YourProgramm is Running!");
		headerLabel.setStyle("-fx-font-weight: bold;-fx-text-fill: WHITE;");
		headerLabel.setWrapText(true);
		bodyBox.getChildren().add(headerLabel);
		
		Label bodyLabel = new Label("YourProgramm Started");
		bodyLabel.setStyle("-fx-text-fill: #9E9E9E;");
		bodyLabel.setWrapText(true);
		bodyBox.getChildren().add(bodyLabel);
		
		notificationPane.setCenter(bodyBox);
		
		VBox rightBox = new VBox();
		rightBox.setPadding(new Insets(2));
		MaterialButton closeButton = new MaterialButton();
		closeButton.setGraphic(new ImageView(new Image(ImageResources.class.getResource("close_16.png").toExternalForm())));
		closeButton.setStyle("-fx-padding: 5px;");
		closeButton.setRipplerFill(Color.WHITE);
		rightBox.getChildren().add(closeButton);
		
		notificationPane.setRight(rightBox);

		Scene notificationScene = new Scene(notificationPane);
		notificationScene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		notificationScene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		notificationStage.setScene(notificationScene);
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		notificationStage.setX(screenBounds.getMaxX() - notificationPane.getWidth());
		notificationStage.setY(screenBounds.getMaxY() - notificationPane.getHeight());
		
		notificationPane.layoutBoundsProperty().addListener((o, oldVal, newVal) -> { 
			notificationStage.setX(screenBounds.getMaxX() - newVal.getWidth());
			notificationStage.setY(screenBounds.getMaxY() - newVal.getHeight());
			});
		
		notificationStage.setOpacity(0.0);
		Timeline fadeAnimation = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(notificationStage.opacityProperty(), 0.0, Interpolator.LINEAR)),
				new KeyFrame(Duration.millis(1000), new KeyValue(notificationStage.opacityProperty(), 1.0, Interpolator.LINEAR)));
		
		closeButton.setOnMouseClicked(e -> {
			fadeAnimation.setOnFinished(event -> {
				notificationStage.close();
			});
			fadeAnimation.setRate(-1.0);
			fadeAnimation.play();
		});
        
		notificationStage.show();
		
		new Thread() {
            public void run() {
            	try {
                	Thread.sleep(5000);
            	} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							fadeAnimation.setRate(-1.0);
							fadeAnimation.play();
						}
					});
            	}                
            }
        }.start();
		
		fadeAnimation.setRate(1.0);
		fadeAnimation.play();
	}
}
