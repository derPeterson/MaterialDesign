package de.derpeterson.materialdesign.controls;

import java.util.List;

import com.sun.javafx.css.converters.PaintConverter;

import de.derpeterson.materialdesign.converters.RipplerClipConverter;
import de.derpeterson.materialdesign.helper.css.CssHelper;
import de.derpeterson.materialdesign.helper.css.DefaultPropertyBasedCssMetaData;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.DefaultProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

@DefaultProperty(value="control")
public class MDRippler extends StackPane {
	
	public enum RipplerClip { CIRCLE, RECTANGLE }
	
	private Node control;
	
	private RippleGenerator rippler;
	
	private StackPane ripplerPane;

	private double defaultRadius = 200;
	private double minRadius = 100;
	private double rippleRadius = 150;

	private BorderPane ripplePane;
	
	public MDRippler(Node control) {
		this(control, RipplerClip.RECTANGLE);
	}

	public MDRippler(Node control, RipplerClip ripplerClip) {
		super();
		setRipplerClip(ripplerClip);
		initialize();
		setControl(control);
		setCache(true);
		setCacheHint(CacheHint.SPEED);
		setCacheShape(true);
		setSnapToPixel(true);
	}

	private void setControl(Node control) {
		if(control != null) {
			this.control = control;
			
			rippler = new RippleGenerator();
			
			this.ripplePane = new BorderPane();
			
			this.control.boundsInParentProperty().addListener((o,oldVal,newVal)->{
				rippleRadius = newVal.getWidth();
				if(rippleRadius > defaultRadius)
					rippleRadius = defaultRadius;
				if(rippleRadius < minRadius)
					rippleRadius = minRadius;
			});
			
			setPickOnBounds(false);
			
			addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
				rippler.setGeneratorCenterX(event.getX());
				rippler.setGeneratorCenterY(event.getY());
				rippler.startGeneration();

			});
			addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
				rippler.stopGeneration();
			});
			
			this.getChildren().add(this.control);
			this.getChildren().add(this.ripplePane);
		}
	}
	
	private Shape createRippleClip() {
		double width = control.getLayoutBounds().getWidth();
		double height = control.getLayoutBounds().getHeight();
		Shape rippleClip = new Rectangle(width,  height);
		if(ripplerClip.get() == RipplerClip.CIRCLE) {
			double radius = Math.min(width / 2, height / 2);
			rippleClip = new Circle(width / 2 , height / 2, radius);
		} 
		return rippleClip;
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
				setClip(createRippleClip());
				Color circleColor = new Color(((Color)getRipplerFill()).getRed(), ((Color)getRipplerFill()).getGreen(), ((Color)getRipplerFill()).getBlue(), 0.5);
				setFill(circleColor);
				setStroke(circleColor);
			}

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

	private void initialize() {
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
	}
	
	private final static String DEFAULT_STYLE_CLASS = "md-rippler";
	
	private StyleableObjectProperty<Paint> ripplerFill = new SimpleStyleableObjectProperty<Paint>(StyleableProperties.RIPPLER_FILL, MDRippler.this, "ripplerFill", Color.rgb(0, 200, 255));
	
	public StyleableObjectProperty<Paint> ripplerFillProperty() {
		return this.ripplerFill;
	}
	
	public Paint getRipplerFill() {
		return ripplerFill == null ? Color.rgb(0, 200, 255) : ripplerFill.get(); 
	}

	public void setRipplerFill(Paint color) {
		this.ripplerFill.set(color);
	}
	
	private StyleableObjectProperty<RipplerClip> ripplerClip = new SimpleStyleableObjectProperty<RipplerClip>(StyleableProperties.RIPPLER_CLIP, MDRippler.this, "ripplerClip", RipplerClip.RECTANGLE);
	
	public StyleableObjectProperty<RipplerClip> ripplerClipProperty() {
		return this.ripplerClip;
	}
	
	public RipplerClip getRipplerClip() {
		return ripplerClip == null ? RipplerClip.RECTANGLE : ripplerClip.get(); 
	}

	public void setRipplerClip(RipplerClip ripplerClip) {
		this.ripplerClip.set(ripplerClip);
	}
	
	private static class StyleableProperties {
		private static final DefaultPropertyBasedCssMetaData<MDRippler, Paint> RIPPLER_FILL = CssHelper.createMetaData("-fx-rippler-fill", PaintConverter.getInstance(), "ripplerFill", Color.rgb(0, 200, 255));
		private static final DefaultPropertyBasedCssMetaData<MDRippler, RipplerClip> RIPPLER_CLIP = CssHelper.createMetaData("-fx-rippler-clip", RipplerClipConverter.getInstance(), "ripplerClip", RipplerClip.RECTANGLE);
		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper.createCssMetaDataList(Parent.getClassCssMetaData(), RIPPLER_FILL, RIPPLER_CLIP);	
	}
	
	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
 
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
}
