package com.cyber.portal.aiManagement;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {
    private final VectorStore vectorStore;

    public DataInitializer(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void initData(){
        TextReader textReader = new TextReader(new ClassPathResource("about.txt"));
        TokenTextSplitter textSplitter = new TokenTextSplitter(150, 10, 50, 200, false);
        List<Document> documents =textSplitter.split(textReader.get());
        vectorStore.add(documents);
    }
}
