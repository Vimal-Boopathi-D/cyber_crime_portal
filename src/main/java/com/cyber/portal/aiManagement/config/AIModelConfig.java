package com.cyber.portal.aiManagement.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AIModelConfig {

    private final OllamaChatModel ollamaChatModel;

    public AIModelConfig(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    @Bean
    public ChatClient chatClient() {
        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(20).build();
        return ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Bean
    public VectorStore vectorStore(@Qualifier("vectorJdbcTemplate") JdbcTemplate vectorJdbcTemplate,
                                   EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(vectorJdbcTemplate, embeddingModel)
                .dimensions(768)
                .build();
    }

    @Bean
    @Qualifier("vectorJdbcTemplate")
    public JdbcTemplate vectorJdbcTemplate() {
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5433/vibo")
                .username("postgres")
                .password("Finytive@123")
                .driverClassName("org.postgresql.Driver")
                .build();
        return new JdbcTemplate(dataSource);
    }
}
