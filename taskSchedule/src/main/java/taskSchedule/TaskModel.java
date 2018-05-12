package taskSchedule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

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

	TaskModel(String title, String description, String creationDate, long totalT) {
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.totalTime = totalT;
		this.creationDate = new SimpleStringProperty(creationDate);
		this.time = new SimpleStringProperty(getTextTime(this.totalTime));

	}

	public HBox getActions() {
		
		StopWatch stopWatch = new StopWatch();
		Timeline timer = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			setTime(getTextTime(this.totalTime + this.getTotalTime()));
			Main.taskTable.getColumns().get(2).setVisible(false);
			Main.taskTable.getColumns().get(2).setVisible(true);
		}), new KeyFrame(Duration.seconds(1)));
		timer.setCycleCount(Animation.INDEFINITE);

		Button actionButton = new Button("Start");
		actionButton.setStyle("-fx-background-color: #90EE90");

		actionButton.setOnAction(event -> {
			System.out.println(stopWatch.isStopped());
			if (stopWatch.isStopped()) {
				actionButton.setText("Stop");
				actionButton.setStyle("-fx-background-color: #ff8040");
				stopWatch.start();
			} else if (stopWatch.isStarted()) {
				actionButton.setText("Start");
				actionButton.setStyle("-fx-background-color: #90EE90");
				stopWatch.reset();
			}
		});

		Button resetButton = new Button("Reset");
		resetButton.setOnAction(event -> {
			totalTime = 0;
			// startTime = new Date().getTime();
		});

		HBox actions = new HBox();
		actions = new HBox();
		actions.getChildren().addAll(actionButton, resetButton);
		actions.setSpacing(5);
		return actions;
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

	public Map<String, Object> serialize() {
		// if (clock.getStatus() == Animation.Status.RUNNING) {
		// totalTime += new Date().getTime() - startTime;
		// }
		Map<String, Object> map = new HashMap<>();
		map.put("title", title.get());
		map.put("description", description.get());
		map.put("totalTime", totalTime);
		map.put("creationDate", creationDate.get());
		return map;
	}

	public static TaskModel deserialize(Map<String, Object> task) {
		String title = task.get("title").toString();
		String description = task.get("description").toString();
		String creationDate = task.get("creationDate").toString();
		long totalTime = (long) task.get("totalTime");
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

}
