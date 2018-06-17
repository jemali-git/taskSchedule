package billCreator;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/*w ww . ja v  a2s .  c om*/
public class Test2 extends Application {

	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 500, 200);
		stage.setScene(scene);

		GridPane grid = new GridPane();
		grid.setHgap(10);

		scene.setRoot(grid);

		grid.addColumn(0, new Circle(20));

		final Separator sepHor = new Separator();
		
		sepHor.setHalignment(HPos.CENTER);
		sepHor.setOrientation(Orientation.VERTICAL);
		
		grid.addColumn(1, sepHor);
		grid.addColumn(2, new Circle(20));

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}