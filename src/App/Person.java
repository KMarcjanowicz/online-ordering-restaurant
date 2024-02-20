package App;

import javafx.util.Pair;

import java.util.ArrayList;

public class Person {

    private String id;
    private ArrayList<MenuItem> order = new ArrayList<>();

    //setID
    public Person(int id) {
        this.id = "person_" + id;
    }

    //getId
    public String getId() {
        return id;
    }

    //getOrder
    public ArrayList<MenuItem> getOrder() {
        return order;
    }

    //setOrder
    public void setOrder(ArrayList<MenuItem> order) {
        this.order = order;
    }
}
