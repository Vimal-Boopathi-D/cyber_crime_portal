package com.cyber.portal.aiManagement.service.impl;

import com.cyber.portal.aiManagement.service.RAGService;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class RAGServiceImpl implements RAGService {
    private final ChatClient chatClient;

    private final VectorStore vectorStore;

    @Override
    public String chatUsingRag(String message) {

        QuestionAnswerAdvisor advisor = QuestionAnswerAdvisor
                .builder(vectorStore)
                .build();

        String template = """
You are a Cybercrime Reporting Portal assistant.

Answer the user's question using ONLY the information provided in the context.
Explain in simple words.
Keep the answer in ONE short line with clear steps.
Do NOT say "I don’t have that information" if context is present.

Question:
{question}
""";

        PromptTemplate promptTemplate = new PromptTemplate(template);

        Prompt prompt = promptTemplate.create(
                Map.of("question", message)
        );

        return chatClient.prompt(prompt)
                .advisors(advisor)
                .call()
                .content();
    }



    @Override
    public String explainIncidentStatus(IncidentStatus status) {

        QuestionAnswerAdvisor advisor = QuestionAnswerAdvisor
                .builder(vectorStore)
                .build();

        String template = """
    You are a cybercrime portal assistant.
    Explain the complaint status in ONE simple line.
    Mention what is happening now and what the next step is.
    Avoid legal jargon.
    Status: {status}
    keep Estimated next update in 48 hours. add this at last in response 
    """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(
                Map.of("status", status.name())
        );

        return chatClient.prompt(prompt)
                .advisors(advisor)
                .call()
                .content();
    }

}
