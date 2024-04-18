package com.etaskify.etaskify.service;

import com.etaskify.etaskify.model.dto.MailDto;
import jakarta.mail.MessagingException;

public interface MailService {

    void sendMail(MailDto mail);

}
