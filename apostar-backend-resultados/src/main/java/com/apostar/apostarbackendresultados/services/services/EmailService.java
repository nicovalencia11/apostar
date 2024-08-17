package com.apostar.apostarbackendresultados.services.services;

public interface EmailService {

    /**
     * Metodo que permite enviar un email
     * @param to
     * @param subject
     * @param body
     */
    void enviarEmail(String to, String subject, String body, byte[] image) throws Exception;

}
