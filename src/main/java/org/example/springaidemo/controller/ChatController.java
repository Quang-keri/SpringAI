package org.example.springaidemo.controller;

import org.example.springaidemo.dto.ChatRequest;
import org.example.springaidemo.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ChatController {
   private  final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        return  chatService.chat(request);
    }
   // xử lí promt vs ảnh , có vấn đề là nó khi hỏi 1 lần nhưng gọi lại nhìu ,
   // thì nó sẽ gen ra nhìu kết quả giống nhay nhìu hơn hoặc ít hơn gì đó
    // gọi là mức sáng tạo

    @PostMapping("/chat-with-image")
    public  String chatWithImage(@RequestParam("file") MultipartFile file,
                                 @RequestParam("message") String message){

        return chatService.chatWithImage(file,message);

    }
}
