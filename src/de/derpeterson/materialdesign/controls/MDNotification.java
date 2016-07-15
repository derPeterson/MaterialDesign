package de.derpeterson.materialdesign.controls;

import java.util.Arrays;
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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.util.Duration;

@DefaultProperty(value="control")
public class MDNotification extends BorderPane {

	public enum NotificationType { INFO, SUCCESS, WARNING, ERROR, CUSTOM};

	private static final double maxWidth = 500;
	private static final double minWidth = 360;
	private static final double minHeight = 60;

	private ImageView imageIcon;

	private Label headerLabel;

	private MDButton closeButton;
	private Stage stage;
	private Timeline fadeAnimation;
	
	private VBox completeBodyBox;
	private VBox bodyTextBox;
	private FlowPane flowPane;

	public MDNotification() {
		this(NotificationType.CUSTOM, "", "");
	}

	public MDNotification(NotificationType notificationType) {
		this(notificationType, "", "");		
	}

	public MDNotification(NotificationType notificationType, String title, String message) {
		initialize(notificationType, title, message);
	}

	private void initialize(NotificationType notificationType, String title, String message) {
		getStyleClass().add(DEFAULT_STYLE_CLASS);

		setMaxWidth(MDNotification.maxWidth);
		setMinWidth(MDNotification.minWidth);
		setMinHeight(MDNotification.minHeight);

		HBox leftBox = new HBox();
		leftBox.getStyleClass().add(DEFAULT_IMAGE_CONTAINER_STYLE_CLASS);
		this.imageIcon = new ImageView(getNotificationImage(notificationType));
		leftBox.getChildren().add(this.imageIcon);

		setLeft(leftBox);

		this.completeBodyBox = new VBox();
		this.completeBodyBox.setPadding(new Insets(8, 0, 8, 0));
		this.completeBodyBox.setAlignment(Pos.CENTER_LEFT);
		
		HBox bodyTitleBox = new HBox();
		this.headerLabel = new Label(title);
		this.headerLabel.getStyleClass().add(DEFAULT_HEADER_LABEL_STYLE_CLASS);
		this.headerLabel.setWrapText(true);
		bodyTitleBox.getChildren().add(this.headerLabel);
		this.completeBodyBox.getChildren().add(bodyTitleBox);
		
		this.bodyTextBox = new VBox();
		this.completeBodyBox.getChildren().add(this.bodyTextBox);
		
		this.flowPane = new FlowPane(Orientation.VERTICAL);
		flowPane.setPrefWrapLength(Region.USE_COMPUTED_SIZE);
		completeBodyBox.getChildren().add(flowPane);
		setCenter(this.completeBodyBox);

		VBox rightBox = new VBox();
		rightBox.getStyleClass().add(DEFAULT_CLOSE_CONTAINER_STYLE_CLASS);
		this.closeButton = new MDButton();
		closeButton.getStyleClass().add(DEFAULT_CLOSE_BUTTON_STYLE_CLASS);
		closeButton.setGraphic(new ImageView(new Image(ImageResources.class.getResource("close_16.png").toExternalForm())));
		rightBox.getChildren().add(closeButton);

		setRight(rightBox);

		initStage();
		initAnimations();
	}
	
	private Image getNotificationImage(NotificationType notificationType) {
		switch(notificationType) {
			case INFO:
				return new Image(ImageResources.class.getResource("info_36.png").toExternalForm());
			case SUCCESS:
				return new Image(ImageResources.class.getResource("success_36.png").toExternalForm());
			case WARNING:
				return new Image(ImageResources.class.getResource("warning_36.png").toExternalForm());
			case ERROR:
				return new Image(ImageResources.class.getResource("error_36.png").toExternalForm());
			case CUSTOM:
				return getImage();
		}
		return null;
	}

	private void initStage() {
		this.stage = new Stage(StageStyle.UNDECORATED);
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);

		Scene notificationScene = new Scene(this);
		notificationScene.getStylesheets().add(CssResources.class.getResource("fonts.css").toExternalForm());
		notificationScene.getStylesheets().add(CssResources.class.getResource("components.css").toExternalForm());
		stage.setScene(notificationScene);

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(screenBounds.getMaxX() - getWidth());
		stage.setY(screenBounds.getMaxY() - getHeight());

