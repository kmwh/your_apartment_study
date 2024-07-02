package com.yamuzinfriends.yourapartment.repositories;

import com.yamuzinfriends.yourapartment.models.ApartmentName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentNameRepository extends JpaRepository<ApartmentName, String>, ApartmentNameRepositoryCustom {
}

