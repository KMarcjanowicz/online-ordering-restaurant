package App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class MenuItem extends Node {

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
    private HBox container;
    private String dishType;
    private String personID;
    private int quantity = 0;
    private String dish;
    private float priceDish;
    private float cals;

    public String getDishType() {
        return dishType;
    }

    public String getPersonID() {
        return personID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDish() {
        return dish;
    }

    public float getPriceDish() {
        return priceDish;
    }

    public float getCals() {
        return cals;
    }


    //create a menu item in the menu
    public MenuItem(String item, String price, String calories, String type, String id, Menu that) throws FileNotFoundException {

        dishType = type;
        personID = id;
        dish = item;
        priceDish = Float.parseFloat(price);
        cals = Float.parseFloat(calories);

        container = new HBox(10);
        container.setPrefSize(600,100);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10));
        container.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, Color.LIGHTGRAY, Color.LIGHTGRAY, Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));



        Label itemLabel = new Label(item);
        itemLabel.setFont(new Font("Roboto", 12));
        itemLabel.setMinWidth(180);
        itemLabel.setMaxWidth(180);
        itemLabel.setWrapText(true);

        Label priceLabel = new Label("Price: "  + currencyFormat.format(Float.parseFloat(price)));
        priceLabel.setFont(new Font("Roboto", 12));
        priceLabel.setMinWidth(100);

        Label calLabel = new Label("Calories: " + calories);
        calLabel.setFont(new Font("Roboto", 12));
        calLabel.setMinWidth(100);

        FileInputStream input = new FileInputStream("src/App/img/question.png");
        if(dishType.equals("main")){
            input = new FileInputStream("src/App/img/main.png");
        }
        else if(dishType.equals("appet")){
            input = new FileInputStream("src/App/img/appet.png");
        }
        else if(dishType.equals("dess")){
            input = new FileInputStream("src/App/img/dess.png");
        }
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Button addButton = new Button("+");
        Label quanLabel = new Label(Integer.toString(quantity));
        quanLabel.setMinWidth(20);
        quanLabel.setAlignment(Pos.CENTER);
        Button remButton = new Button("-");

        container.getChildren().addAll(imageView, itemLabel, priceLabel, calLabel, remButton, quanLabel, addButton);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                quantity += 1;
                quanLabel.setText(Integer.toString(quantity));
                that.updateOrder();
            }
        });

        remButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(quantity > 0){
                    quantity -= 1;
                    quanLabel.setText(Integer.toString(quantity));
                    that.updateOrder();
                }
            }
        });
    }

    public HBox getContainer() {
        return container;
    }
}
