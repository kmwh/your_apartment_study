package com.yamuzinfriends.yourapartment.dtos;

import com.yamuzinfriends.yourapartment.models.Comment;

import java.util.List;

public class GetCommentsResponseDto {
    private List<Comment> comments;

    public GetCommentsResponseDto(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() { return comments; }
}
