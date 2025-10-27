package com.gyqstd.aiagent.tools;
import com.gyqstd.imagesearchmcpserver.tools.ImageSearchTool;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider() {
        ImageSearchTool imageSearchTool = new ImageSearchTool();
        return MethodToolCallbackProvider.builder()
                .toolObjects(imageSearchTool)
                .build();

//        // 返回一个空数组，啥也不干，只是为了防止启动时报错
//        return () -> new FunctionCallback[0];
    }
}

