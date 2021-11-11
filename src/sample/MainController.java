package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import java.io.IOException;

public class MainController {
    @FXML
    private Circle start;

    @FXML
    private void onStartClicked() {
        Scene previousScene = Main.getMainStage().getScene();
        Scene scene = Controller.getScene();
        Main.getMainStage().setScene(scene);
        scene.setOnKeyPressed(event -> { if(event.getCode().equals(KeyCode.ESCAPE)) Main.getMainStage().setScene(previousScene); });
        start.setOpacity(0);
    }

    @FXML
    private void onStartPressed() {
        start.setOpacity(1);
    }

    public static Scene getScene() {
        Parent root = loadFXML();
        assert root != null;
        return new Scene(root);
    }

    private static Parent loadFXML() {
        try {
            return FXMLLoader.load(MainController.class.getResource("mainwin.fxml"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new Pane();
        }
    }
}
