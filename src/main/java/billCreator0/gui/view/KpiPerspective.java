package billCreator0.gui.view;

import billCreator0.gui.view.editor.Editor;
import billCreator0.gui.view.explorer.Explorer;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

public class KpiPerspective extends AnchorPane {
	public static Explorer explorer;
	public static Editor editor;

	public KpiPerspective() {
		explorer = new Explorer();
		editor = new Editor();
		SplitPane splitPane = new SplitPane();
		AnchorPane.setBottomAnchor(splitPane, 0.0);
		AnchorPane.setTopAnchor(splitPane, 0.0);
		AnchorPane.setRightAnchor(splitPane, 0.0);
		AnchorPane.setLeftAnchor(splitPane, 0.0);
		splitPane.setDividerPositions(0.1, 0.9);
		getChildren().add(splitPane);
		splitPane.getItems().addAll(explorer, editor);

	}
}
