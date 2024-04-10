package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.model.dto.MailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailServiceImpl mailService;

    @Test
    void testSendMail() {
        MailDto mailDto = new MailDto("recipient@mail.com",
                "Test Subject",
                "Test Message");

        mailService.sendMail(mailDto);

        SimpleMailMessage expectedMessage = new SimpleMailMessage();  // Configure expected message
        // ... (set from, to, subject, text based on your @Value and mailDto)

        verify(mailSender).send(any(SimpleMailMessage.class));  // Or verify with the expectedMessage
    }
}

