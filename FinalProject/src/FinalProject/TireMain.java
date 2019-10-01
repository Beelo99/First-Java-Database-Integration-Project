package FinalProject;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * The TireMain Program is responsible for creating a Stage
 * and running it. This program is a tire manager program
 * that allows you to add, remove, view wheels or tires
 * as well as add & view the details.
 * 
 * @author Belal Kaoukji
 * @version 1.0
 * @since 2019-04-11
 */
public class TireMain extends javafx.application.Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		URL res = this.getClass().getResource("FXMLTireManager.fxml");
		Parent root = FXMLLoader.load(res);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("TireStyle.css").toExternalForm());
		arg0.setScene(scene);
		arg0.sizeToScene();
		arg0.setTitle("Tire Manager");
		arg0.show();
	}
}