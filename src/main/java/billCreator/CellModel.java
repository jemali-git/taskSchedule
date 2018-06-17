package billCreator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class CellModel {
	private SimpleStringProperty value = new SimpleStringProperty("");
	private ColumnModel columnModel;
	private RowModel rowModel;

	public void setModels(RowModel rowModel, ColumnModel columnModel) {
		this.rowModel = rowModel;
		this.columnModel = columnModel;
		columnModel.getExpressionValue().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateValue();
			}
		});
	}

	public SimpleStringProperty getValue() {
		return value;
	}

	public void updateValue() {
		if (columnModel != null) {
			String expression = columnModel.getExpressionValue().get();
			if (expression != null) {
				Pattern pattern = Pattern.compile("[a-zA-Z_]+");
				Matcher matcher = pattern.matcher(expression);
				StringBuilder stringBuilder = new StringBuilder();
				int start = 0;

				while (matcher.find()) {
					for (CellModel cellModel : rowModel.getValues().values()) {
						if (cellModel.getColumnModel().getReference().get().equals(matcher.group())) {
							stringBuilder.append(expression.substring(start, matcher.end()).replace(matcher.group(),
									cellModel.getValue().get()));
							break;
						}
					}
					start = matcher.end();
				}
				stringBuilder.append(expression.substring(start, expression.length()));
				ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
				ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
				try {
					this.value.set(scriptEngine.eval(stringBuilder.toString()).toString());
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
		}
	}

	public ColumnModel getColumnModel() {
		return columnModel;
	}

	public RowModel getRowModel() {
		return rowModel;
	}

}
