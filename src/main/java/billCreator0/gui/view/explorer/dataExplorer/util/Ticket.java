package billCreator0.gui.view.explorer.dataExplorer.util;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ticket extends HBox {
	HBox ticketName = new HBox();
	HBox selector = new HBox();
	HBox ticketState = new HBox();

	public Ticket(String type) {
		setAlignment(Pos.CENTER);
		ticketState.setAlignment(Pos.BASELINE_CENTER);

		getChildren().addAll(ticketName, ticketState, selector);
		setPadding(new Insets(2, 2, 2, 2));
		setSpacing(3);

		ticketName.getChildren().add(new Label(type));

		ticketName.getStyleClass().add("ticketName");
		ticketState.getStyleClass().add("ticketState");

	}

	public void selectionOn() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				selector.getChildren().add(new Circle(3, 3, 3, Color.RED));
			}
		});
		selector.getChildren().add(new Circle(3, 3, 3, Color.RED));
	}

	public void selectOff() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				selector.getChildren().clear();
			}
		});

	}

	public int setTicketState(String state) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ticketState.getChildren().clear();
				ticketState.getChildren().add(new Label(state));
			}
		});

		return 0;
	}

}
