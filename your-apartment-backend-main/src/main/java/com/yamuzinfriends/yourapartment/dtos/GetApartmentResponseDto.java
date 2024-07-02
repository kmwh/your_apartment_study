package com.yamuzinfriends.yourapartment.dtos;

import com.yamuzinfriends.yourapartment.models.ApartmentName;

import java.util.Date;

public class GetApartmentResponseDto {

  public int _id;
  public String nickname;
  public ApartmentName apartmentName;
  public float gptScore;
  public boolean isLiked;
  public int likeCount;
  public int commentCount;
  public Date createdAt;

  public GetApartmentResponseDto(
      int _id,
      String nickname,
      ApartmentName apartmentName,
      float gptScore,
      boolean isLiked,
      int likeCount,
      int commentCount,
      Date createdAt
  ) {
    this._id = _id;
    this.nickname = nickname;
    this.apartmentName = apartmentName;
    this.gptScore = gptScore;
    this.isLiked = isLiked;
    this.likeCount = likeCount;
    this.commentCount = commentCount;
    this.createdAt = createdAt;
  }
}
