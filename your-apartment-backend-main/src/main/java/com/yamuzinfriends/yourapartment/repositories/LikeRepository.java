package com.yamuzinfriends.yourapartment.repositories;

import com.yamuzinfriends.yourapartment.models.Apartment;
import com.yamuzinfriends.yourapartment.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByApartmentAndIpAddress(Apartment apartment, String ipAddress);
    Optional<Like> findFirstByApartmentAndIpAddress(Apartment apartment, String ipAddress);
}
