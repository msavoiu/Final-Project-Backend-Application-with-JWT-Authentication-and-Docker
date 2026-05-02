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
@Table(name = "blade")
public class Blade {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",
        nullable = false,
        length = 200
    )
    private String name;

    @Column(name = "length")
    private int length;

    @Column(name = "rocker")
    private int rocker;
    
    @Column(name = "price")
    private Double price;

    // N blades -> 1 brand
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRocker() {
        return rocker;
    }

    public void setRocker(int rocker) {
        this.rocker = rocker;
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
