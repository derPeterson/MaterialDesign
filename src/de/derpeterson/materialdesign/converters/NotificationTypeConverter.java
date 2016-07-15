package de.derpeterson.materialdesign.converters;

import com.sun.javafx.css.StyleConverterImpl;

import de.derpeterson.materialdesign.controls.MDNotification.NotificationType;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

public class NotificationTypeConverter extends StyleConverterImpl<String, NotificationType> {
	private static class InstanceHolder {
		static final NotificationTypeConverter INSTANCE = new NotificationTypeConverter();
	}
	
	public static StyleConverter<String, NotificationType> getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
	private NotificationTypeConverter() {
		super();
	}
	
	@Override
	public NotificationType convert(ParsedValue<String, NotificationType> value, Font font) {
		try {
			return NotificationType.valueOf(value.getValue());
		} catch(IllegalArgumentException | NullPointerException e) {
			return NotificationType.CUSTOM;
		}
	}
	
	@Override
	public String toString() {
		return "NotificationTypeConverter";
	}
}
