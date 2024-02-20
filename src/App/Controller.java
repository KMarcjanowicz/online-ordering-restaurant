package App;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javafx.scene.paint.Color;

public class Controller {

    //XML Elements
    public TextField numberOfPeopleTB;
    public VBox tableContents;
    public VBox menuVB;
    public VBox orderVB;
    public Label tableNumber;
    public Button payByCash;
    public Button submit;
    public Button adminLogOn;

    //Classes

    private Menu menu;
    private VisualElements visualElements = new VisualElements();
    private Table table;
    private Stage primaryStage;

    //variables
    Controller this_c = this;
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
    float globalCalories;
    float globalPrice;
    int numberOfPpl;

    //create table and add buttons for the people at the table to make their orders
    public void createTable(ActionEvent actionEvent) throws FileNotFoundException {
        tableContents.setSpacing(10);                                   //some layout adjustment
        tableContents.setPadding(new Insets(20));
        tableContents.getChildren().clear();
        tableNumber.setText("Order");
        orderVB.getChildren().clear();
        menuVB.getChildren().clear();
        payByCash.setDisable(true);

        //table creation, with unique table id and the array of people, every person has their own order array

        try{
            numberOfPpl = Integer.parseInt(numberOfPeopleTB.getText());
        }catch (Exception e){
            System.out.println(e);
            tableContents.getChildren().add(new Label("Wrong input provided"));
            return;
        }

        if(numberOfPpl <= 0){
            tableContents.getChildren().add(new Label("Is there even a person at this point?"));
            return;
        }

        RandomID newID = new RandomID();
        table = new Table(numberOfPpl, newID.getID());
        for (int i = 0; i < table.getPeople().size(); i++) {
            //create button to choose the course amount
            Button pButton = new Button("Make order...", visualElements.getImageView("src/App/img/icon.png"));
            pButton.setId("person_" + i);
            pButton.setPrefWidth(250);

            pButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    System.out.println(pButton.getId());
                    menuVB.getChildren().clear();
                    try {
                        menu = new Menu(menuVB, pButton.getId(), this_c);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tableContents.getChildren().add(pButton);
        }
    }

    public void updateOrder(ArrayList<MenuItem> order) { //update order for the people at the table
        String id = menu.getPersonID();
        ArrayList<MenuItem> formattedOrder = new ArrayList<>();
        for(int i = 0; i < table.getPeople().size(); i++){
            if(table.getPeople().get(i).getId().equals(id)){         //for every person with the same id as the menu id
                for(int j = 0; j < order.size(); j++){               //for every dish in the order
                    if(order.get(j).getQuantity() > 0){
                        formattedOrder.add(order.get(j));           //add the order to the formatted tmp array
                    }
                }
                table.getPeople().get(i).setOrder(formattedOrder);  //set the order for that person
            }
        }
        displayOrder();
    }

    public void displayOrder(){

        payByCash.setDisable(false);                    //some layout adjustment
        orderVB.getChildren().clear();

        tableNumber.setText("Order for table: " + table.getId());
        globalCalories = 0;
        globalPrice = 0;

        for(int i = 0; i < table.getPeople().size(); i++){          //for showing the order with price and cals
            Label labelID = new Label("============== " + table.getPeople().get(i).getId() + ": ===============");
            labelID.setTextFill(Color.web("#404040", 0.8));
            orderVB.getChildren().add(labelID);
            float caloricValuePerson = 0;
            float payPerson = 0;
            for (MenuItem dish : table.getPeople().get(i).getOrder()) {         //count all the important stuff
                Label labelDish = new Label(dish.getDish() + " : " + dish.getQuantity());
                orderVB.getChildren().add(labelDish);
                caloricValuePerson += dish.getCals() * dish.getQuantity(); //values per person
                payPerson += dish.getPriceDish() * dish.getQuantity();
                globalPrice += dish.getPriceDish() * dish.getQuantity();
                globalCalories += dish.getCals() * dish.getQuantity();
            }
            Label calsDish = new Label("Caloric value: " + caloricValuePerson + "kcal");
            calsDish.setTextFill(Color.web("#808080", 0.8));
            orderVB.getChildren().add(calsDish);
            Label priceDish = new Label("Price for this: " + currencyFormat.format(payPerson));
            priceDish.setTextFill(Color.web("#808080", 0.8));
            orderVB.getChildren().add(priceDish);
        }
        orderVB.getChildren().add(new Label(" "));
        Label calsDish = new Label("Total Calorific Value for This Order: " + globalCalories + "kcal");
        orderVB.getChildren().add(calsDish);
        Label priceDish = new Label("Total Price for This Order: " + currencyFormat.format(globalPrice));
        orderVB.getChildren().add(priceDish);
    }

    public void payForOrder(ActionEvent actionEvent) {
        submit.setDisable(false);
        PopUpReceipt popup = new PopUpReceipt(table.getPeople());
        popup.initOwner(primaryStage);
        popup.show();
    }

    public void adminLogOn(ActionEvent actionEvent) {
        AdminLogin adminLogin = new AdminLogin("admin", "admin");
        adminLogin.initOwner(primaryStage);
        adminLogin.show();
    }

    public void sendToKitchen(ActionEvent actionEvent) {  //reset button
        numberOfPeopleTB.setText("");
        tableNumber.setText("Order:");
        orderVB.getChildren().clear();
        tableContents.getChildren().clear();
        menuVB.getChildren().clear();
    }

}

