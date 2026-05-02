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
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",
        nullable = false,
        unique = true,
        length = 200
    )
    private String email;

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

    @Column(name = "passwordHash",
        nullable = false,
        length = 200
    )
    private String passwordHash;

    @Column(name = "role",
        nullable = false,
        length = 200
    )
    private String role;

    // 1 user -> N orders
    @OneToMany(mappedBy = "customerOrder",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    // for JWT
    @Column(nullable = false)
    private Long ownerId;
}
