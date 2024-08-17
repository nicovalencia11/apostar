package com.apostar.apostarbackendresultados.services.implementation;

import com.apostar.apostarbackendresultados.services.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Metodo que permite enviar un email
     *
     * @param to
     * @param subject
     * @param body
     */
    @Override
    public void enviarEmail(String to, String subject, String body, byte[] imageBytes) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        // Convertir los bytes de la imagen a un InputStreamSource
        InputStreamSource imageSource = new ByteArrayResource(imageBytes);

        // Adjuntar la imagen al correo electrónico
        helper.addAttachment("image.png", imageSource);

        // Enviar el mensaje
        javaMailSender.send(message);
    }
}
