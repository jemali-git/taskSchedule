package taskSchedule;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static TaskTable taskTable;
	public static void main(String[] args) {
		launch(args);
	}
 
	@Override
	public void start(Stage stage) {

		stage.setTitle("Task Schedule");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("taskSchedule.png")));

		taskTable = new TaskTable();
		TextField titleText = new TextField();
		titleText.setPromptText("title");

		final VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 10, 0, 10));
		vbox.getChildren().addAll(taskTable);

		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Runtime.getRuntime().exit(0);
			}
		});
	}

}
