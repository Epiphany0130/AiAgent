package com.gyqstd.aiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GuYuqi
 * @version 1.0
 */
@SpringBootTest
public class PgVectorVectorStoreConfigTest {


    @Resource
    VectorStore pgVectorVectorStore;

    @Test
    void pgVectorVectorStore() {

        List<Document> documents = List.of(
                new Document("这是一个AI恋爱大师应用，是我用来学习编程的", Map.of("meta1", "meta1")),
                new Document("通过这个应用，你可以和AI进行有趣的对话，获取恋爱建议"),
                new Document("我通过学习这个项目，学习了 SpringAI 的使用，目前正在测试向量数据库的功能", Map.of("meta2", "meta2")));

        pgVectorVectorStore.add(documents);

        List<Document> results = this.pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("这是个什么项目，可以带给学习者什么好处").topK(5).build());
        Assertions.assertNotNull(results);
    }
}