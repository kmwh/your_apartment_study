package com.yamuzinfriends.yourapartment.dtos;

public class GetApartmentNamesRequestDto {
  private final String query;

  public GetApartmentNamesRequestDto(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }
}
