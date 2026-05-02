package entities;

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
@Table(name = "brand")
public class Brand {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",
        nullable = false,
        length = 200
    )
    private String name;

    @Column(name = "phoneNumber",
        nullable = false,
        length = 200
    )
    private String phoneNumber;

    @Column(name = "email",
        nullable = false,
        length = 200
    )
    private String email;

    @Column(name = "address",
        nullable = false,
        length = 255
    )
    private String address;

    // 1 brand -> N skates
    @OneToMany(mappedBy = "brand",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Skate> skates = new ArrayList<>();

    // 1 brand -> N blades
    @OneToMany(mappedBy = "brand",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<Blade> blades = new ArrayList<>();

    // for JWT
    @Column(nullable = false)
    private Long ownerId;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
