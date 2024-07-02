package com.yamuzinfriends.yourapartment.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Apartment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int _id;

  @Column(columnDefinition = "VARCHAR(20)", nullable = false)
  public String nickname;

  @Column(name="ip_address", columnDefinition = "VARCHAR(128)", nullable = false)
  public String ipAddress;

  @ManyToOne
  @JoinColumn(name = "apartment_name_id")
  public ApartmentName apartmentName;

  @Column(columnDefinition = "FLOAT", name="gpt_score", nullable = false)
  public float gptScore;

  @Column(columnDefinition = "VARCHAR(30)", name="popular_cursor", nullable = false)
  public String popularCursor;

  @Column(columnDefinition = "TEXT", nullable = false)
  public String password;

  @Column(name="created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
  @Temporal(TemporalType.DATE)
  public Date createdAt;

  @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  public List<Comment> comments;

  @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  public List<Like> likes;
}
