package billCreator0.gui.view.editor;

import java.io.File;

import billCreator.BCTableView;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EditorTab extends Tab {

	public EditorTab(File templateFile) {
		setText(templateFile.getName());
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10, 10, 50, 10));
		vBox.setSpacing(20);
		setContent(vBox);

		BCTableView bcTableView = new BCTableView( templateFile);
		VBox.setVgrow(bcTableView, Priority.SOMETIMES);
		vBox.getChildren().add(bcTableView);
	}
}
