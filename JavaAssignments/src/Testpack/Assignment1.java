package Testpack;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Assignment1 extends javafx.application.Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		URL res = this.getClass().getResource("PhoneBook.fxml");
		Parent root = FXMLLoader.load(res);
		Scene scene = new Scene(root);
		arg0.setScene(scene);
		arg0.sizeToScene();
		arg0.setTitle("PhoneBook");
		arg0.show();
		
		
	}
}
