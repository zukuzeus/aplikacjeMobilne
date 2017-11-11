package database.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.function.Predicate;

public class ProductService {
	private List<Product> productsList = new ArrayList<Product>();
	
	public List<Product> getAllProducts() {
		return this.productsList;
	}

	public void createNewProduct(String productName, String shop, double prize, int quantity) {
		if (!isProductListContainsProduct(productName)) {
			Product newProduct = new Product(productName, shop, prize, quantity);
			this.productsList.add(newProduct);
		}
	}

	public void updateProductQuantity(String productName, int quantity) {
		Product product = getProduct(productName);
		Product alteredProduct = product;
		alteredProduct.setQuantity(quantity);
		replaceIf(this.productsList, x -> x.equals(product), alteredProduct);

	}

	private <T> void replaceIf(List<T> list, Predicate<T> predicate, T replacement) {
		for (int i = 0; i < list.size(); ++i)
			if (predicate.test(list.get(i)))
				list.set(i, replacement);
	}

	public Product getProduct(String productname) {
		Iterator<Product> itr = this.productsList.iterator();
		while (itr.hasNext()) {
			Product product = itr.next();
			if (product.getProductName().equals(productname)) {
				return product;
			}
		}
		return null;
	}

	public void addProduct(Product product) {
		this.productsList.add(product);
	}

	private boolean isProductListContainsProduct(String productName) {
		Iterator<Product> itr = this.productsList.iterator();
		while (itr.hasNext()) {
			Product product = itr.next();
			if (product.getProductName().equals(productName)) {
				return true;
			}
		}
		return false;
	}

}
