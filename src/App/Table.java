package App;

import java.util.ArrayList;

public class Table {

    private int size;
    private String id;
    private ArrayList<Person> people = new ArrayList<Person>();

    public String getId() {
        return id;
    }

    public Table(int size, String id) {
        this.size = size;
        this.id = id;

        for(int i = 0; i < this.size; i++){
            Person p = new Person(i);
            people.add(p);
        }
    }

    public ArrayList<Person> getPeople() {
        return people;
    }
}
