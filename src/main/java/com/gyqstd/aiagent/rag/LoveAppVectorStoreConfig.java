package com.gyqstd.aiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
//@Configuration
public class LoveAppVectorStoreConfig {

//    @Resource
//    private LoveAppDocumentLoader loveAppDocumentLoader;
//
//    @Resource
//    private MyKeywordEnricher myKeywordEnricher;
//
//    @Bean
//    VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
//        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
//        // 加载文档
//        List<Document> documentList = loveAppDocumentLoader.loadMarkdowns();
//        // 自动补充关键词元信息
//        List<Document> enrichDocuments = myKeywordEnricher.enrichDocuments(documentList);
//        simpleVectorStore.add(documentList);
//        return simpleVectorStore;
//    }

}
