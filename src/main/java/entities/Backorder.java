package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "backorder")
public class Backorder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datePlaced",
        updatable = false
    )
    private LocalDate datePlaced;

    @Column(name = "received")
    private Boolean received;
    
    // 1 backorder -> N brands
    @OneToMany(mappedBy = "backorder",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Brand> brands = new ArrayList<>();

    // 1 backorder -> N skates
    @OneToMany(mappedBy = "backorder",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Skate> skates = new ArrayList<>();

    // 1 backorder -> N blades
    @OneToMany(mappedBy = "backorder",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Blade> blades = new ArrayList<>();

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

    public LocalDate getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(LocalDate datePlaced) {
        this.datePlaced = datePlaced;
    }

    public Boolean getReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Skate> getSkates() {
        return skates;
    }

    public void setSkates(List<Skate> skates) {
        this.skates = skates;
    }

    public List<Blade> getBlades() {
        return blades;
    }

    public void setBlades(List<Blade> blades) {
        this.blades = blades;
    }
}
