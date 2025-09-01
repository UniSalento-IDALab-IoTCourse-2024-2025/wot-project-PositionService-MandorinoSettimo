package it.unisalento.pas2425.positionservice.service;

import it.unisalento.pas2425.positionservice.dto.EmailResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailResultDTO sendRegistrationConfirmation(String to, String userName) {
        EmailResultDTO result = new EmailResultDTO();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Benvenuto su DeliveryGo!");
            message.setText("Ciao " + userName + ",\n\nLa tua registrazione è avvenuta con successo.\n\nGrazie per esserti unito a noi!\n\nIl team di DeliveryGo");

            mailSender.send(message);
            result.setSuccess(true);
            result.setMessage("Email inviata con successo a " + to);
        } catch (MailException e) {
            result.setSuccess(false);
            result.setMessage("Errore durante l'invio dell'email a " + to + ": " + e.getMessage());
        }

        System.out.println(result.getMessage());
        return result;
    }

    public EmailResultDTO sendChangePassowrdConfirm(String to, String userName) {
        EmailResultDTO result = new EmailResultDTO();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Password modificata con successo - Swapp");
            message.setText("Ciao " + userName + ",\n\nLa tua password è stata cambiata correttamente.\n\nSe non sei stato tu, contattaci immediatamente.\n\nIl team di Swapp");

            mailSender.send(message);
            result.setSuccess(true);
            result.setMessage("Email inviata con successo a " + to);
        } catch (MailException e) {
            result.setSuccess(false);
            result.setMessage("Errore durante l'invio dell'email a " + to + ": " + e.getMessage());
        }

        System.out.println(result.getMessage());
        return result;
    }

    public EmailResultDTO sendPasswordResetCode(String email, String name, String code) {
        EmailResultDTO result = new EmailResultDTO();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Codice di reset password - Swapp");
            message.setText("Ciao " + name + ",\n\nHai richiesto il reset della password.\n\nQuesto è il tuo codice di verifica:\n\n" + code + "\n\nInseriscilo nell'app per impostare una nuova password.\n\nSe non sei stato tu, ignora questa email.\n\nIl team di Swapp");

            mailSender.send(message);
            result.setSuccess(true);
            result.setMessage("Codice reset inviato con successo a " + email);
        } catch (MailException e) {
            result.setSuccess(false);
            result.setMessage("Errore durante l'invio del codice reset a " + email + ": " + e.getMessage());
        }

        System.out.println(result.getMessage());
        return result;
    }
}
