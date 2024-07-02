package com.yamuzinfriends.yourapartment.dtos;

import java.util.Date;

public class CreateCommentResponseDto {
    private int _id;
    private String nickname;
    private String comment;
    private Date created_at;

    public CreateCommentResponseDto(int id, String nickname, String comment, Date created_at) {
        this._id = id;
        this.nickname = nickname;
        this.comment = comment;
        this.created_at = created_at;
    }

    public int get_id() { return _id; }

    public String getNickname() { return nickname; }

    public String getComment() { return comment; }

    public Date getCreated_at() { return created_at; }
}
