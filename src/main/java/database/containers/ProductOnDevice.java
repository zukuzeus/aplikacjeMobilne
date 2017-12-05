package database.containers;

public class ProductOnDevice extends Product {
    private int quantityRemote;

    public ProductOnDevice() {
        super();
    }

    public ProductOnDevice(String product, String shop, double prize, int quantity, int quantityRemote) {
        super(product, shop, prize, quantity);
        this.quantityRemote = quantityRemote;
    }

    public int getQuantityRemote() {
        return quantityRemote;
    }

    public void setQuantityRemote(int quantityRemote) {
        this.quantityRemote = quantityRemote;
    }
}
