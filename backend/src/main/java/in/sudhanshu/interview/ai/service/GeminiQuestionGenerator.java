package in.sudhanshu.interview.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.sudhanshu.interview.ai.config.GeminiProperties;
import in.sudhanshu.interview.ai.dto.GeminiQuestionResponse;
import in.sudhanshu.interview.ai.dto.GeneratedQuestion;
import in.sudhanshu.interview.ai.dto.gemini.GeminiApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class GeminiQuestionGenerator implements QuestionGenerator {

  private final RestClient restClient;

  private final GeminiProperties geminiProperties;

  private final ObjectMapper objectMapper;

  @Override
  public List<GeneratedQuestion> generateQuestions(String resumeText) {
    String prompt = """
        You are an experienced technical interviewer.

        Based on the resume below:

        %s

        Generate:

        10 technical questions
        3 behavioral questions
        2 system design questions

        Return ONLY valid JSON.

        Format:

        {
          "questions": [
            {
              "question": "Explain Spring Security Filter Chain",
              "difficulty": "MEDIUM",
              "category": "SPRING_BOOT"
            }
          ]
        }

        Allowed difficulties:
        EASY
        MEDIUM
        HARD

        Allowed categories:
        JAVA
        SPRING_BOOT
        MYSQL
        REACT
        JAVASCRIPT
        SYSTEM_DESIGN
        BEHAVIORAL

        Return JSON only.
        Do not use markdown.
        Do not use code blocks.
        """
        .formatted(resumeText);

    try {
      String requestBody = """
          {
            "contents": [
              {
                "parts": [
                  {
                    "text": %s
                  }
                ]
              }
            ]
          }
          """.formatted(objectMapper.writeValueAsString(prompt));

      String response = restClient.post()
          .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
              + geminiProperties.getApiKey())
          .body(requestBody)
          .retrieve()
          .body(String.class);

      log.info("Gemini response received");

      GeminiApiResponse apiResponse = objectMapper.readValue(response, GeminiApiResponse.class);
      String generatedJson = apiResponse
          .candidates()
          .getFirst()
          .content()
          .parts()
          .getFirst()
          .text();

      GeminiQuestionResponse questions = objectMapper.readValue(
          generatedJson,
          GeminiQuestionResponse.class);

      return questions.questions();
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate interview questions", e);
    }

  }

}
