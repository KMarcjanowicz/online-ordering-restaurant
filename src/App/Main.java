package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MenuScreen.fxml"));
        primaryStage.setTitle("Menu Application");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
        System.out.println(System.getProperty("user.dir"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
