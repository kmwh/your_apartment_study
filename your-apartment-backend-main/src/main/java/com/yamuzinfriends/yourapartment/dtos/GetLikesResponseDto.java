package com.yamuzinfriends.yourapartment.dtos;

public class GetLikesResponseDto {
    private int likes;

    public GetLikesResponseDto(int likes) {
        this.likes = likes;
    }

    public int getLikes() { return likes; }
}
