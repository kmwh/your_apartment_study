package com.yamuzinfriends.yourapartment.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ApartmentName {
  @Id
  public String _id;

  @Column(name="apartment_name", columnDefinition = "VARCHAR(255)", nullable = false)
  public String apartmentName;

  @Column(name="region_name", columnDefinition = "VARCHAR(255)", nullable = false)
  public String regionName;

  @Column(columnDefinition = "VARCHAR(255)", nullable = false)
  public String address;

  @OneToMany(mappedBy = "apartmentName", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Apartment> apartments;
}
