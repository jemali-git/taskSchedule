package billCreator;

import javafx.beans.property.SimpleStringProperty;

public class ColumnModel {
	private final String id;

	private SimpleStringProperty reference = new SimpleStringProperty();
	private SimpleStringProperty expressionValue = new SimpleStringProperty();

	public ColumnModel(String id) {
		this.id = id;
	}

	public SimpleStringProperty getReference() {
		return reference;
	}

	public void setReference(SimpleStringProperty reference) {
		this.reference = reference;
	}

	public SimpleStringProperty getExpressionValue() {
		return expressionValue;
	}

	public void setExpressionValue(SimpleStringProperty expressionValue) {
		this.expressionValue = expressionValue;
	}

	public String getId() {
		return id;
	}

}
