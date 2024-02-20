package App;

import java.util.Random;

public class RandomID {

    private String tableID;

    public RandomID(){
        this.tableID = generateID ();
    }

    public String getID() {
        return tableID;
    }

    private String generateID (){

        String id = "";
        Random rg = new Random ();

        for (int i = 0; i < 2; i ++) {
            id += rg.nextInt (10);
        }
        return id;
    }
}