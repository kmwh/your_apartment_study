package com.yamuzinfriends.yourapartment.dtos;

import com.yamuzinfriends.yourapartment.models.Apartment;

import java.util.List;

public class GetApartmentsResponseDto {
  private final int totalSize;
  private List<GetApartmentResponseDto> data;

  public GetApartmentsResponseDto(int totalSize, List<GetApartmentResponseDto> data) {
    this.totalSize = totalSize;
    this.data = data;
  }

  public int getTotalSize() {
    return totalSize;
  }

  public List<GetApartmentResponseDto> getData() {
    return data;
  }
}
