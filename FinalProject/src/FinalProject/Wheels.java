package FinalProject;

public class Wheels extends Item {

	private int offset;
	private String boltPattern;
	private double width;
	public Wheels() {
		super();
	}
	public Wheels(String brand, double price, int size, String itemType) {
		super(brand, price, size, itemType);
		super.getCounter();
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public String getBoltPattern() {
		return boltPattern;
	}
	public void setBoltPattern(String boltPattern) {
		this.boltPattern = boltPattern;
	}
	public String toString() {
		return super.toString();
	}
	public String saveString() {
		return super.saveString() + String.format("%s;%f;%s;%s;%.1f;%d;%d;", super.getpDate(), super.getpPrice(), super.getpFrom(), boltPattern, width, offset, super.getCounter()) ;
	}
}
