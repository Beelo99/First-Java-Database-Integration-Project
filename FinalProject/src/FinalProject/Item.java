package FinalProject;

public class Item {

	private String brand;
	private String pDate;
	private String pFrom;
	private String moreDetails;
	private String itemType;
	private String invalid = "Invalid Value; Must Be Bigger Than 0";
	private int size;
	private int counter = 0;
	private double pPrice;
	private double price;
	
	public Item() {
		brand = "unknown";
		price = 0.0;
		size = 0;
	}
	public Item(String brand, double price, int size, String itemType) {
		this.setBrand(brand);
		this.setPrice(price);
		this.setSize(size);
		this.setItemType(itemType);
	}
//	public int getItemID() {
//		return itemID;
//	}
//	public void setItemID(int itemID) {
//		this.itemID = itemID;
//	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		if (price < 0.0) {
			System.out.println(invalid);
		} else {
		this.price = price;
		}
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		if (size <= 0) {
			System.out.println(invalid);
		}
		this.size = size;
	}
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = pDate;
	}
	public double getpPrice() {
		return pPrice;
	}
	public void setpPrice(double pPrice) {
		this.pPrice = pPrice;
	}
	public String getpFrom() {
		return pFrom;
	}
	public void setpFrom(String pFrom) {
		this.pFrom = pFrom;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter() {
		counter++;
	}
	public String toString() {
		return String.format("\t\tBrand: %s\t\tPrice: $%.2f\t\tDiameter: %d\"\t\tType: %s", brand, price, size, itemType);
	}
	public String saveString() {
		return String.format("%s;%.2f;%d;%s;", brand, price, size, itemType);
	}
}
