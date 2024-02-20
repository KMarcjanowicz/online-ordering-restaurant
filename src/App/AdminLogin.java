package App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class AdminLogin extends Stage {

    private VBox dialogVbox;
    // This pop up window is modal

    public AdminLogin(String passwd, String login) {

        this.initModality(Modality.APPLICATION_MODAL);

        dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        Text loginLabel = new Text("Admin Login: ");
        TextField loginTextField = new TextField ();
        Text passwdLabel = new Text("Admin Password: ");
        PasswordField passwdTextField = new PasswordField();
        VBox msgVBox = new VBox();
        msgVBox.setAlignment(Pos.CENTER);
        Button btn = new Button("Login");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(loginTextField.getText().equals(login) && passwdTextField.getText().equals(passwd)){
                    createAddDishLayout();
                }
                else {
                    Text msgLabel = new Text("Wrong password/login");
                    msgVBox.getChildren().add(msgLabel);
                }
            }
        });

        dialogVbox.getChildren().addAll(loginLabel,loginTextField,passwdLabel,passwdTextField);
        dialogVbox.getChildren().add(btn);
        dialogVbox.getChildren().add(msgVBox);
        Scene dialogScene = new Scene(dialogVbox, 200, 400);
        this.setScene(dialogScene);
    }

    public void createAddDishLayout(){

        dialogVbox.getChildren().clear();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "appetizers",
                        "mains",
                        "desserts"
                );
        final ComboBox comboBox = new ComboBox(options);

        Text nameLabel = new Text("Name the dish: ");
        TextField nameTextField = new TextField ();

        Text priceLabel = new Text("Add the price of the dish: ");
        TextField priceTextField = new TextField ();

        Text calsLabel = new Text("Add the calorific value: ");
        TextField calsTextField = new TextField ();

        Button btn = new Button("Add the Dish");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    writeAfterNthLine("src/App/menu.txt", nameTextField.getText() ,Float.parseFloat(priceTextField.getText()), Float.parseFloat(calsTextField.getText()) , comboBox.getSelectionModel().getSelectedItem().toString());
                    Text resultLabel = new Text("Dish added!");
                    createAddDishLayout();
                    dialogVbox.getChildren().add(resultLabel);
                } catch (Exception e) {
                    e.printStackTrace();
                    Text resultLabel = new Text("Something went wrong");
                    createAddDishLayout();
                    dialogVbox.getChildren().add(resultLabel);
                }
            }
        });

        dialogVbox.getChildren().addAll(nameLabel, nameTextField, priceLabel, priceTextField, calsLabel, calsTextField, comboBox,btn);
    }

    public void writeAfterNthLine(String filename, String name, float price, float cals, String option) throws IOException{
        File file = new File(filename);
        File temp = File.createTempFile("temp-file-name", ".tmp");
        BufferedReader br = new BufferedReader(new FileReader( file ));
        PrintWriter pw =  new PrintWriter(new FileWriter( temp ));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            pw.println(line);
//            if(lineCount==lineno){
//                pw.println(text);
//            }
//            lineCount++;
            if(line.equals(option)){
                String text = name + "," + price + "," + cals;
                pw.println(text);
            }
        }
        br.close();
        pw.close();
        file.delete();
        temp.renameTo(file);
    }
}
