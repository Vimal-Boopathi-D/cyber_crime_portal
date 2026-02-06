package com.cyber.portal.volunteerManagement.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;


public interface VolunteerCsvService {
    ByteArrayInputStream exportApprovedVolunteersExcel() throws IOException;
}
