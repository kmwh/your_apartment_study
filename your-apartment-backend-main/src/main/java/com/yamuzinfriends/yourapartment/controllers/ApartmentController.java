package com.yamuzinfriends.yourapartment.controllers;

import com.yamuzinfriends.yourapartment.dtos.*;
import com.yamuzinfriends.yourapartment.models.Apartment;
import com.yamuzinfriends.yourapartment.services.ApartmentService;
import com.yamuzinfriends.yourapartment.utils.components.GetIpAddress;
import com.yamuzinfriends.yourapartment.utils.enums.GetApartmentsOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/v1/apartment")
@CrossOrigin("*")
public class ApartmentController {
  private final ApartmentService apartmentService;
  private final GetIpAddress ipAddressService;

  public ApartmentController(ApartmentService service, GetIpAddress ipAddressService) {
    this.apartmentService = service;
    this.ipAddressService = ipAddressService;
  }

  @GetMapping()
  public ResponseEntity<GetApartmentsResponseDto> getApartments(
      @RequestParam("order") String order) {
    GetApartmentsRequestDto requestDto = new GetApartmentsRequestDto(
        Objects.equals(order, "newest") ? GetApartmentsOrder.NEWEST : GetApartmentsOrder.POPULAR
    );

    String ipAddress = this.ipAddressService.getIpAddress();

    ArrayList<GetApartmentResponseDto> data = new ArrayList<>();
    for (Apartment apartment : this.apartmentService.getApartments(requestDto)) {
      boolean isLiked = this.apartmentService.isLikeClicked(ipAddress, apartment);
      data.add(new GetApartmentResponseDto(
          apartment._id,
          apartment.nickname,
          apartment.apartmentName,
          apartment.gptScore,
          isLiked,
          apartment.likes.size(),
          apartment.comments.size(),
          apartment.createdAt
      ));
    }
    return new ResponseEntity<>(
        new GetApartmentsResponseDto(
            this.apartmentService.getApartmentCount(),
            data
        ),
        HttpStatus.OK
    );
  }

  @PostMapping()
  public ResponseEntity<CreateApartmentResponseDto> createApartment(@RequestBody CreateApartmentRequestDto dto) {
    if (dto.getApartmentName().isEmpty() || dto.getNickname().isEmpty() || dto.getPassword().isEmpty()){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if (this.apartmentService.getApartmentByName(dto.getApartmentName()) != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    CreateApartmentResponseDto responseDto = this.apartmentService.createApartment(dto);

    return new ResponseEntity<>(
        responseDto,
        HttpStatus.CREATED
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteApartment(
          @PathVariable(value = "id") int id,
          @RequestBody Map<String, String> password) {
    this.apartmentService.deleteApartment(id, password.get("password"));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<GetCommentsResponseDto> getComments(@PathVariable(value = "id") int id) {
    GetCommentsResponseDto responseDto = this.apartmentService.getComments(id);
    return new ResponseEntity<>(
        responseDto,
        HttpStatus.OK
    );
  }

  @PostMapping("/{id}/comments")
  public ResponseEntity<CreateCommentResponseDto> createApartmentComment(
          @PathVariable(value = "id") int id,
          @RequestBody CreateCommentRequestDto dto) {
    if (dto.getNickname().isEmpty() || dto.getComment().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    CreateCommentResponseDto responseDto = this.apartmentService.createComment(id, dto);

    return new ResponseEntity<>(
        responseDto,
        HttpStatus.CREATED
    );
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<Void> deleteApartmentComment(
          @PathVariable(value = "commentId") int commentId,
          @RequestBody Map<String, String> password) {
    DeleteCommentRequestDto dto = new DeleteCommentRequestDto(
            commentId, password.get("password")
    );
    this.apartmentService.deleteComment(dto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("{id}/like")
  public ResponseEntity<GetLikesResponseDto> getLikes(@PathVariable(value = "id") int id) {
    return new ResponseEntity<>(
        this.apartmentService.getLikes(id),
        HttpStatus.OK
    );
  }

  @PostMapping("/{id}/like")
  public ResponseEntity<Void> createApartmentLike(@PathVariable(value = "id") int id) {
    this.apartmentService.createLike(id);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}/like")
  public ResponseEntity<Void> deleteApartmentLike(@PathVariable(value = "id") int id) {
    this.apartmentService.deleteLike(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
