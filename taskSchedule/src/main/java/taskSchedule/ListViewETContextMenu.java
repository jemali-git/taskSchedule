package taskSchedule;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class ListViewETContextMenu extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox hBox = new HBox();

		hBox.getStylesheets().add(getClass().getResource("style/DarkTheme.css").toExternalForm());
		Label label = new Label();
		label.getStyleClass().add("operation");
		
		hBox.getChildren().add(label);
		Scene scene = new Scene(hBox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}