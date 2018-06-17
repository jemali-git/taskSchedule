package billCreator0.gui.menuBar.fileMenu;

import billCreator0.gui.view.explorer.Explorer;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class FileMenu extends Menu {
	public FileMenu() {
		setText("File");
		setMnemonicParsing(false);
		MenuItem menuItem = new MenuItem("New Template");
		getItems().add(menuItem);
		menuItem.setMnemonicParsing(false);
		menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		menuItem.setOnAction(value -> {
			Explorer.addTemplate(null, null);
		});
	}

	class DataSource {
		SimpleStringProperty serverType;
		SimpleStringProperty serverPath;
		SimpleStringProperty serverPassword;

		public DataSource() {
			serverPath = new SimpleStringProperty();
			serverType = new SimpleStringProperty();
			serverPassword = new SimpleStringProperty();
		}

		public SimpleStringProperty getServerType() {
			return serverType;
		}

		public void setServerType(SimpleStringProperty serverType) {
			this.serverType = serverType;
		}

		public SimpleStringProperty getServerPath() {
			return serverPath;
		}

		public void setServerPath(SimpleStringProperty serverPath) {
			this.serverPath = serverPath;
		}

		public SimpleStringProperty getServerPassword() {
			return serverPassword;
		}

		public void setServerPassword(SimpleStringProperty serverPassword) {
			this.serverPassword = serverPassword;
		}
	}

	// public boolean showPersonEditDialog(Person person) {
	// try {
	//
	// FXMLLoader loader = new FXMLLoader();
	// loader.setLocation(MainApp.class.getResource("view/DataSourceEditDialog.fxml"));
	// AnchorPane anchorPane = (AnchorPane) loader.load();
	// anchorPane.setPrefSize(354.0, 439.0);
	// GridPane gridPane=new GridPane();
	//
	//
	// // Create the dialog Stage.
	// Stage dialogStage = new Stage();
	// dialogStage.setTitle("Edit Person");
	// dialogStage.initModality(Modality.WINDOW_MODAL);
	// //dialogStage.initOwner(primaryStage);
	// Scene scene = new Scene(anchorPane);
	// dialogStage.setScene(scene);
	//
	// // Set the person into the controller.
	// DataSourceEditDialogController controller = loader.getController();
	// controller.setDialogStage(dialogStage);
	// controller.setPerson(person);
	//
	// // Set the dialog icon.
	// dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
	//
	// // Show the dialog and wait until the user closes it
	// dialogStage.showAndWait();
	//
	// return controller.isOkClicked();
	// } catch (IOException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
}
