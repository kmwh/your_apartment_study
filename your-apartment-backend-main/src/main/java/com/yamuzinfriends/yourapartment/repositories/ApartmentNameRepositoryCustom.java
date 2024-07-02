package com.yamuzinfriends.yourapartment.repositories;

import com.yamuzinfriends.yourapartment.models.ApartmentName;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApartmentNameRepositoryCustom {
  @Query(value = "SELECT * FROM apartment_name WHERE MATCH (apartment_name, address) AGAINST (:query IN BOOLEAN MODE)", nativeQuery = true)
  List<ApartmentName> searchByApartmentName(String query);
}
