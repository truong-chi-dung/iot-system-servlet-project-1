package nidec.beans;

public class Product {
	private String productId;
	private String productName;
	private int targetDay;
	private int targetWeek;
	private int targetMonth;
	
	public Product() {
		
	}
	
	public Product(String productId, String productName, int targetDay, int targetWeek, int targetMonth) {
		this.productId = productId;
		this.productName = productName;
		this.targetDay = targetDay;
		this.targetMonth = targetMonth;
		this.targetWeek = targetWeek;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getTargetDay() {
		return targetDay;
	}
	
	public void setTargetDay(int targetDay) {
		this.targetDay = targetDay;
	}

	public int getTargetWeek() {
		return targetWeek;
	}
	
	public void setTargetWeek(int targetWeek) {
		this.targetWeek = targetWeek;
	}
	public int getTargetMonth() {
		return targetMonth;
	}
	
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
}
