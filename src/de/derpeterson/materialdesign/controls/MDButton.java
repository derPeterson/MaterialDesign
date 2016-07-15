package de.derpeterson.materialdesign.controls;

import java.util.List;

import com.sun.javafx.css.converters.PaintConverter;

import de.derpeterson.materialdesign.converters.ButtonTypeConverter;
import de.derpeterson.materialdesign.helper.css.CssHelper;
import de.derpeterson.materialdesign.helper.css.DefaultPropertyBasedCssMetaData;
import de.derpeterson.materialdesign.skins.MDButtonSkin;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;

/**
 * MaterialButton is the material design implementation of a button. It contains
 * ripple effect, the effect color is set according to text fill of the button
 * 1st or the text fill of graphic node (if it was set to Label) 2nd.
 * 
 * @author cdoebl
 * @version 1.0
 * @since 2016.05.02
 */
public class MDButton extends Button {

	/**
	 * {@inheritDoc}
	 */
	public MDButton() {
		super();
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	public MDButton(String text) {
		super(text);
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	public MDButton(String text, Node graphic) {
		super(text, graphic);
		initialize();
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new MDButtonSkin(this);
	}

	private void initialize() {
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
	}

	/***************************************************************************
	 * * Properties * *
	 **************************************************************************/
	/**
	 * The ripple color proeprty of MaterialButton
	 */
	private StyleableObjectProperty<Paint> ripplerFill = new SimpleStyleableObjectProperty<Paint>(StyleableProperties.RIPPLER_FILL, MDButton.this, "ripplerFill");


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
	 * Initialize the style class to 'md-button'
	 * 
	 * This is the selector class from which CSS can be used to style this
	 * control.
	 */
	private static final String DEFAULT_STYLE_CLASS = "md-button";

	/**
	 * According to material design the button has two types: - flat: only shows
	 * the ripple effect upon clicking the button - raised: shows the ripple
	 * effect and change in depth upon clicking the button
	 */
	public static enum ButtonType {
		FLAT, RAISED
	};

	private StyleableObjectProperty<ButtonType> buttonType = new SimpleStyleableObjectProperty<ButtonType>(
			StyleableProperties.BUTTON_TYPE, MDButton.this, "buttonType", ButtonType.FLAT);

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
		private static final DefaultPropertyBasedCssMetaData<MDButton, ButtonType> BUTTON_TYPE = CssHelper
				.createMetaData("-fx-button-type", ButtonTypeConverter.getInstance(), "buttonType", ButtonType.FLAT);
		private static final DefaultPropertyBasedCssMetaData<MDButton, Paint> RIPPLER_FILL = CssHelper
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
