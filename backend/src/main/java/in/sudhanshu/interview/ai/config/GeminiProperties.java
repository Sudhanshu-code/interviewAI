package in.sudhanshu.interview.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeminiProperties {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}