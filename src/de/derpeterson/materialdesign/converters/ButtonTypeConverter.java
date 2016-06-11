package de.derpeterson.materialdesign.converters;

import com.sun.javafx.css.StyleConverterImpl;

import de.derpeterson.materialdesign.controls.MaterialButton.ButtonType;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

public class ButtonTypeConverter extends StyleConverterImpl<String, ButtonType> {
	private static class InstanceHolder {
		static final ButtonTypeConverter INSTANCE = new ButtonTypeConverter();
	}
	
	public static StyleConverter<String, ButtonType> getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
	private ButtonTypeConverter() {
		super();
	}
	
	@Override
	public ButtonType convert(ParsedValue<String, ButtonType> value, Font font) {
		try {
			return ButtonType.valueOf(value.getValue());
		} catch(IllegalArgumentException | NullPointerException e) {
			return ButtonType.FLAT;
		}
	}
	
	@Override
	public String toString() {
		return "ButtonTypeConverter";
	}
}
