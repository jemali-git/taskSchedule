package billCreator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static TaskTable taskTable;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		// stage.setTitle("Task Schedule");
		// ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		// InputStream is = classloader.getResourceAsStream("taskSchedule.png");
		// stage.getIcons().add(new Image(is));

		// taskTable = new TaskTable();
		// TextField titleText = new TextField();
		// titleText.setPromptText("title");

		// final VBox vbox = new VBox();
		// vbox.setPadding(new Insets(10, 10, 0, 10));
		// vbox.getChildren().add(new BCTableView());

		//Scene scene = new Scene(new TaskTable());
		//stage.setScene(scene);
		// stage.setResizable(false);
		//stage.show();

		// stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		// public void handle(WindowEvent we) {
		// Runtime.getRuntime().exit(0);
		// }
		// });
	}

}
