package dto;

public class BladeDTO {
    private String name;
    private int length;
    private int rocker;
    private double price;
    private Long brandId;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public int getRocker() { return rocker; }
    public void setRocker(int rocker) { this.rocker = rocker; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }
}