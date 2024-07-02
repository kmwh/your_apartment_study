package com.yamuzinfriends.yourapartment.dtos;

import com.yamuzinfriends.yourapartment.utils.enums.GetApartmentsOrder;

public class GetApartmentsRequestDto {
  private GetApartmentsOrder order;

  public GetApartmentsRequestDto(GetApartmentsOrder order) {
    this.order = order;
  }


  public GetApartmentsOrder getOrder() {
    return order;
  }
}