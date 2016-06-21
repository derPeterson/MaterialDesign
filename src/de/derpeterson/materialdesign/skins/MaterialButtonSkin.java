package de.derpeterson.materialdesign.skins;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import de.derpeterson.materialdesign.controls.MaterialButton;
import de.derpeterson.materialdesign.controls.MaterialButton.ButtonType;
import de.derpeterson.materialdesign.controls.MaterialRippler;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MaterialButtonSkin extends ButtonSkin {

	private BorderPane buttonPane;

	private MaterialRippler rippler;

	private Timeline shadowAnimation;

	private boolean invalid = true;

	public MaterialButtonSkin(MaterialButton materialButton) {
		super(materialButton);

		this.buttonPane = new BorderPane();		

		BorderPane ripplerPane = new BorderPane();
		rippler = new MaterialRippler(ripplerPane);		
		buttonPane.setCenter(rippler);
		
		// Add Listener for change the ripplerColor
		materialButton.ripplerFillProperty().addListener((obj, oldVal, newVal) -> {rippler.setRipplerFill(newVal);});

		updateButtonType(materialButton.getButtonType());
		
		// Add Listener for raised or flat effect
		materialButton.buttonTypeProperty().addListener((o, oldVal, newVal) -> { updateButtonType(newVal); });

		materialButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			if(materialButton.getButtonType() == ButtonType.RAISED) {	
				if(shadowAnimation != null) {
					shadowAnimation.setRate(1);
					shadowAnimation.play();
				}
			}
		});
		materialButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
			if(materialButton.getButtonType() == ButtonType.RAISED) {	
				if(shadowAnimation != null) {
					shadowAnimation.setRate(-1);
					shadowAnimation.play();
				}
			}
		});
		
		materialButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, materialButton.getBackground().getFills().get(0).getRadii(), materialButton.getBackground().getFills().get(0).getInsets())));
		
		updateChildren();
	}	

	@Override
	protected void updateChildren() {
		super.updateChildren();
		if(this.buttonPane != null) {
			getChildren().add(0, buttonPane);
		}
		getChildren().forEach(e -> { if(e != buttonPane) e.setMouseTransparent(true); });
	}

	@Override 
	protected void layoutChildren(final double x, final double y, final double w, final double h) {
		if(invalid) {
			rippler.setRipplerFill(((MaterialButton)getSkinnable()).getRipplerFill());
			invalid = false;
		}
		super.layoutChildren(x, y, w, h);
		buttonPane.resizeRelocate(getSkinnable().getLayoutBounds().getMinX(), getSkinnable().getLayoutBounds().getMinY(), getSkinnable().getLayoutBounds().getWidth(), getSkinnable().getLayoutBounds().getHeight());
	}


	private void updateButtonType(ButtonType buttonType) {
		MaterialButton materialButton = (MaterialButton)getSkinnable();
		switch(buttonType) {
		case RAISED:
			materialButton.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(0,0,0, 0.26), 15, 0.16, 0, 4));
			this.shadowAnimation = new Timeline(
					new KeyFrame(Duration.ZERO,
							new KeyValue(((DropShadow)materialButton.getEffect()).radiusProperty(), 15,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)materialButton.getEffect()).spreadProperty(), 0.16,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)materialButton.getEffect()).offsetXProperty(), 0,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)materialButton.getEffect()).offsetYProperty(), 4,  Interpolator.EASE_BOTH)),							
					new KeyFrame(Duration.millis(200),
							new KeyValue(((DropShadow)materialButton.getEffect()).radiusProperty(), 30,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)materialButton.getEffect()).spreadProperty(), 0.30,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)materialButton.getEffect()).offsetXProperty(), 0,  Interpolator.EASE_BOTH),
							new KeyValue(((DropShadow)materialButton.getEffect()).offsetYProperty(), 10,  Interpolator.EASE_BOTH)
							));
			this.shadowAnimation.setDelay(Duration.seconds(0));
			break;
		case FLAT:
			materialButton.setEffect(null);
			this.shadowAnimation = null;
			break;
		}
	}
}
