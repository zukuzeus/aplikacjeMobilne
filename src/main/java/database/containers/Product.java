package database.containers;

public class Product {
	private String product;
	private String store;
	private double prize;
	private int quantity;

	public Product() {

	}

	public Product(String product, String shop, double prize, int quantity) {
		this.product = product;
		this.store = shop;
		this.prize = prize;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product{" +
				"product='" + product + '\'' +
				", store='" + store + '\'' +
				", prize=" + prize +
				", quantity=" + quantity +
				'}';
	}

	public String getProductName() {
		return product;
	}

	public void setProductName(String product) {
		this.product = product;
	}

	public String getShop() {
		return store;
	}

	public void setShop(String shop) {
		this.store = shop;
	}

	public double getPrize() {
		return prize;
	}

	public void setPrize(double prize) {
		this.prize = prize;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
