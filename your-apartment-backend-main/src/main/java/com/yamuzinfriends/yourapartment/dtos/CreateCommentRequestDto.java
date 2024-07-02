package com.yamuzinfriends.yourapartment.dtos;

public class CreateCommentRequestDto {
    private String nickname;
    private String comment;
    private String password;

    public String getNickname() { return nickname; }

    public String getComment() { return comment; }

    public String getPassword() {
        return password;
    }
}
