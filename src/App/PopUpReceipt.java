package App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PopUpReceipt extends Stage {

    private float globalPrice = 0;
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);;
    VBox dialogVbox;
    private ArrayList<ReceiptItem> receiptList = new ArrayList<ReceiptItem>();
    private ArrayList<String> alreadyCounted = new ArrayList<>();

    public PopUpReceipt(ArrayList<Person> people) {
        this.initModality(Modality.APPLICATION_MODAL);
        dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        Button btn = new Button("Close");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PopUpReceipt.this.close();
            }
        });

        createReceipt(people);

        dialogVbox.getChildren().add(btn);

        Scene dialogScene = new Scene(dialogVbox, 400, 800);
        this.setScene(dialogScene);
    }

    private void createReceipt(ArrayList<Person> people){
        for(int i = 0; i < people.size(); i++){
            for (MenuItem dish : people.get(i).getOrder()) {
                if(!alreadyCounted.contains(dish.getDish())){       //for every item which was not mentioned before form the user's orders
                    System.out.println(dish.getDish());
                    alreadyCounted.add(dish.getDish());
                    ReceiptItem item = new ReceiptItem(dish.getDish(), dish.getPriceDish());
                    receiptList.add(item);
                }
            }
        }
        for(ReceiptItem item : receiptList){
            //for every item in receipt list
            for(int i = 0; i < people.size(); i++){
                for (MenuItem dish : people.get(i).getOrder()) {
                    //for every dish in the whole table order
                    if(item.getName().equals(dish.getDish())){
                        item.addQuantity(dish.getQuantity());
                    }
                }
            }
        }
        for(ReceiptItem item : receiptList) {
            //print receipt
            dialogVbox.getChildren().add(new Label(item.getQuantity() + " * " + item.getName() + " : " + currencyFormat.format(item.getPrice())));
            globalPrice += item.getPrice() * item.getQuantity();
        }
        Label priceDish = new Label("Total Price for This Order: " + currencyFormat.format(globalPrice));
        dialogVbox.getChildren().add(priceDish);
    }
}
