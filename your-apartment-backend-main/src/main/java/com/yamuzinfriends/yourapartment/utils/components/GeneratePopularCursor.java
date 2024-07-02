package com.yamuzinfriends.yourapartment.utils.components;

import java.util.Date;

public class GeneratePopularCursor {
  public static String generatePopularCursor(float gptScore, int likeCount, Date createdAt) {
    String cursor = String.format("%03d", Math.round(gptScore));
    cursor += String.format("%010d", likeCount);
    cursor += String.format("%011d", createdAt.getTime());
    return cursor;
  }
}
