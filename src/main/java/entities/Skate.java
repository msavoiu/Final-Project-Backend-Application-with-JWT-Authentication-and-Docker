package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "skate")
public class Skate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",
        nullable = false,
        length = 200
    )
    private String name;

    @Column(name = "color",
        nullable = false,
        length = 200
    )
    private String color;

    @Column(name = "size",
        nullable = false,
        length = 200
    )
    private String size;

    @Column(name = "price")
    private Double price;

    // N skates -> 1 brand
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId",
        nullable = false)
    private Brand brand;

    // for JWT
    @Column(name = "ownerEmail",
        nullable = false,
        length = 200
    )
    private String ownerEmail;


    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
