package com.cyber.portal.aiManagement.service.impl;

import com.cyber.portal.aiManagement.service.AiPredictionService;
import com.cyber.portal.sharedResources.enums.Label;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AiPredictionServiceImpl implements AiPredictionService {

    @Autowired
    private  ChatClient chatClient;

    @Override
    public Label getIncidentLabel(String description) {

        try {
            String template = """
                You are a classification system.

                Return ONLY one word:
                LIKELY_GENUINE
                or
                POSSIBLY_FAKE

                No explanation.
                No extra text.

                Incident description:
                {description}
                """;

            PromptTemplate promptTemplate = new PromptTemplate(template);
            Prompt prompt = promptTemplate.create(Map.of("description", description));

            String response = chatClient.prompt(prompt).call().content();

            log.info("AI Raw Response: {}", response);

            String normalized = response
                    .trim()
                    .toUpperCase()
                    .replace(" ", "_");

            return Label.valueOf(normalized);

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unexpected response from AI model");
        }
    }

    }
