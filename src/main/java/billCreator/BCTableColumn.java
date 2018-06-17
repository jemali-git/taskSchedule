package billCreator;

import billCreator0.gui.WorkBenchWindow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BCTableColumn extends TableColumn<RowModel, String> {

	Stage dialogStage;
	ColumnModel columnModel;

	public BCTableColumn(String text) {

		this.columnModel = new ColumnModel(text);
		setText(text);
		setEditable(true);
		setMinWidth(100);
		addMenu();
		setCellValueFactory(new Callback<CellDataFeatures<RowModel, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<RowModel, String> p) {
				return p.getValue().values.get(text).getValue();
			}
		});
		
		setCellFactory(TextFieldTableCell.forTableColumn());
		setOnEditCommit(new EventHandler<CellEditEvent<RowModel, String>>() {
			@Override
			public void handle(CellEditEvent<RowModel, String> t) {
				t.getRowValue().getValues().get(text).getValue().set(t.getNewValue());
			}
		});

		dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.setTitle("Edit Variable");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(WorkBenchWindow.primaryStage);
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20, 20, 20, 20));
		gridPane.addColumn(0, new Label("Reference"), new Label("Expression Value"));

		TextField refrence = new TextField();
		columnModel.getReference().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				refrence.setText(newValue);
				setText(newValue);
			}
		});
		columnModel.getReference().set(text);


		TextField expressionValue = new TextField();
		columnModel.getExpressionValue().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				expressionValue.setText(newValue);
			}
		});
		columnModel.getExpressionValue().set(text);

		Button button = new Button("Apply");
		button.setOnAction(value -> {
			columnModel.getReference().set(refrence.getText());
			columnModel.getExpressionValue().set(expressionValue.getText());
			dialogStage.close();
			
		});
		gridPane.addColumn(1, refrence, expressionValue, button);
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		Scene scene = new Scene(gridPane);
		dialogStage.setScene(scene);
	}

	private void addMenu() {
		MenuItem newRow = new MenuItem("Edit");
		newRow.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		newRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				dialogStage.showAndWait();
			}
		});
		setContextMenu(new ContextMenu(newRow));
	}

	public ColumnModel getColumnModel() {
		return columnModel;
	}

	public void setColumnModel(ColumnModel columnModel) {
		this.columnModel = columnModel;
	}

}
