package de.derpeterson.materialdesign.skins;

import java.util.List;

import com.sun.javafx.scene.control.skin.CheckBoxSkin;

import de.derpeterson.materialdesign.controls.MaterialCheckBox;
import de.derpeterson.materialdesign.controls.MaterialRippler;
import de.derpeterson.materialdesign.controls.MaterialRippler.RipplerClip;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class MaterialCheckBoxSkin extends CheckBoxSkin {

	private AnchorPane container;
	
	private double boxWidth;
	private double boxHeight;
	
	private double maxHeight;
	
	private double boxPadding = 10;
	
	private double lineThick = 2;
	
	private double labelOffset = 0;

	private StackPane boxStackPane;

	private Line rightLine;

	private Line leftLine;
	
	private Boolean invalid = true;

	private MaterialRippler rippler;

	public MaterialCheckBoxSkin(MaterialCheckBox materialCheckBox) {
		super(materialCheckBox);
		
		boxStackPane = new StackPane();
		boxStackPane.setMinSize(20, 20);
		boxStackPane.setPrefSize(20, 20);
		boxStackPane.setMaxSize(20, 20);
		boxStackPane.setBorder(new Border(new BorderStroke(materialCheckBox.getUncheckedColor(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		StackPane boxContainer = new StackPane();
		boxContainer.getChildren().add(boxStackPane);
		boxContainer.setPadding(new Insets(boxPadding));
		rippler = new MaterialRippler(boxContainer, RipplerClip.CIRCLE);
		rippler.setRipplerFill(getSkinnable().isSelected() ? materialCheckBox.getUncheckedColor() : materialCheckBox.getCheckedColor());
		
		this.rightLine = new Line();
		this.leftLine = new Line();
		rightLine.setStroke(materialCheckBox.getCheckedColor());
		leftLine.setStroke(materialCheckBox.getCheckedColor());
		rightLine.setStrokeWidth(lineThick);
		leftLine.setStrokeWidth(lineThick);
		rightLine.setVisible(false);
		leftLine.setVisible(false);
		
		this.container = new AnchorPane();
		container.getChildren().add(rightLine);
		container.getChildren().add(leftLine);
		container.getChildren().add(rippler);
		AnchorPane.setRightAnchor(rippler, labelOffset);
		
		materialCheckBox.selectedProperty().addListener((o, oldVal, newVal) ->{
			rippler.setRipplerFill(newVal ? materialCheckBox.getUncheckedColor() : materialCheckBox.getCheckedColor());
			animation.setRate(newVal ? 1: -1);
			animation.play();
		});
		
		updateChildren();
	}
	
	@Override protected void updateChildren() {
		super.updateChildren();
		if(container != null) {
			getChildren().remove(1);
			getChildren().add(container);
		}
	}
	
	@Override protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) + snapSize(boxStackPane.minWidth(-1)) + labelOffset + 2 * boxPadding;
	}

	@Override protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) + snapSize(boxStackPane.prefWidth(-1)) + labelOffset + 2 * boxPadding;
	}
	
	@Override protected void layoutChildren(final double x, final double y, final double w, final double h) {		
		final CheckBox checkBox = getSkinnable();
		
		boxWidth = snapSize(container.prefWidth(-1));
		boxHeight = snapSize(container.prefHeight(-1));
		
		final double computeWidth = Math.min(checkBox.prefWidth(-1), checkBox.minWidth(-1)) + labelOffset + 2 * boxPadding;
		final double labelWidth = Math.min(computeWidth - boxWidth, w - snapSize(boxWidth)) + labelOffset + 2 + boxPadding;
		final double labelHeight = Math.min(checkBox.prefHeight(labelWidth), h);
		
		maxHeight = Math.max(labelHeight, boxHeight);
		
		final double xOffset = (computeXOffest(w, labelWidth + boxWidth, checkBox.getAlignment().getHpos()) + x);
		final double yOffset = (computeYOffset(h, labelHeight + boxHeight, checkBox.getAlignment().getVpos()) + h);
		
		if(invalid) {
			rightLine.setStartX((boxWidth + boxPadding - labelOffset) / 2 - boxWidth/5.5);
			rightLine.setStartY(maxHeight - boxPadding - lineThick);
			rightLine.setEndX((boxWidth + boxPadding - labelOffset) / 2 - boxWidth/5.5);
			rightLine.setEndY(maxHeight - boxPadding - lineThick);
			leftLine.setStartX((boxWidth + boxPadding - labelOffset) / 2 - boxWidth/5.5);
			leftLine.setStartY(maxHeight - boxPadding - lineThick);
			leftLine.setEndX((boxWidth + boxPadding - labelOffset) / 2 - boxWidth/5.5);
			leftLine.setEndY(maxHeight - boxPadding - lineThick);
			
			animation = new Timeline(
					new KeyFrame(
							Duration.ZERO,       
							new KeyValue(rightLine.visibleProperty(), false,Interpolator.EASE_BOTH),
							new KeyValue(leftLine.visibleProperty(), false,Interpolator.EASE_BOTH),
							new KeyValue(boxStackPane.rotateProperty(), 0 ,Interpolator.EASE_BOTH),
							new KeyValue(boxStackPane.scaleXProperty(), 1 ,Interpolator.EASE_BOTH),
							new KeyValue(boxStackPane.scaleYProperty(), 1 ,Interpolator.EASE_BOTH),
							new KeyValue(boxStackPane.translateYProperty(), 0 ,Interpolator.EASE_BOTH),
							new KeyValue(boxStackPane.translateXProperty(), 0 ,Interpolator.EASE_BOTH),
							new KeyValue(boxStackPane.opacityProperty(), 1 ,Interpolator.EASE_BOTH)
							),
							new KeyFrame(Duration.millis(100),
									new KeyValue(rightLine.visibleProperty(), true,Interpolator.EASE_BOTH),
									new KeyValue(leftLine.visibleProperty(), true,Interpolator.EASE_BOTH),
									new KeyValue(rightLine.endXProperty(), (boxWidth + boxPadding - labelOffset) / 2 - boxWidth / 5.5 ,Interpolator.EASE_BOTH),
									new KeyValue(rightLine.endYProperty(), maxHeight-boxPadding - 2 * lineThick ,Interpolator.EASE_BOTH),
									new KeyValue(leftLine.endXProperty(), (boxWidth + boxPadding - labelOffset) / 2 - boxWidth / 5.5 ,Interpolator.EASE_BOTH),
									new KeyValue(leftLine.endYProperty(), maxHeight - boxPadding - 2 * lineThick ,Interpolator.EASE_BOTH)
									),
									new KeyFrame(Duration.millis(125),
											new KeyValue(boxStackPane.rotateProperty(), 44 ,Interpolator.EASE_BOTH),
											new KeyValue(boxStackPane.scaleXProperty(), 0.3 ,Interpolator.EASE_BOTH),
											new KeyValue(boxStackPane.scaleYProperty(), 0.4 ,Interpolator.EASE_BOTH),
											new KeyValue(boxStackPane.translateYProperty(), boxHeight/12  ,Interpolator.EASE_BOTH),
											new KeyValue(boxStackPane.translateXProperty(), - boxWidth/12 ,Interpolator.EASE_BOTH)										
											),											
											new KeyFrame(Duration.millis(175),
													new KeyValue(boxStackPane.opacityProperty(), 0 ,Interpolator.EASE_BOTH)
													),
													new KeyFrame(
															Duration.millis(200),
															new KeyValue(rightLine.endXProperty(), boxWidth - boxPadding - labelOffset + lineThick / 2 ,Interpolator.EASE_BOTH),
															new KeyValue(rightLine.endYProperty(), (maxHeight - boxPadding) / 2.4 ,Interpolator.EASE_BOTH),
															new KeyValue(leftLine.endXProperty(), boxPadding + lineThick / 4 ,Interpolator.EASE_BOTH),
															new KeyValue(leftLine.endYProperty(), (maxHeight-boxPadding) / 1.4 ,Interpolator.EASE_BOTH)
															)
					);
			animation.setDelay(Duration.seconds(0));
			invalid = false;
		}
		layoutLabelInArea(xOffset + boxWidth, yOffset, labelWidth, maxHeight, checkBox.getAlignment());
		container.resize(boxWidth, boxHeight);
		positionInArea(container, xOffset, yOffset, boxWidth, maxHeight, 0, checkBox.getAlignment().getHpos(), checkBox.getAlignment().getVpos());
		
	}
	
	static double computeXOffest(double width, double contentWidth, HPos hpos) {
		switch(hpos) {
		case CENTER:
			return (width - contentWidth) / 2;
		case LEFT:
			return 0;
		case RIGHT:
			return width - contentWidth;
		default:
			return 0;		
		}
	}
	
	static double computeYOffset(double height, double contentHeight, VPos vpos) {
		switch(vpos) {
		case BOTTOM:
			return height - contentHeight;
		case CENTER:
			return (height - contentHeight) / 2;
		case TOP:
			return 0;
		default:
			return 0;	
		}
	}
	
	private Timeline animation = null;
}
