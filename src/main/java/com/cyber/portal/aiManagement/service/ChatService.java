package com.cyber.portal.aiManagement.service;

import com.cyber.portal.aiManagement.dto.MessageDto;
import com.cyber.portal.aiManagement.dto.Response;

public interface ChatService {
    Response chat(MessageDto message);

}
