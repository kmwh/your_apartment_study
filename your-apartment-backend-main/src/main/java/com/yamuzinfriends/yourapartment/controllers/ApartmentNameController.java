package com.yamuzinfriends.yourapartment.controllers;

import com.yamuzinfriends.yourapartment.dtos.GetApartmentNamesRequestDto;
import com.yamuzinfriends.yourapartment.dtos.GetApartmentNamesResponseDto;
import com.yamuzinfriends.yourapartment.models.ApartmentName;
import com.yamuzinfriends.yourapartment.services.ApartmentNameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/apartment/name")
@CrossOrigin("*")
public class ApartmentNameController {
  private final ApartmentNameService apartmentNameService;

  public ApartmentNameController(ApartmentNameService service) {
    this.apartmentNameService = service;
  }

  @GetMapping()
  public ResponseEntity<GetApartmentNamesResponseDto> getApartmentNames(@RequestParam("query") String query) {
    GetApartmentNamesRequestDto requestDto = new GetApartmentNamesRequestDto(query);
    List<ApartmentName> data = apartmentNameService.searchApartmentNames(requestDto);
    return ResponseEntity.ok(new GetApartmentNamesResponseDto(data));
  }
}
