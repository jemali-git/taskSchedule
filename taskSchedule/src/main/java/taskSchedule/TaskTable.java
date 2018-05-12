package taskSchedule;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class TaskTable extends TableView<TaskModel> {
	public TaskTable() {
		setEditable(true);
		TableColumn<TaskModel, String> titleCol = new TableColumn<>("Title");
		titleCol.setMinWidth(100);
		titleCol.setCellValueFactory(new PropertyValueFactory<TaskModel, String>("title"));
		titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
		titleCol.setOnEditCommit(new EventHandler<CellEditEvent<TaskModel, String>>() {
			@Override
			public void handle(CellEditEvent<TaskModel, String> t) {
				((TaskModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());
			}
		});

		TableColumn<TaskModel, String> descriptionCol = new TableColumn<>("Description");
		descriptionCol.setMinWidth(200);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<TaskModel, String>("description"));
		descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		descriptionCol.setOnEditCommit(new EventHandler<CellEditEvent<TaskModel, String>>() {
			@Override
			public void handle(CellEditEvent<TaskModel, String> t) {
				((TaskModel) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setDescription(t.getNewValue());
			}
		});

		TableColumn<TaskModel, String> creationDateCol = new TableColumn<>("Creation Date");
		creationDateCol.setMinWidth(200);
		creationDateCol.setCellValueFactory(new PropertyValueFactory<TaskModel, String>("creationDate"));
		creationDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
		creationDateCol.setOnEditCommit(new EventHandler<CellEditEvent<TaskModel, String>>() {
			@Override
			public void handle(CellEditEvent<TaskModel, String> t) {
				((TaskModel) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setCreationDate(t.getNewValue());
			}
		});

		TableColumn<TaskModel, String> timeCol = new TableColumn<>("Time");
		timeCol.setMinWidth(200);
		timeCol.setCellValueFactory(new PropertyValueFactory<TaskModel, String>("time"));

		// timeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		// timeCol.setOnEditCommit(new EventHandler<CellEditEvent<TaskModel, String>>()
		// {
		// @Override
		// public void handle(CellEditEvent<TaskModel, String> t) {
		// ((TaskModel)
		// t.getTableView().getItems().get(t.getTablePosition().getRow())).setTime(t.getNewValue());
		// }
		// });

		TableColumn<TaskModel, String> actionCol = new TableColumn<>("Action");
		actionCol.setMinWidth(110);
		actionCol.setCellValueFactory(new PropertyValueFactory<>("Action"));

		Callback<TableColumn<TaskModel, String>, TableCell<TaskModel, String>> cellFactory = //
				new Callback<TableColumn<TaskModel, String>, TableCell<TaskModel, String>>() {
					@Override
					public TableCell<TaskModel, String> call(final TableColumn<TaskModel, String> param) {
						final TableCell<TaskModel, String> cell = new TableCell<TaskModel, String>() {

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);

								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									TaskModel person = getTableView().getItems().get(getIndex());
									setGraphic(person.getActions());
									setText(null);
								}
							}
						};
						return cell;
					}
				};

		actionCol.setCellFactory(cellFactory);
		  getColumns().addAll(titleCol, descriptionCol, creationDateCol,timeCol, actionCol);
	}
}
