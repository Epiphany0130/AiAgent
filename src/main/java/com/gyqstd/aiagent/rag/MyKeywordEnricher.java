package com.gyqstd.aiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基于 Ai 的文档信息增强器（生成 metadata）
 * @author GuYuqi
 * @version 1.0
 */
@Component
public class MyKeywordEnricher {

    @Resource
    ChatModel dashscopeChatModel;

    public List<Document> enrichDocuments(List<Document> documents) {
        KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(dashscopeChatModel, 5);
        return keywordMetadataEnricher.apply(documents);
    }
}
