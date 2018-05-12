package taskSchedule;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

		Label label = new Label("");

		StringConverter<Number> converter = new NumberStringConverter();

		Bindings.bindBidirectional(label.textProperty(), time, converter);
		timeView.getChildren().add(label);
		timeView.setAlignment(Pos.CENTER);

		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			time.set(time.get() + 1.0 / 3600.0);
		}), new KeyFrame(Duration.seconds(1)));

		
		timeline.setCycleCount(Animation.INDEFINITE);

		Button actionButton = new Button("Start");
		Button resetButton = new Button("Reset");

		actionButton.setStyle("-fx-background-color: #90EE90");
		actionButton.setOnAction(event -> {
			if (timeline.getStatus() == Status.STOPPED) {
				actionButton.setText("Stop");
				actionButton.setStyle("-fx-background-color: #ff8040");
				timeline.play();
			} else if (timeline.getStatus() == Status.RUNNING) {
				actionButton.setText("Start");
				actionButton.setStyle("-fx-background-color: #90EE90");
				timeline.stop();
			}
		});
		resetButton.setOnAction(event -> {
			time.set(0);
		});
		actions.getChildren().addAll(actionButton, resetButton);
		actions.setSpacing(5);
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
		map.put("totalTime", time.get());
		map.put("creationDate", creationDate.get());
		return map;
	}

	public static TaskModel deserialize(Map<String, Object> task) {
		String title = task.get("title").toString();
		String description = task.get("description").toString();
		String creationDate = task.get("creationDate").toString();
		double totalTime = (double) task.get("totalTime");
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

// String getTextTime(long time) {
// long hours = time / (60 * 60);
// time = time % (60 * 60);
// long minutes = (time / 60);
// time = time % 60;
// long seconds = time;
// return ("Seconds: " + seconds + " Minutes: " + minutes + " Hours: " + hours);
// }