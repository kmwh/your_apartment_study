package com.yamuzinfriends.yourapartment.repositories;

import com.yamuzinfriends.yourapartment.models.ApartmentName;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public class ApartmentNameRepositoryImpl implements ApartmentNameRepositoryCustom {

  public List<ApartmentName> searchByApartmentName(String query) {
    return null;
  }
}
