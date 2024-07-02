package com.yamuzinfriends.yourapartment.dtos;

public class DeleteCommentRequestDto {
    private final int commentId;
    private final String password;

    public DeleteCommentRequestDto(int commentId, String password) {
        this.commentId = commentId;
        this.password = password;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getPassword() {
        return password;
    }
}
