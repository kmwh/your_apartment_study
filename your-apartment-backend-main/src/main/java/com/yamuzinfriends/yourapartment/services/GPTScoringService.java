package com.yamuzinfriends.yourapartment.services;

import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GPTScoringService {
  @Value("${spring.chatgpt.accessKey}")
  private String accessKey;
  private final OkHttpClient client;
  private final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  public GPTScoringService() {
    this.client = new OkHttpClient().newBuilder().build();
  }

  public float requestScore(String apartmentName) throws IOException {
    // 크래딧 소진 방지를 위해 테스트 기간동안은 렌덤 넘버 이용
    RequestBody body = RequestBody.create(this.prompt(apartmentName), JSON);
    Request request = new Request.Builder()
        .url("https://api.openai.com/v1/chat/completions")
        .method("POST", body)
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Bearer " + this.accessKey)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (response.body() == null) {
        throw new IOException();
      }

      String data = response.body().string();
      JSONParser parser = new JSONParser();
      JSONObject obj = (JSONObject) parser.parse(data);
      JSONArray choices = (JSONArray)obj.get("choices");
      JSONObject choice = (JSONObject) choices.getFirst();
      JSONObject message = (JSONObject) choice.get("message");
      String score = message.get("content").toString();

      return Float.parseFloat(score);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new IOException();
    }
  }

  public String prompt(String apartmentName) {
    return "{\r\n" +
        "    \"model\": \"gpt-4o\",\r\n" +
        "    \"messages\": [\r\n" +
        "        {\r\n" +
        "            \"role\": \"user\",\r\n" +
        "            \"content\": \"한국에 다양한 아파트에 점수를 매겨보려고 해. \\n\\n" +
        "아파트 이름을 평가할 때 다음 네 가지 기준을 적용하여 0에서 100점 사이로 점수를 매겨. 각 기준은 25점 만점으로 평가하며, 이를 모두 합산하여 최종 점수를 산정합니다. 1의 자리까지 정확하게 평가해. \\n\\n" +
        "라틴어와 영어가 많이 들어간 이름 (25점)\\n\\n" +
        "예: \\\"에스클래스\\\", \\\"베르데카운티\\\", \\\"에트르더펠리체\\\", \\\"퍼스트\\\", \\\"케슬\\\" 등.\\n" +
        "라틴어 및 영어 단어가 포함된 정도에 따라 점수를 매깁니다.\\n" +
        "예시: 라틴어와 영어 단어가 3개 이상 = 25점, 2개 = 15점, 1개 = 5점, 0개 = 0점.\\n" +
        "역 이름이나 공원 등 주변 랜드마크 명이 들어간 이름 (25점)\\n\\n" +
        "예: \\\"검단신도시\\\", \\\"파크뷰\\\" 등.\\n" +
        "주변 랜드마크나 지명 등이 포함된 정도에 따라 점수를 매깁니다.\\n" +
        "예시: 역 이름 및 랜드마크가 포함된 경우 = 25점, 주변 지명이 포함된 경우 = 15점, 포함되지 않은 경우 = 0점.\\n" +
        "이름의 길이와 복잡성 (25점)\\n\\n" +
        "예: \\\"검단신도시신안인스빌어반퍼스트\\\"와 같이 길고 복잡한 이름.\\n" +
        "이름의 길이와 복잡성에 따라 점수를 매깁니다.\\n" +
        "예시: 20자 이상 = 25점, 15자 이상 = 15점, 10자 이상 = 5점, 10자 미만 = 0점.\\n" +
        "회사 이름이나 브랜드 명이 영어로 복잡하게 적혀있는 이름 (25점)\\n\\n" +
        "예: \\\"포스코\\\", \\\"삼성\\\", \\\"현대\\\" 등.\\n" +
        "회사 이름이나 브랜드 명이 포함된 정도에 따라 점수를 매깁니다.\\n" +
        "예시: 회사 이름 및 브랜드 명이 2개 이상 포함 = 25점, 1개 = 15점, 포함되지 않은 경우 = 0점.\\n\\n" +
        "--\\n\\n" +
        "위의 기준을 바탕으로 \\\"" + apartmentName + "\\\" 이름의 점수를 숫자 하나만 반환해.\\n" +
        "컴퓨터가 읽어야 하니 반드시 숫자만 반환해 \"\r\n" +
        "        }\r\n" +
        "    ],\r\n" +
        "    \"temperature\": 1,\r\n" +
        "    \"max_tokens\": 256,\r\n" +
        "    \"top_p\": 1,\r\n" +
        "    \"frequency_penalty\": 0,\r\n" +
        "    \"presence_penalty\": 0\r\n" +
        "}";
  }

}
