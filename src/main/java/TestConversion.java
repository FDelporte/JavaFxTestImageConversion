import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class TestConversion extends Application {

    @Override
    public void start(Stage stage) {
        var label = new Label("Testing screenshot methods");
        var button = new Button("Save screenshots");
        var imgHolder = new VBox();
        var holder = new VBox(label, button, imgHolder);

        var scene = new Scene(holder, 640, 480);
        button.setOnAction(e -> makeScreenshots(scene, imgHolder));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void makeScreenshots(Scene scene, VBox imgHolder) {
        imgHolder.getChildren().clear();
        imgHolder.getChildren().add(new Label(LocalDateTime.now().toString()));

        Image sceneSnapshot = scene.snapshot(new WritableImage((int) scene.getWidth(), (int) scene.getHeight()));

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        PngEncoderFX encoder = new PngEncoderFX(sceneSnapshot, true);
        byte[] bytes = encoder.pngEncode();
        try {
            Files.write(Path.of("/tmp/test_" + System.currentTimeMillis() + ".png"), bytes);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
