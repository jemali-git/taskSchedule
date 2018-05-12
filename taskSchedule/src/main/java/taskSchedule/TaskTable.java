package taskSchedule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.util.Callback;

public class TaskTable extends TableView<TaskModel> {
	public TaskTable() {
		setEditable(true);
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
		// TODO crtl+n new row
		sceneProperty().addListener((obs, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.getAccelerators().put(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY),
						new Runnable() {
							@Override
							public void run() {
								final Clipboard clipboard = Clipboard.getSystemClipboard();
								final ClipboardContent content = new ClipboardContent();
								StringBuilder stringBuilder = new StringBuilder();
								getSelectionModel().getSelectedItems().forEach(taskModel -> {
									stringBuilder
											.append(taskModel.title.get() + "->" + taskModel.description.get() + "\n");
								});
								content.putString(stringBuilder.toString());
								clipboard.setContent(content);
								System.out.println(getSelectionModel().getSelectedItems().size());
							}
						});
				newScene.getAccelerators().put(new KeyCodeCombination(KeyCode.DELETE), new Runnable() {
					@Override
					public void run() {
						getItems().removeAll(getSelectionModel().getSelectedItems());
					}
				});
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
		getColumns().addAll(titleCol, descriptionCol, creationDateCol, timeCol, actionCol);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			public void run() {
				saveData();
			}
		}));
	}

	public void loadData() {
		try {
			FileInputStream fileInputStream;
			fileInputStream = new FileInputStream("data.task");
			ObjectInputStream ois = new ObjectInputStream(fileInputStream);
			List<Map> dataList = (List<Map>) ois.readObject();
			dataList.forEach(task -> {
				getItems().add(TaskModel.deserialize(task));
			});
			ois.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void saveData() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("data.task");
			ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
			List<Map> dataList = new ArrayList<>();
			getItems().forEach(task -> {
				dataList.add(task.serialize());
			});
			oos.writeObject(dataList);
			oos.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
