package FinalProject;

public class Tires extends Item {
	
	private int width;
	private String type;
	private String treadLife;
	public Tires() {
		
	}
	public Tires(String brand, double price, int size, String itemType) {
		super(brand, price, size, itemType);
		super.getCounter();

	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public String getTreadLife() {
		return treadLife;
	}
	public void setTreadLife(String treadLife) {
		this.treadLife = treadLife;
	}
	public String toString() {
		return super.toString();
	}
	public String saveString() {
		return super.saveString() + String.format("%s;%f;%s;%s;%d;%s;%d;", super.getpDate(), super.getpPrice(), super.getpFrom(), type, width, treadLife, super.getCounter()) ;
	}
}