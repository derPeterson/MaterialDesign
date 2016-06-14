package de.derpeterson.materialdesign.controls;

import java.util.List;

import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.skin.CheckBoxSkin;

import de.derpeterson.materialdesign.helper.css.CssHelper;
import de.derpeterson.materialdesign.helper.css.DefaultPropertyBasedCssMetaData;
import de.derpeterson.materialdesign.skins.MaterialCheckBoxSkin;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class MaterialCheckBox extends CheckBox {
	public MaterialCheckBox() {
		super();
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	public MaterialCheckBox(String text) {
		super(text);
		initialize();
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new MaterialCheckBoxSkin(this);
	}
	
	private void initialize() {
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
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
	private static final String DEFAULT_STYLE_CLASS = "material-check-box";
	
	/**
	 * checkbox color proeprty when selected
	 */
	private StyleableObjectProperty<Paint> checkedColor = new SimpleStyleableObjectProperty<Paint>(StyleableProperties.CHECKED_COLOR, MaterialCheckBox.this, "checkedColor", Color.valueOf("#0F9D58"));

	public Paint getCheckedColor() {
		return checkedColor == null ? Color.valueOf("#0F9D58") : checkedColor.get();
	}
	
	public StyleableObjectProperty<Paint> checkedColorProperty() {
		return checkedColor;
	}
	
	public void setCheckedColor(Paint checkedColor) {
		this.checkedColor.set(checkedColor);
	}
	
	/**
	 * checkbox color proeprty when not selected
	 */
	private StyleableObjectProperty<Paint> uncheckedColor = new SimpleStyleableObjectProperty<Paint>(StyleableProperties.UNCHECKED_COLOR, MaterialCheckBox.this, "uncheckedColor", Color.valueOf("#5A5A5A"));

	public Paint getUncheckedColor() {
		return uncheckedColor == null ? Color.valueOf("#5A5A5A") : uncheckedColor.get();
	}
	
	public StyleableObjectProperty<Paint> uncheckedColorProperty() {
		return uncheckedColor;
	}
	
	public void setUncheckedColor(Paint uncheckedColor) {
		this.uncheckedColor.set(uncheckedColor);
	}
	
	private static class StyleableProperties {
		private static final DefaultPropertyBasedCssMetaData<MaterialCheckBox, Paint> CHECKED_COLOR = CssHelper
				.createMetaData("-fx-checked-color", PaintConverter.getInstance(), "checkedColor", Color.valueOf("#0F9D58"));
		private static final DefaultPropertyBasedCssMetaData<MaterialCheckBox, Paint> UNCHECKED_COLOR = CssHelper
				.createMetaData("-fx-unchecked-color", PaintConverter.getInstance(), "uncheckedColor", Color.valueOf("#5A5A5A"));
		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES = CssHelper
				.createCssMetaDataList(CheckBox.getClassCssMetaData(), CHECKED_COLOR, UNCHECKED_COLOR);
	}
	
	@Override
	public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
		return getClassCssMetaData();
	}

	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return StyleableProperties.STYLEABLES;
	}
}
