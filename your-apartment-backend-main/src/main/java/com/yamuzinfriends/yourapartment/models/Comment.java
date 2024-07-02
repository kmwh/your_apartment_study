package com.yamuzinfriends.yourapartment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int _id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    public String nickname;

    @Column(name="comment", columnDefinition = "VARCHAR(255)", nullable = false)
    public String comment;

    @Column(name="password", columnDefinition = "VARCHAR(255)", nullable = false)
    public String password;

    @Column(name="created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    @JsonBackReference
    public Apartment apartment;
}
