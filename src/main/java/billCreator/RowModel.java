package billCreator;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class RowModel {

	Map<String, CellModel> values = new HashMap<>();

	public RowModel() {
		for (Character c = 'A'; c <= 'Z'; c++) {
			values.put(c.toString(), new CellModel());
		}
		values.values().forEach(value -> {
			value.getValue().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					values.values().forEach(otherValue -> {
						otherValue.updateValue();
					});
				}
			});
		});
	}

	public Map<String, CellModel> getValues() {
		return values;
	}

	public void setValues(Map<String, CellModel> columnInfoMap) {
		this.values = columnInfoMap;
	}
}
