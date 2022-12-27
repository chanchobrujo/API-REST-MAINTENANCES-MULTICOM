package com.proyectoagendador.multicom;

import com.proyectoagendador.multicom.common.properties.SenderMailProperties;
import com.proyectoagendador.multicom.service.authentication.MailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class MailSenderServiceTest {
    @Mock
    private SenderMailProperties senderMailProperties;

    @InjectMocks
    private MailSenderService service;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mailSendError() {
        when(senderMailProperties.getEmail()).thenReturn("email");
        when(senderMailProperties.getAuth()).thenReturn("");
        when(senderMailProperties.getHost()).thenReturn("");
        when(senderMailProperties.getPort()).thenReturn("");
        when(senderMailProperties.getStarttls()).thenReturn("");
        when(senderMailProperties.getPassword()).thenReturn("");
        this.service.sendMail("email", "", "");
    }
}
