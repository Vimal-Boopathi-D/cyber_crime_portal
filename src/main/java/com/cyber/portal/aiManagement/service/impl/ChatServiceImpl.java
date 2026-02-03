package com.cyber.portal.aiManagement.service.impl;

import com.cyber.portal.aiManagement.dto.MessageDto;
import com.cyber.portal.aiManagement.dto.Response;
import com.cyber.portal.aiManagement.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    @Override
    public Response chat(MessageDto message){

//        ChatResponse chatResponse = chatClient
//                .prompt(message.getMessage())
//                .call()
//                .chatResponse(); // to get Metadata
//
//                String response = chatResponse
//                        .getResult()
//                        .getOutput()
//                        .getText();  //alter for .call().content()
//
//        if (chatResponse != null) {
//            System.out.println(chatResponse.getMetadata().getModel());
//        }


        String template = """
                keep the response in one or two lines 
                Rules:
                1.should not include * , ** 
                and new line symbols
                2.use bullet points 
                """;
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create();

        String result = chatClient.prompt(prompt).user(message.getMessage()).call().content();

        Response response =new Response();
        response.setResponse(result);

        return response;
    }

}
