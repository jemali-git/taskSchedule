package billCreator0.gui.view.explorer.dataExplorer;

import java.io.File;

import billCreator0.gui.view.KpiPerspective;
import billCreator0.gui.view.editor.EditorTab;
import billCreator0.gui.view.explorer.dataExplorer.util.Ticket;
import javafx.scene.control.TreeItem;

public class TemplateItem extends TreeItem<Object> {
	private Ticket ticket;
	File templateFile;

	public TemplateItem(Object value) {
		super(((File) value).getName());
		templateFile = (File) value;
		ticket = new Ticket("Server");
		setGraphic(ticket);
	}

	public void select() {
		ticket.selectionOn();
	}

	public void deselect() {
		ticket.selectOff();
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void open() {
		KpiPerspective.editor.getTabs().add(new EditorTab(templateFile));
	}

}
