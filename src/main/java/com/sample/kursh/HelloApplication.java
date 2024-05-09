package com.sample.kursh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.setTitle("Cipher App");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
