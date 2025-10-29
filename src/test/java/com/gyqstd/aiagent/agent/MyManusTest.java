package com.gyqstd.aiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GuYuqi
 * @version 1.0
 */
@SpringBootTest
class MyManusTest {

    @Resource
    private MyManus myManus;

    @Test
    void run() {
        String userPrompt = """  
                我的另一半居住在潍坊经济开发区民主街7777号潍坊理工学院(中心校区)，请帮我找到 5 公里内合适的约会地点，
                并结合一些网络图片，制定一份详细的约会计划，
                并以 PDF 格式输出""";
        String answer = myManus.run(userPrompt);
        Assertions.assertNotNull(answer);
    }
}
