package eCommerceE2E;

import java.util.List;

public class OrderSummary {
	
	List<String> orders;
	List<String> productOrderId;
	String message;
	
	public List<String> getOrders() {
		return orders;
	}
	public void setOrders(List<String> orders) {
		this.orders = orders;
	}
	public List<String> getProductOrderId() {
		return productOrderId;
	}
	public void setProductOrderId(List<String> productOrderId) {
		this.productOrderId = productOrderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
