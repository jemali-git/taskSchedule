package billCreator;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class BCTableView extends TableView<RowModel> {

	File templateFile;

	public BCTableView(File templateFile) {
		this.templateFile = templateFile;
		addMenu();
		setEditable(true);
		setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		for (Character c = 'A'; c <= 'Z'; c++) {
			BCTableColumn bCTableColumn = new BCTableColumn(c.toString());
			getColumns().add(bCTableColumn);
		}

	}

	public void addRow() {
		RowModel rowModel = new RowModel();
		getColumns().forEach(column -> {
			ColumnModel columnModel = ((BCTableColumn) column).getColumnModel();
			rowModel.values.get(columnModel.getId()).setModels(rowModel, columnModel);
		});
		getItems().add(rowModel);
	}

	public void deleteRow() {
		getItems().removeAll(getSelectionModel().getSelectedItems());
		getSelectionModel().clearSelection();
	}

	private void addMenu() {
		MenuItem newRow = new MenuItem("New");
		newRow.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		newRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addRow();
			}
		});
		MenuItem getPdf = new MenuItem("Get Pdf");
		getPdf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(getSelectionModel().getSelectedItems().size());
			}
		});
		MenuItem deleteRow = new MenuItem("Delete");
		deleteRow.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
		deleteRow.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				deleteRow();
			}
		});
		setContextMenu(new ContextMenu(newRow, getPdf, deleteRow));
	}

}
