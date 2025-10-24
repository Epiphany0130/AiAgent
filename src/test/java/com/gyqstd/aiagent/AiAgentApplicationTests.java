package com.gyqstd.aiagent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiAgentApplicationTests {


    @Resource
    private ChatModel dahscopeChatModel;

    @Test
    void contextLoads() {
        String s = dahscopeChatModel.call("你好").toString();
        System.out.println("s = " + s);
    }

}
