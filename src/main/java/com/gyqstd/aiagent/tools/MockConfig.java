package com.gyqstd.aiagent.tools;
//import com.gyqstd.imagesearchmcpserver.tools.ImageSearchTool;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider() {
        // 返回一个空的ToolCallbackProvider，防止启动时报错
        return () -> new org.springframework.ai.model.function.FunctionCallback[0];
//        return () -> new FunctionCallback[0];
    }
}

