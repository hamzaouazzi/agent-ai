package fr.ho.agentai.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Service
public class AIAgent {
    private ChatClient chatClient;

    public AIAgent(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public Flux<String> onQuery(String query) {
        return chatClient.prompt()
                .user(query)
                .stream().content();
    }
}
