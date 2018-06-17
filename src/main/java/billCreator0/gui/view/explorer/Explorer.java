package billCreator0.gui.view.explorer;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import billCreator.Main;
import billCreator0.gui.WorkBenchWindow;
import billCreator0.gui.view.editor.Editor;
import billCreator0.gui.view.explorer.dataExplorer.TemplateItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class Explorer extends TabPane {

	static TreeItem<Object> templates = new TreeItem<>();

	public Explorer() {
		Tab tab = new Tab("Templates");
		tab.setClosable(false);

		TreeView<Object> treeView = new TreeView<Object>(templates);
		treeView.setContextMenu(addMenu(treeView));
		treeView.setShowRoot(false);
		tab.setContent(treeView);
		getTabs().add(tab);
		loadTemplates();
	}

	public static void loadTemplates() {

		try {

			Arrays.asList(new File("./src/main/resources/").listFiles()).forEach(file -> {
				if (file.getName().endsWith(".rtf")) {
					TemplateItem templateItem = new TemplateItem(file);
					templates.getChildren().add(templateItem);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addTemplate(String serverPath, String serverPassword) {

		try {
			File file = new File("./src/main/resources/template.rtf");
			int increment = 1;
			while (file.exists()) {
				file = new File("./src/main/resources/template" + "(" + increment + ")" + ".rtf");
				increment++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.close();
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
			TemplateItem templateItem = new TemplateItem(file);
			templates.getChildren().add(templateItem);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteTemplate(TreeItem<Object> treeItem) {
		Desktop desktop = Desktop.getDesktop();
		templates.getChildren().remove(treeItem);
	}

	private ContextMenu addMenu(TreeView<Object> treeView) {
		MenuItem newRow = new MenuItem("New");
		newRow.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		newRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Explorer.addTemplate(null, null);
			}
		});

		MenuItem openTemplate = new MenuItem("Open Template");
		openTemplate.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		openTemplate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				((TemplateItem) treeView.getSelectionModel().getSelectedItem()).open();
			}
		});

		MenuItem copyRow = new MenuItem("Copy");
		copyRow.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
		copyRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// copyRow();
			}
		});
		MenuItem deleteRow = new MenuItem("Delete");
		deleteRow.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
		deleteRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				deleteTemplate(treeView.getSelectionModel().getSelectedItem());
			}
		});

		return (new ContextMenu(newRow, openTemplate, copyRow, deleteRow));
	}
}
