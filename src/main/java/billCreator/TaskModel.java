package billCreator;

import java.util.HashMap;
import java.util.Map;

import billCreator.util.StopWatch;
import billCreator.util.StopWatch.TimeUnit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;;

public class TaskModel {

	SimpleStringProperty title;
	SimpleStringProperty description;
	SimpleDoubleProperty time;
	SimpleStringProperty creationDate;

	HBox actions = new HBox();
	HBox timeView = new HBox();

	TaskModel(String title, String description, String creationDate, Double totalT) {
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.creationDate = new SimpleStringProperty(creationDate);
		this.time = new SimpleDoubleProperty(totalT);

		Label label = new Label();
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(label.textProperty(), time, converter);
		timeView.getChildren().add(label);
		timeView.setAlignment(Pos.CENTER);

		StopWatch stopWatch = new StopWatch(totalT, TimeUnit.HOURS);
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			time.set(stopWatch.getTime(StopWatch.TimeUnit.HOURS));

		}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		Button actionButton = new Button("Start");
		Button resetButton = new Button("Reset");

		actionButton.setStyle("-fx-background-color: #90EE90");
		actionButton.setOnAction(event -> {
			if (stopWatch.isStopped()) {
				actionButton.setText("Stop");
				actionButton.setStyle("-fx-background-color: #ff8040");
				stopWatch.start();
			} else if (stopWatch.isStarted()) {
				actionButton.setText("Start");
				actionButton.setStyle("-fx-background-color: #90EE90");
				stopWatch.stop();
			}
		});
		resetButton.setOnAction(event -> {
			stopWatch.reset();

		});
		actions.getChildren().addAll(actionButton, resetButton);
		actions.setSpacing(5);
	}

	public ContextMenu getMenu() {
		MenuItem newRow = new MenuItem("New");
		newRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(newRow.getText());
			}
		});
		MenuItem copyRow = new MenuItem("Copy");
		copyRow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(copyRow.getText());
			}
		});
		MenuItem deleteRow = new MenuItem("Delete");
		deleteRow.setGraphic(new Circle(5));

		deleteRow.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(deleteRow.getText());
			}
		});

		ContextMenu contextMenu = new ContextMenu(newRow, copyRow, deleteRow);
		contextMenu.setStyle("-fx-min-width: 150;");
		return contextMenu;
	}

	public HBox getTimeView() {

		return timeView;
	}

	public HBox getActions() {
		return actions;
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("title", title.get());
		map.put("description", description.get());
		map.put("time", time.get());
		map.put("creationDate", creationDate.get());
		return map;
	}

	public static TaskModel deserialize(Map<String, Object> task) {
		String title = task.get("title").toString();
		String description = task.get("description").toString();
		String creationDate = task.get("creationDate").toString();
		double totalTime = (double) task.get("time");
		return new TaskModel(title, description, creationDate, totalTime);
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title = new SimpleStringProperty(title);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}

	public double getTime() {
		return time.get();
	}

	public String getCreationDate() {
		return creationDate.get();
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = new SimpleStringProperty(creationDate);
	}

}
