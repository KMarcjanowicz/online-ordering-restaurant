package App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Menu {

    private String personID;
    private ArrayList<ArrayList<String>> menuList = new ArrayList<>();
    public VBox menuVB;
    private ArrayList<MenuItem> menuItemsList = new ArrayList<>();
    private Controller controller;

    public String getPersonID() {
        return personID;
    }

    public Menu(VBox destination, String id, Controller that) throws FileNotFoundException {

        menuVB = destination;
        personID = id;
        controller = that;

        getMenu();

        //Buttons for choosing courses
        javafx.scene.control.Button b1 = new javafx.scene.control.Button("1 Course Meal");
        javafx.scene.control.Button b2 = new javafx.scene.control.Button("2 Course Meal");
        javafx.scene.control.Button b3 = new Button("3 Course Meal");

        menuVB.getChildren().addAll(b1,b2,b3);
        menuVB.setSpacing(10);

        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("1 course meal");
                try {
                    show1Course();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("2 course meal");
                try {
                    show2Courses();;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("3 course meal");
                try {
                    show3Courses();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getMenu(){

        //get menu form the txt file
        try (BufferedReader br = new BufferedReader(new FileReader("src/App/menu.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                int startIndex = 0;
                ArrayList<String> tmp = new ArrayList<String>();
                for (int i = 0; i < line.length(); i++){
                    if(line.charAt(i) == ',' || (i + 1) == line.length()){ //format the data form the line
                        String substr = line.substring(startIndex, i + 1);
                        substr = substr.replace(',',' ');
                        tmp.add("" + substr);
                        startIndex = i + 1;

                    }
                }
                menuList.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(menuList);
    }

    public void show1Course() throws FileNotFoundException {
        menuVB.getChildren().clear();
        boolean append = false;
        for (ArrayList<String> a : menuList){  //for every element in menu array form a file
            if(a.size() <= 1){
                if(a.get(0).equals("mains")){
                    menuVB.getChildren().addAll(new Label(personID),new Label("Mains:"));
                    append = true;
                }
                else {
                    append = false;
                }
            }
            else {
                if(append){
                    MenuItem item = new MenuItem(a.get(0), a.get(1), a.get(2), "main", personID, this);
                    menuItemsList.add(item);
                    menuVB.getChildren().add(item.getContainer());
                }
            }
        }
    }

    public void  show2Courses() throws FileNotFoundException{
        menuVB.getChildren().clear();
        menuVB.getChildren().addAll(new Label(personID));
        boolean append = false;
        boolean main = false;
        for (ArrayList<String> a : menuList){ //for every element in menu array form a file
            if(a.size() <= 1){
                if(a.get(0).equals("mains")){
                    menuVB.getChildren().addAll(new Label("Mains:"));
                    append = true;
                    main = true;
                }
                else if(a.get(0).equals("appetizers")){
                    main = false;
                    menuVB.getChildren().addAll(new Label("Appetizers:"));
                    append = true;
                }
                else {
                    main = false;
                    append = false;
                }
            }
            else {
                if(append){
                    if(main){
                        MenuItem item = new MenuItem(a.get(0), a.get(1), a.get(2), "main", personID, this);
                        menuItemsList.add(item);
                        menuVB.getChildren().add(item.getContainer());
                    }
                    else {
                        MenuItem item = new MenuItem(a.get(0), a.get(1), a.get(2), "appet", personID, this);
                        menuItemsList.add(item);
                        menuVB.getChildren().add(item.getContainer());
                    }
                }
            }
        }
    }

    public void show3Courses() throws FileNotFoundException{
        menuVB.getChildren().clear();
        menuVB.getChildren().addAll(new Label(personID));

        boolean main = false;
        boolean dess = false;
        boolean appet = false;

        for (ArrayList<String> a : menuList){ //for every element in menu array form a file
            if(a.size() <= 1){
                if(a.get(0).equals("mains")){
                    menuVB.getChildren().addAll(new Label("Mains:"));
                    main = true;
                    dess = false;
                    appet = false;
                }
                else if(a.get(0).equals("appetizers")){
                    menuVB.getChildren().addAll(new Label("Appetizers:"));
                    main = false;
                    dess = false;
                    appet = true;
                }
                else {
                    menuVB.getChildren().addAll(new Label("Desserts:"));
                    main = false;
                    dess = true;
                    appet = false;
                }
            }
            else if(a.size() > 1){
                    if(main){
                        MenuItem item = new MenuItem(a.get(0), a.get(1), a.get(2), "main", personID, this);
                        menuItemsList.add(item);
                        menuVB.getChildren().add(item.getContainer());
                    }
                    else if(appet){
                        MenuItem item = new MenuItem(a.get(0), a.get(1), a.get(2), "appet", personID, this);
                        menuItemsList.add(item);
                        menuVB.getChildren().add(item.getContainer());
                    }
                    else if(dess){
                        MenuItem item = new MenuItem(a.get(0), a.get(1), a.get(2), "dess", personID, this);
                        menuItemsList.add(item);
                        menuVB.getChildren().add(item.getContainer());
                    }
            }
        }
    }
    public void updateOrder(){
        controller.updateOrder(menuItemsList);
    }
}
