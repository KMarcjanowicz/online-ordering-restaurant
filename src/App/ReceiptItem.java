package App;

public class ReceiptItem {

    private String name;
    private int quantity;
    private float price;

    public void setName(String name) {
        this.name = name;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public ReceiptItem(String name, float price) {
        this.name = name;
        this.price = price;
    }
}
