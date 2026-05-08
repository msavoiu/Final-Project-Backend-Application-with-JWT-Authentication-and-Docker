package com.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customerOrder")
public class CustomerOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datePlaced")
    private LocalDate datePlaced;

    @Column(name = "readyForPickup")
    private Boolean readyForPickup;

    // N skates <-> N orders
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "customer_order_skates",
        joinColumns = @JoinColumn(name = "customerOrderId"),
        inverseJoinColumns = @JoinColumn(name = "skateId")
    )
    private List<Skate> skates = new ArrayList<>();

    // N blades <-> N orders
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "customer_order_blades",
        joinColumns = @JoinColumn(name = "customerOrderId"),
        inverseJoinColumns = @JoinColumn(name = "bladeId")
    )
    private List<Blade> blades = new ArrayList<>();

    // N orders -> 1 customer (user)
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

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

    public Boolean getReadyForPickup() {
        return readyForPickup;
    }

    public void setReadyForPickup(Boolean readyForPickup) {
        this.readyForPickup = readyForPickup;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
