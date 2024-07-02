package com.yamuzinfriends.yourapartment.repositories;

import com.yamuzinfriends.yourapartment.models.Apartment;
import com.yamuzinfriends.yourapartment.models.ApartmentName;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    @Query("SELECT COUNT(l) FROM Like l WHERE l.apartment._id = :apartmentId")
    int countLikesByApartmentId(@Param("apartmentId") int apartmentId);

    Apartment findByApartmentName(ApartmentName name);
}
