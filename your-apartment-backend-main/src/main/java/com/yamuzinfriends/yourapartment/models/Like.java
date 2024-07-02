package com.yamuzinfriends.yourapartment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int _id;

    @Column(name="ip_address", columnDefinition = "VARCHAR(128)", nullable = false)
    public String ipAddress;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    @JsonBackReference
    public Apartment apartment;
}
