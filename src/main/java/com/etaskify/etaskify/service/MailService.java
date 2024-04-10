package com.etaskify.etaskify.service;

import com.etaskify.etaskify.model.dto.MailDto;

public interface MailService {

    void sendMail(MailDto mail);

}
