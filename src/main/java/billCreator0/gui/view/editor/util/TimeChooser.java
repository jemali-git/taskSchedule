package billCreator0.gui.view.editor.util;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class TimeChooser extends HBox {
	long seconds;
	long minutes;
	long hours;

	public TimeChooser(String msg, SimpleLongProperty simpleLongProperty) {

		setSpacing(10);
		getChildren().add(new Label(msg + ": "));
		TextField textTime = new TextField();
		StringConverter<Number> converter = new NumberStringConverter();

		Bindings.bindBidirectional(textTime.textProperty(), simpleLongProperty, converter);

		Label label = new Label();
		label.setText("Seconds: " + seconds + " Minutes: " + minutes + " Hours: " + hours);


		textTime.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				long time = simpleLongProperty.get();
				hours = time / (60 * 60);
				time = time % (60 * 60);
				minutes = (time / 60);
				time = time % 60;
				seconds = time;
				label.setText("Seconds: " + seconds + " Minutes: " + minutes + " Hours: " + hours);

				if (!newValue.matches("\\d*")) {
					textTime.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		getChildren().addAll(textTime, label);
	}

}
