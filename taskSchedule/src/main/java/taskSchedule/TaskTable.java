package taskSchedule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.javafx.event.EventHandlerManager;
import com.sun.javafx.scene.control.skin.ListViewSkin;
import com.sun.javafx.scene.control.skin.TableViewSkin;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Bounds;
import javafx.scene.control.Cell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Skin;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
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

		TableColumn<TaskModel, String> timeCol = new TableColumn<>("Time (Hours)");
		timeCol.setMinWidth(200);
		Callback<TableColumn<TaskModel, String>, TableCell<TaskModel, String>> cellFactoryT = //
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
									TaskModel taskModel = getTableView().getItems().get(getIndex());
									setGraphic(taskModel.getTimeView());
									setText(null);
								}
							}
						};
						return cell;
					}
				};

		timeCol.setCellFactory(cellFactoryT);

		TableColumn<TaskModel, String> actionCol = new TableColumn<>("Action");
		actionCol.setMinWidth(115);

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
		addMenu();
		loadData();
		setRowFactory(new Callback<TableView<TaskModel>, TableRow<TaskModel>>() {
			@Override
			public TableRow<TaskModel> call(TableView<TaskModel> tableView) {
				final TableRow<TaskModel> row = new TableRow<TaskModel>() {
					@Override
					protected void updateItem(TaskModel item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							setContextMenu(item.getMenu());
						}
					}
				};
				return row;
			}
		});

	}

	@Override
	public EventDispatchChain buildEventDispatchChain(EventDispatchChain eventDispatchChain) {
		if (getSkin() instanceof EventTarget) {
			eventDispatchChain = ((EventTarget) getSkin()).buildEventDispatchChain(eventDispatchChain);
		}
		return super.buildEventDispatchChain(eventDispatchChain);
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new TableViewCSkin<>(this);
	}

	private static class TableViewCSkin<T> extends TableViewSkin<T> implements EventTarget {
		private ContextMenuEventDispatcher contextHandler = new ContextMenuEventDispatcher(
				new EventHandlerManager(this));

		@Override
		public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
			int focused = getSkinnable().getFocusModel().getFocusedIndex();
			Cell cell = null;
			if (focused > -1) {
				cell = flow.getCell(focused);
				tail = cell.buildEventDispatchChain(tail);
			}
			contextHandler.setTargetCell(cell);
			return tail.prepend(contextHandler);
		}

		public TableViewCSkin(TableView<T> tableView) {
			super(tableView);
		}

	}

	private static class ContextMenuEventDispatcher implements EventDispatcher {
		private EventDispatcher delegate;
		private Cell<?> targetCell;

		public ContextMenuEventDispatcher(EventDispatcher delegate) {
			this.delegate = delegate;
		}

		public void setTargetCell(Cell<?> cell) {
			this.targetCell = cell;
		}

		@Override
		public Event dispatchEvent(Event event, EventDispatchChain tail) {
			event = handleContextMenuEvent(event);
			return delegate.dispatchEvent(event, tail);
		}

		private Event handleContextMenuEvent(Event event) {
			if (!(event instanceof ContextMenuEvent) || targetCell == null)
				return event;
			ContextMenuEvent cme = (ContextMenuEvent) event;
			if (!cme.isKeyboardTrigger())
				return event;
			final Bounds bounds = targetCell.localToScreen(targetCell.getBoundsInLocal());
			double x2 = bounds.getMinX() + bounds.getWidth() / 4;
			double y2 = bounds.getMinY() + bounds.getHeight() / 2;
			ContextMenuEvent toCell = new ContextMenuEvent(ContextMenuEvent.CONTEXT_MENU_REQUESTED, 0, 0, x2, y2, true,
					null);
			return toCell;
		}
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
		MenuItem copyRow = new MenuItem("Copy");
		copyRow.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
		copyRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				copyRow();
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

		setContextMenu(new ContextMenu(newRow, copyRow, deleteRow));
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

	public void addRow() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		getItems().add(new TaskModel("", "", LocalDateTime.now().format(formatter).toString(), 0.0));
	}

	public void deleteRow() {
		getItems().removeAll(getSelectionModel().getSelectedItems());
		getSelectionModel().clearSelection();
	}

	public void copyRow() {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		StringBuilder stringBuilder = new StringBuilder();
		getSelectionModel().getSelectedItems().forEach(taskModel -> {
			stringBuilder.append(
					taskModel.title.get() + "_" + taskModel.description.get() + "_" + taskModel.getTime() + "\n");
		});
		content.putString(stringBuilder.toString());
		clipboard.setContent(content);
	}
	
}
