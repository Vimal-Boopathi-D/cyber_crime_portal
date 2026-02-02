package com.cyber.portal.aiManagement.facade;

import com.cyber.portal.aiManagement.dto.MessageDto;
import com.cyber.portal.aiManagement.dto.Response;
import com.cyber.portal.aiManagement.service.ChatService;
import com.cyber.portal.aiManagement.service.RAGService;
import com.cyber.portal.sharedResources.dto.ApiResponse;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatFacade {
    private final ChatService chatService;
    private final RAGService ragService;

    public ApiResponse<Response> chat(MessageDto message) {
        try{
            Response response = chatService.chat(message);
            return new ApiResponse<>(200, "responded successfully", response);
        } catch (Exception e) {
            log.error("Error in chatFacade.chat()", e);
            return new ApiResponse<>(409,e.getMessage(),null);
        }
    }




    public ApiResponse<String> routeMessage(MessageDto dto) {
        String message = dto.getMessage().toLowerCase();
        boolean isProductQuery = isCyberCrimeRelated(message);
        if (isProductQuery) {
            String result = ragService.chatUsingRag(dto.getMessage());
            return new ApiResponse<>(200, "responded successfully", result);
        } else {
            String result = chatService.chat(dto).getResponse();
            return new ApiResponse<>(200, "responded successfully", result);
        }
    }


    private boolean isCyberCrimeRelated(String message) {
        if (message == null || message.isBlank()) return false;

        String lowerMsg = message.toLowerCase();

        return CYBERCRIME_KEYWORDS.stream()
                .anyMatch(lowerMsg::contains);
    }


    private static final Set<String> CYBERCRIME_KEYWORDS = Set.of(

            "cybercrime",
            "cyber crime",
            "national cybercrime",
            "national cybercrime reporting portal",
            "cybercrime reporting portal",
            "cybercrime.gov.in",
            "https://cybercrime.gov.in",
            "mha cybercrime",
            "ministry of home affairs",
            "i4c",
            "indian cybercrime coordination centre",

            "report cybercrime",
            "report cyber crime",
            "file complaint",
            "file a complaint",
            "online complaint",
            "cyber complaint",
            "register complaint",
            "register cyber complaint",
            "lodge complaint",
            "complain",
            "complaint",
            "case",
            "incident report",
            "crime report",

            "complaint status",
            "track complaint",
            "track cyber complaint",
            "complaint id",
            "acknowledgement number",
            "reference number",
            "case status",
            "under review",
            "investigation in progress",
            "forwarded to police",
            "closed complaint",

            "fraud",
            "financial fraud",
            "online fraud",
            "upi fraud",
            "bank fraud",
            "internet banking fraud",
            "credit card fraud",
            "debit card fraud",
            "payment fraud",
            "transaction fraud",
            "cryptocurrency scam",
            "crypto fraud",
            "investment scam",
            "lottery scam",
            "job fraud",
            "fake job",
            "scam",
            "scammer",

            "fake profile",
            "fake account",
            "identity theft",
            "impersonation",
            "social media fraud",
            "online impersonation",
            "account hacked",
            "profile hacked",
            "defamation",
            "online stalking",
            "cyberbullying",
            "cyber bullying",

            "hacking",
            "hacked",
            "unauthorized access",
            "data theft",
            "data leak",
            "website defacement",
            "malware",
            "virus attack",
            "ransomware",
            "phishing",
            "email phishing",
            "sms phishing",
            "otp fraud",
            "link fraud",
            "suspicious link",

            "women harassment",
            "cyber harassment",
            "online harassment",
            "sextortion",
            "revenge porn",
            "intimate images",
            "child abuse",
            "child exploitation",
            "child sexual abuse material",
            "csam",
            "online grooming",

            "anonymous complaint",
            "anonymous reporting",
            "confidential complaint",
            "sensitive case",

            "1930",
            "cyber helpline",
            "cybercrime helpline",
            "fraud helpline",
            "call 1930",
            "emergency cyber fraud",

            "cyber police",
            "cyber crime police",
            "police complaint",
            "police station",
            "fir",
            "register fir",
            "law enforcement",
            "investigation",
            "legal action",
            "ipc",
            "it act",

            "how to report",
            "how to complain",
            "how to file",
            "how to report cybercrime",
            "steps",
            "procedure",
            "process",
            "what to do",
            "help",
            "support",
            "guidelines",
            "complaint process"
    );


    public ApiResponse<String> trackUpdates(IncidentStatus status) {
        String response =ragService.explainIncidentStatus(status);
        return new ApiResponse<>(200, "responded successfully", response);
    }
}
