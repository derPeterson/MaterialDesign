package de.derpeterson.materialdesign.converters;

import com.sun.javafx.css.StyleConverterImpl;

import de.derpeterson.materialdesign.controls.MaterialRippler.RipplerClip;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

public class RipplerClipConverter extends StyleConverterImpl<String, RipplerClip> {
	private static class InstanceHolder {
		static final RipplerClipConverter INSTANCE = new RipplerClipConverter();
	}
	
	public static StyleConverter<String, RipplerClip> getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
	private RipplerClipConverter() {
		super();
	}
	
	@Override
	public RipplerClip convert(ParsedValue<String, RipplerClip> value, Font font) {
		try {
			return RipplerClip.valueOf(value.getValue());
		} catch(IllegalArgumentException | NullPointerException e) {
			return RipplerClip.RECTANGLE;
		}
	}
	
	@Override
	public String toString() {
		return "RipplerClipConverter";
	}
}
