package billCreator0.gui;

import java.util.concurrent.ThreadLocalRandom;

import billCreator0.gui.menuBar.BCiMenuBar;
import billCreator0.gui.view.KpiPerspective;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WorkBenchWindow extends Application {
	public static BCiMenuBar bCiMenuBar;
	public static KpiPerspective kpiPerspective;
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		WorkBenchWindow.primaryStage = primaryStage;
		bCiMenuBar = new BCiMenuBar();
		kpiPerspective = new KpiPerspective();

		BorderPane borderPane = new BorderPane();
		borderPane.getStylesheets().add(getClass().getResource("style/DarkTheme.css").toExternalForm());

		borderPane.setTop(new VBox(bCiMenuBar));

		borderPane.setCenter(kpiPerspective);

		primaryStage.setScene(new Scene(borderPane, 700, 500));
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Runtime.getRuntime().exit(0);
			}
		});
	}

	public static void init(String[] args) {
		launch(args);
	}

	public static String getRN() {
		int length = ThreadLocalRandom.current().nextInt(5, 10);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int codeAC = ThreadLocalRandom.current().nextInt(32, 126);
			stringBuilder.append((char) codeAC);
		}
		return stringBuilder.toString();
	}
}
