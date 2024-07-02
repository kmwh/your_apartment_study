package com.yamuzinfriends.yourapartment.dtos;

import java.util.Date;

public class CreateApartmentResponseDto {
    private int _id;
    private String nickname;
    private String ipAddress;
    private String apartmentName;
    private double gptScore;
    private Date createdAt;

    public CreateApartmentResponseDto(int _id, String nickname, String ipAddress, String apartmentName, double gptScore, Date createdAt) {
        this._id = _id;
        this.nickname = nickname;
        this.ipAddress = ipAddress;
        this.apartmentName = apartmentName;
        this.gptScore = gptScore;
        this.createdAt = createdAt;
    }

    public int get_id() { return _id; }

    public String getNickname() { return nickname; }

    public String getIpAddress() { return ipAddress; }

    public String getApartmentName() { return apartmentName; }

    public Date getCreatedAt() { return createdAt; }

    public double getGptScore() {
        return gptScore;
    }
}
