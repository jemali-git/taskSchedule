package billCreator0.gui.menuBar;

import billCreator0.gui.menuBar.fileMenu.FileMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class BCiMenuBar extends MenuBar {
	public BCiMenuBar() {
		getMenus().add(new FileMenu());
	}
}
