package taskSchedule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
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
		taskTable.loadData();

		TextField titleText = new TextField();
		titleText.setPromptText("title");

		final TextField descriptionText = new TextField();
		descriptionText.setPromptText("description");

		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				taskTable.getItems().add(new TaskModel(titleText.getText(), descriptionText.getText(),
						LocalDateTime.now().format(formatter).toString(), 0));
				titleText.clear();
				descriptionText.clear();
			}
		});
		final Button deleteButton = new Button("Delete");
		deleteButton.setStyle("-fx-background-color: red");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				taskTable.getItems().remove(taskTable.getSelectionModel().getSelectedItem());
			}
		});

		final HBox hBox = new HBox();
		hBox.getChildren().addAll(titleText, descriptionText, addButton, deleteButton);
		hBox.setSpacing(10);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll(new HBox(taskTable), hBox);

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
