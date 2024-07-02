package com.yamuzinfriends.yourapartment.dtos;

import com.yamuzinfriends.yourapartment.models.ApartmentName;

import java.util.List;

public class GetApartmentNamesResponseDto {
  private final List<ApartmentName> data;

    public GetApartmentNamesResponseDto(List<ApartmentName> data) {
        this.data = data;
    }

    public List<ApartmentName> getData() {
        return data;
    }
}
