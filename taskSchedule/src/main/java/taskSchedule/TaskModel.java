package taskSchedule; 

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class TaskModel {

	SimpleStringProperty title;
	SimpleStringProperty description;
	SimpleStringProperty time;
	SimpleStringProperty creationDate;

	long totalTime;
	long startTime;
	Timeline clock;

	Button actionButton = new Button("Start");
	Button resetButton = new Button("Reset");

	TaskModel(String title, String description, String creationDate, long totalT) {
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.totalTime = totalT;
		this.creationDate = new SimpleStringProperty(creationDate);

		this.time = new SimpleStringProperty(getTextTime(this.totalTime));

		clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

			setTime(getTextTime(this.totalTime + new Date().getTime() - startTime));
			Main.taskTable.getColumns().get(2).setVisible(false);
			Main.taskTable.getColumns().get(2).setVisible(true);

		}), new KeyFrame(Duration.seconds(2)));
		clock.setCycleCount(Animation.INDEFINITE);

		actionButton = new Button("Start");
		actionButton.setStyle("-fx-background-color: #90EE90");

		resetButton = new Button("Reset");

		actionButton.setOnAction(event -> {
			action();
		});
		resetButton.setOnAction(event -> {
			reset();
		});

	}

	public HBox getActions() {
		HBox actions = new HBox();
		actions = new HBox();
		actions.getChildren().addAll(actionButton, resetButton);
		actions.setSpacing(5);
		return actions;
	}

	public void action() {
		if (clock.getStatus() == Animation.Status.STOPPED) {
			actionButton.setText("Stop");
			actionButton.setStyle("-fx-background-color: #ff8040");
			startTime = new Date().getTime();
			clock.play();
		} else if (clock.getStatus() == Animation.Status.RUNNING) {
			actionButton.setText("Start");
			actionButton.setStyle("-fx-background-color: #90EE90");
			totalTime += new Date().getTime() - startTime;
			clock.stop();
		}
	}

	String getTextTime(long time) {
		time = time / 1000;
		long hours = time / (60 * 60);
		time = time % (60 * 60);
		long minutes = (time / 60);
		time = time % 60;
		long seconds = time;
		return ("Seconds: " + seconds + " Minutes: " + minutes + " Hours: " + hours);
	}

	public Map<String, Object> getSerialization() {
		if (clock.getStatus() == Animation.Status.RUNNING) {
			totalTime += new Date().getTime() - startTime;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("title", title.get());
		map.put("description", description.get());
		map.put("totalTime", totalTime);
		map.put("creationDate", creationDate.get());
		return map;
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

	public String getTime() {
		return time.get();
	}

	public void setTime(String time) {
		this.time = new SimpleStringProperty(time);
	}

	public long getTotalTime() {
		return totalTime;
	}

	public String getCreationDate() {
		return creationDate.get();
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = new SimpleStringProperty(creationDate);
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public void reset() {
		totalTime = 0;
		startTime = new Date().getTime();
	}

}