		layoutBoundsProperty().addListener((o, oldVal, newVal) -> { 
			stage.setX(screenBounds.getMaxX() - newVal.getWidth());
			stage.setY(screenBounds.getMaxY() - newVal.getHeight());
		});

		stage.setOpacity(0.0);
	}

	private void initAnimations() {
		this.fadeAnimation = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(stage.opacityProperty(), 0.0, Interpolator.LINEAR)),
				new KeyFrame(Duration.millis(1000), new KeyValue(stage.opacityProperty(), 1.0, Interpolator.LINEAR)));

		closeButton.setOnMouseClicked(e -> {
			fadeAnimation.setOnFinished(event -> {
				stage.close();
			});
			fadeAnimation.setRate(-1.0);
			fadeAnimation.play();
		});
	}

	public void setTitle(String title) {
		if(headerLabel != null) headerLabel.setText(title);
	}

	public String getTitle() {
		return headerLabel != null ? headerLabel.getText() : null;
	}
	
	public void setImage(Image image) {
		if(imageIcon != null) imageIcon.setImage(image);
	}

	public Image getImage() {
		return imageIcon != null ? imageIcon.getImage() : null;
	}
	
	public void setMessage(String... message) {
		setMessage(Arrays.asList(message).stream().map(Label::new).toArray(Label[]::new));
	}
	
	public void setMessage(Label... messageStyledLabel) {
		if(bodyTextBox != null) {
			if(bodyTextBox.getChildren().size() > 0) bodyTextBox.getChildren().clear();
			Arrays.asList(messageStyledLabel).forEach(label -> {
				label.getStyleClass().add(DEFAULT_BODY_LABEL_STYLE_CLASS);
				label.setWrapText(true);
				bodyTextBox.getChildren().add(label);
			});
		}
	}
	
	public void addMessage(String message) {
		addMessage(new Label(message));
	}
	
	public void addMessage(Label label) {
		if(bodyTextBox != null) {
			label.getStyleClass().add(DEFAULT_BODY_LABEL_STYLE_CLASS);
			label.setWrapText(true);
			bodyTextBox.getChildren().add(label);
		}
	}
	
	public String[] getMessageText() {
		return bodyTextBox.getChildren().stream().filter(e -> e instanceof Label).map(e -> (Label)e).map(e -> new String(e.getText())).toArray(String[]::new);
	}
	
	public Label[] getMessageLabel() {
		return bodyTextBox.getChildren().stream().filter(e -> e instanceof Label).map(e -> (Label)e).toArray(Label[]::new);
	}

	public void showAndWait() {
		showAndDismiss(Duration.UNKNOWN);
	}

	public void showAndDismiss(Duration dismissDelay) {        
		if(!stage.isShowing()) {
			stage.show();

			if(dismissDelay != Duration.UNKNOWN) {
				fadeAnimation.setOnFinished(e -> {
					new Thread() {
						public void run() {
							try {
								Thread.sleep(new Double(dismissDelay.toMillis()).longValue());
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										fadeAnimation.setOnFinished(event -> {
											stage.close();
										});
										fadeAnimation.setRate(-1.0);
										fadeAnimation.play();
									}
								});
							}                
						}
					}.start();
				});
			}


			fadeAnimation.setRate(1.0);
			fadeAnimation.play();
		}
	}

	private final static String DEFAULT_STYLE_CLASS = "md-notification";
	private final static String DEFAULT_IMAGE_CONTAINER_STYLE_CLASS = "iconContainer";
	private final static String DEFAULT_CLOSE_CONTAINER_STYLE_CLASS = "closeContainer";
	private final static String DEFAULT_HEADER_LABEL_STYLE_CLASS = "headerLabel";
	private final static String DEFAULT_BODY_LABEL_STYLE_CLASS = "bodyLabel";
	private final static String DEFAULT_CLOSE_BUTTON_STYLE_CLASS = "closeButton";

	private StyleableObjectProperty<NotificationType> notificationType = new SimpleStyleableObjectProperty<NotificationType>(
			StyleableProperties.NOTIFICATION_TYPE, MDNotification.this, "notificationType", NotificationType.CUSTOM);

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
		private static final DefaultPropertyBasedCssMetaData<MDNotification, NotificationType> NOTIFICATION_TYPE = CssHelper
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
