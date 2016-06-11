package de.derpeterson.materialdesign.controls;

import java.util.List;

import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;

import de.derpeterson.materialdesign.converters.ButtonTypeConverter;
import de.derpeterson.materialdesign.helper.css.CssHelper;
import de.derpeterson.materialdesign.helper.css.DefaultPropertyBasedCssMetaData;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;

import javafx.animation.Timeline;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Skin;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.util.Duration;

/**
 * MaterialButton is the material design implementation of a button. It contains
 * ripple effect, the effect color is set according to text fill of the button
 * 1st or the text fill of graphic node (if it was set to Label) 2nd.
 * 
 * @author cdoebl
 * @version 1.0
 * @since 2016.05.02
 */
public class MaterialButton extends Button {

	private double defaultRadius = 200;
	private double minRadius = 100;
	private double rippleRadius = 150;
	private RippleGenerator rippler;
	private BorderPane ripplePane;
	private Timeline shadowAnimation;
	/**
	 * {@inheritDoc}
	 */
	public MaterialButton() {
		super();
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	public MaterialButton(String text) {
		super(text);
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	public MaterialButton(String text, Node graphic) {
		super(text, graphic);
		initialize();
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		ButtonSkin buttonSkin = (ButtonSkin) getSkin();
		if (buttonSkin == null) {
			buttonSkin = new ButtonSkin(this);

			this.ripplePane = new BorderPane();
			Rectangle rectangle = new Rectangle();
			rectangle.widthProperty().bind(widthProperty());
			rectangle.heightProperty().bind(heightProperty());
			ripplePane.setClip(rectangle);

			ripplePane.getChildren().addAll(getChildren());

			ripplePane.boundsInParentProperty().addListener((o,oldVal,newVal)->{
				rippleRadius = newVal.getWidth();
				if(rippleRadius > defaultRadius)
					rippleRadius = defaultRadius;
				if(rippleRadius < minRadius)
					rippleRadius = minRadius;
			});

			updateButtonType(getButtonType());

			getChildren().add(0, ripplePane);
			
			setPickOnBounds(false);

			rippler = new RippleGenerator();
			addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
				rippler.setGeneratorCenterX(event.getX());
				rippler.setGeneratorCenterY(event.getY());
				rippler.startGeneration();

				if(getButtonType() == ButtonType.RAISED) {	
					if(shadowAnimation != null) {
						shadowAnimation.setRate(1);
						shadowAnimation.play();
					}
				}
			});
			addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
				rippler.stopGeneration();

				if(getButtonType() == ButtonType.RAISED) {	
					if(shadowAnimation != null) {
						shadowAnimation.setRate(-1);
						shadowAnimation.play();
					}
				}
			});
			
			buttonTypeProperty().addListener((o, oldVal, newVal) -> { updateButtonType(newVal); });
			
			setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, getBackground().getFills().get(0).getRadii(), getBackground().getFills().get(0).getInsets())));

			setSkin(buttonSkin);

		}
		return buttonSkin;
	}
	
	private void updateButtonType(ButtonType newVal) {
		switch(newVal) {
		case RAISED:
			setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(0,0,0, 0.26), 15, 0.16, 0, 4));
			
			this.shadowAnimation = new Timeline(
					new KeyFrame(Duration.ZERO,
							new KeyValue(((DropShadow)getEffect()).radiusProperty(), 15,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)getEffect()).spreadProperty(), 0.16,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)getEffect()).offsetXProperty(), 0,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)getEffect()).offsetYProperty(), 4,  Interpolator.EASE_BOTH)),							
					new KeyFrame(Duration.millis(200),
							new KeyValue(((DropShadow)getEffect()).radiusProperty(), 30,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)getEffect()).spreadProperty(), 0.30,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)getEffect()).offsetXProperty(), 0,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)getEffect()).offsetYProperty(), 10,  Interpolator.EASE_BOTH)
							));
			this.shadowAnimation.setDelay(Duration.seconds(0));
			break;
		case FLAT:
			setEffect(null);
			this.shadowAnimation = null;
			break;
		}
	}

	private void initialize() {
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
	}

	public class RippleGenerator {
		private Double generatorCenterX = 0.0;
		private Double generatorCenterY = 0.0;

		private ObservableList<Ripple> activeRippleList = FXCollections.observableArrayList();

		public class Ripple extends Circle {
			Timeline inAnimation = new Timeline(
					new KeyFrame(Duration.millis(300), new KeyValue(radiusProperty(), rippleRadius * 0.7, Interpolator.LINEAR)),
					new KeyFrame(Duration.millis(50), new KeyValue(opacityProperty(), 1, Interpolator.EASE_BOTH)));

			Timeline outAnimation = new Timeline(
					new KeyFrame(Duration.millis(400), new KeyValue(radiusProperty(), rippleRadius, Interpolator.LINEAR)),
					new KeyFrame(Duration.millis(400), new KeyValue(opacityProperty(), 0, Interpolator.EASE_BOTH)));


			public Ripple(double centerX, double centerY) {
				super(centerX, centerY, 0);
				setOpacity(0.0);
				setCache(true);
				setCacheHint(CacheHint.SPEED);
				setCacheShape(true);
				setSnapToPixel(false);
				setStrokeWidth(1.0);
				Color circleColor = new Color(((Color)getRipplerFill()).getRed(), ((Color)getRipplerFill()).getGreen(), ((Color)getRipplerFill()).getBlue(), 0.5);
				setFill(circleColor);
				setStroke(circleColor);
			}

		}

		public RippleGenerator() {
		}

		public void stopGeneration() {
			Ripple ripple = activeRippleList.get(activeRippleList.size() - 1);
			ripple.outAnimation.setOnFinished(event -> {
				ripplePane.getChildren().remove(ripple);					
				ripple.inAnimation.stop();
				activeRippleList.remove(ripple);
			});
			ripple.outAnimation.play();
		}

		public void startGeneration() {
			Ripple ripple = new Ripple(generatorCenterX, generatorCenterY);
			activeRippleList.add(ripple);
			ripplePane.getChildren().add(0, ripple);
			ripple.inAnimation.playFromStart();
		}

		public Double getGeneratorCenterX() {
			return generatorCenterX;
		}

		public void setGeneratorCenterX(Double generatorCenterX) {
			this.generatorCenterX = generatorCenterX;
		}

		public Double getGeneratorCenterY() {
			return generatorCenterY;
		}

		public void setGeneratorCenterY(Double generatorCenterY) {
			this.generatorCenterY = generatorCenterY;
		}
	}

	/***************************************************************************
	 * * Properties * *
	 **************************************************************************/
	/**
	 * The ripple color proeprty of MaterialButton
	 */
	private StyleableObjectProperty<Paint> ripplerFill = new SimpleStyleableObjectProperty<Paint>(StyleableProperties.RIPPLER_FILL, MaterialButton.this, "ripplerFill");


	/**
	 * Return the rippler fill property.
	 */
	public final StyleableObjectProperty<Paint> ripplerFillProperty() {
		return this.ripplerFill;
	}

	/**
	 * Return the rippler Color.
	 */
	public final Paint getRipplerFill() {
		return ripplerFillProperty().get() != null ? ripplerFillProperty().get() : getTextFill();
	}

	/**
	 * Set the rippler Color.
	 */
	public final void setRipplerFill(Paint ripplerFill) {
		ripplerFillProperty().set(ripplerFill);
	}

	/***************************************************************************
	 * * Stylesheet Handling * *
	 **************************************************************************/
	/**
	 * Initialize the style class to 'material-text-field'
	 * 
	 * This is the selector class from which CSS can be used to style this
	 * control.
	 */
	private static final String DEFAULT_STYLE_CLASS = "material-button";

	/**
	 * According to material design the button has two types: - flat: only shows
	 * the ripple effect upon clicking the button - raised: shows the ripple
	 * effect and change in depth upon clicking the button
	 */
	public static enum ButtonType {
		FLAT, RAISED
	};

	private StyleableObjectProperty<ButtonType> buttonType = new SimpleStyleableObjectProperty<ButtonType>(
			StyleableProperties.BUTTON_TYPE, MaterialButton.this, "buttonType", ButtonType.FLAT);

	public StyleableObjectProperty<ButtonType> buttonTypeProperty() {
		return buttonType;
	}

	public ButtonType getButtonType() {
		return buttonTypeProperty().get();
	}

	public void setButtonType(ButtonType buttonType) {
		buttonTypeProperty().setValue(buttonType);
	}

	private static class StyleableProperties {
		private static final DefaultPropertyBasedCssMetaData<MaterialButton, ButtonType> BUTTON_TYPE = CssHelper
				.createMetaData("-fx-button-type", ButtonTypeConverter.getInstance(), "buttonType", ButtonType.FLAT);
		private static final DefaultPropertyBasedCssMetaData<MaterialButton, Paint> RIPPLER_FILL = CssHelper
				.createMetaData("-fx-rippler-fill",	PaintConverter.getInstance(), "ripplerFill");
		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper
				.createCssMetaDataList(Button.getClassCssMetaData(), BUTTON_TYPE, RIPPLER_FILL);
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
		return getClassCssMetaData();
	}

	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return StyleableProperties.STYLEABLES;
	}
}
