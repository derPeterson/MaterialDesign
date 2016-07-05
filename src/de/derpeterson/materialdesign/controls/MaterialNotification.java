package de.derpeterson.materialdesign.controls;

import java.util.List;

import com.sun.javafx.css.converters.PaintConverter;

import de.derpeterson.materialdesign.converters.NotificationTypeConverter;
import de.derpeterson.materialdesign.css.CssResources;
import de.derpeterson.materialdesign.helper.css.CssHelper;
import de.derpeterson.materialdesign.helper.css.DefaultPropertyBasedCssMetaData;
import de.derpeterson.materialdesign.images.ImageResources;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.DefaultProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.util.Duration;

@DefaultProperty(value="control")
public class MaterialNotification extends BorderPane {
	
	public enum NotificationType { INFO , CUSTOM};
	
	private static final double maxWidth = 500;
	private static final double minWidth = 360;
	private static final double minHeight = 60;
	
	public MaterialNotification() {
		this("", "", NotificationType.CUSTOM);
	}
	
	public MaterialNotification(NotificationType notificationType) {
		this("", "", notificationType);		
	}
	
	public MaterialNotification(String title, String body, NotificationType notificationType) {
		setNotificationType(notificationType);
		initialize();		
	}
	private void initialize() {
		Stage notificationStage = new Stage(StageStyle.UNDECORATED);
		notificationStage.setResizable(false);
		notificationStage.setAlwaysOnTop(true);
		
		getStyleClass().add(DEFAULT_STYLE_CLASS);
		setMaxWidth(MaterialNotification.maxWidth);
		setMinWidth(MaterialNotification.minWidth);
		setMinHeight(MaterialNotification.minHeight);
		
		HBox leftBox = new HBox();
		leftBox.getStyleClass().add(DEFAULT_IMAGE_CONTAINER_STYLE_CLASS);
		ImageView notificationImage = new ImageView(new Image(ImageResources.class.getResource("info_36.png").toExternalForm()));
		leftBox.getChildren().add(notificationImage);
		
		setLeft(leftBox);
		
		VBox bodyBox = new VBox();
		bodyBox.setPadding(new Insets(8 ,0, 8, 0));
		bodyBox.setAlignment(Pos.CENTER_LEFT);
		Label headerLabel = new Label();
		headerLabel.getStyleClass().add(DEFAULT_HEADER_LABEL_STYLE_CLASS);
		headerLabel.setWrapText(true);
		bodyBox.getChildren().add(headerLabel);
		
		Label bodyLabel = new Label();
		bodyLabel.getStyleClass().add(DEFAULT_BODY_LABEL_STYLE_CLASS);
		bodyLabel.setWrapText(true);
		bodyBox.getChildren().add(bodyLabel);
		
		setCenter(bodyBox);
		
		VBox rightBox = new VBox();
		rightBox.getStyleClass().add(DEFAULT_CLOSE_CONTAINER_STYLE_CLASS);
		MaterialButton closeButton = new MaterialButton();
		closeButton.getStyleClass().add(DEFAULT_CLOSE_BUTTON_STYLE_CLASS);
		closeButton.setGraphic(new ImageView(new Image(ImageResources.class.getResource("close_16.png").toExternalForm())));
		rightBox.getChildren().add(closeButton);
		
		setRight(rightBox);

		Scene notificationScene = new Scene(this);
		notificationScene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		notificationScene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		notificationStage.setScene(notificationScene);
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		notificationStage.setX(screenBounds.getMaxX() - getWidth());
		notificationStage.setY(screenBounds.getMaxY() - getHeight());
		
		layoutBoundsProperty().addListener((o, oldVal, newVal) -> { 
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
							fadeAnimation.setOnFinished(event -> {
								notificationStage.close();
							});
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
	
	private final static String DEFAULT_STYLE_CLASS = "material-notification";
	private final static String DEFAULT_IMAGE_CONTAINER_STYLE_CLASS = "iconContainer";
	private final static String DEFAULT_CLOSE_CONTAINER_STYLE_CLASS = "closeContainer";
	private final static String DEFAULT_HEADER_LABEL_STYLE_CLASS = "headerLabel";
	private final static String DEFAULT_BODY_LABEL_STYLE_CLASS = "bodyLabel";
	private final static String DEFAULT_CLOSE_BUTTON_STYLE_CLASS = "closeButton";
	
	private StyleableObjectProperty<NotificationType> notificationType = new SimpleStyleableObjectProperty<NotificationType>(
			StyleableProperties.NOTIFICATION_TYPE, MaterialNotification.this, "notificationType", NotificationType.CUSTOM);

	public StyleableObjectProperty<NotificationType> notificationTypeProperty() {
		return notificationType;
	}

	public NotificationType getNotificationType() {
		return notificationTypeProperty().get();
	}

	public void setNotificationType(NotificationType notificationType) {
		notificationTypeProperty().setValue(notificationType);
	}
	
	private static class StyleableProperties {
		private static final DefaultPropertyBasedCssMetaData<MaterialNotification, NotificationType> NOTIFICATION_TYPE = CssHelper
				.createMetaData("-fx-notification-type", NotificationTypeConverter.getInstance(), "notificationType", NotificationType.CUSTOM);
		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper
				.createCssMetaDataList(Button.getClassCssMetaData(), NOTIFICATION_TYPE);
	}

	public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
		return getClassCssMetaData();
	}

	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return StyleableProperties.STYLEABLES;
	}
}
