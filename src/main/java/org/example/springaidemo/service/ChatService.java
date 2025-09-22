package org.example.springaidemo.service;

import org.example.springaidemo.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ChatService {
     //chat client này dùng để tương tác vs spring ai
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    public String chat(ChatRequest request) {
        //system message là tin nhắn hệ thống, user message là tin nhắn người dùng
        SystemMessage systemMessage = new SystemMessage("""
                You are Quang - a software developer ai.
                You should response with a formal voice
                """);

        UserMessage userMessage = new UserMessage(request.message());

        Prompt prompt = new Prompt(systemMessage, userMessage);
//        return request.message();
        return chatClient
                .prompt(prompt)
                .call()
                .content();

    }

    public String chatWithImage(MultipartFile file, String message) {
        Media media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
                .data(file.getResource())
                .build();

        ChatOptions options= ChatOptions.builder()
                .temperature(0D)  // mức độ sáng tạo của câu trả lời
                .build();
        return chatClient.prompt()
                .options(options)
                .system("Quang Ai")
                .user( promptUserSpec ->
                        promptUserSpec.media(media).text(message))
                .call()
                .content();
    }
}
