package com.yamuzinfriends.yourapartment.services;

import com.yamuzinfriends.yourapartment.dtos.GetApartmentNamesRequestDto;
import com.yamuzinfriends.yourapartment.models.ApartmentName;
import com.yamuzinfriends.yourapartment.repositories.ApartmentNameRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentNameService {
  private final ApartmentNameRepository apartmentNameRepository;

  @PersistenceContext
  private EntityManager entityManager;
  public ApartmentNameService(ApartmentNameRepository  repository) {
    this.apartmentNameRepository = repository;
  }

  public List<ApartmentName> searchApartmentNames(GetApartmentNamesRequestDto dto) {
    return this.apartmentNameRepository.searchByApartmentName(dto.getQuery());
  }
}
