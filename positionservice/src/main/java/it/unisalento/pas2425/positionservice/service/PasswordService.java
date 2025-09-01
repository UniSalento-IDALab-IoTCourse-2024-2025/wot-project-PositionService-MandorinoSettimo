package it.unisalento.pas2425.positionservice.service;

import it.unisalento.pas2425.positionservice.domain.User;
import it.unisalento.pas2425.positionservice.dto.PasswordResetDTO;
import it.unisalento.pas2425.positionservice.dto.PasswordResetResultDTO;
import it.unisalento.pas2425.positionservice.dto.PasswordUpdateDTO;
import it.unisalento.pas2425.positionservice.dto.PasswordUpdateResultDTO;
import it.unisalento.pas2425.positionservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public PasswordService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public PasswordUpdateResultDTO updatePassword(PasswordUpdateDTO dto) {

        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (!optionalUser.isPresent()) {
            return new PasswordUpdateResultDTO(PasswordUpdateResultDTO.USER_NOT_FOUND, "Utente non trovato");
        }

        User user = optionalUser.get();

        // Verifica la password attuale
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            return new PasswordUpdateResultDTO(PasswordUpdateResultDTO.PASSWORD_ERROR, "La vecchia password è errata");
        }

        // Verifica che la nuova password non sia vuota
        if (dto.getNewPassword() == null || dto.getNewPassword().isBlank()) {
            return new PasswordUpdateResultDTO(PasswordUpdateResultDTO.BLANK_SPACE, "La nuova password non può essere vuota");
        }

        // Verifica che la nuova password sia diversa
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            return new PasswordUpdateResultDTO(PasswordUpdateResultDTO.SAME_PASSWORD, "Le password sono uguali");
        }

        // Codifica la nuova password e salvala
        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.save(user);

        // Invio conferma email
        emailService.sendChangePassowrdConfirm(user.getEmail(), user.getName());

        return new PasswordUpdateResultDTO(PasswordUpdateResultDTO.OK, "La password è stata modificata con successo");
    }

    public PasswordResetResultDTO handlePasswordReset(PasswordResetDTO dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (!optionalUser.isPresent()) {
            return new PasswordResetResultDTO("USER_NOT_FOUND", "Utente non trovato");
        }

        User user = optionalUser.get();

        // Fase 1: richiesta codice
        if (dto.getCode() == null || dto.getNewPassword() == null) {
            String generatedCode = UUID.randomUUID().toString().substring(0, 6); // esempio: codice a 6 caratteri
            user.setResetCode(generatedCode);
            userRepository.save(user);
            emailService.sendPasswordResetCode(user.getEmail(), user.getName(), generatedCode);
            return new PasswordResetResultDTO("CODE_SENT", "Codice di reset inviato alla tua email");
        }

        // Fase 2: conferma reset con codice
        if (!dto.getCode().equals(user.getResetCode())) {
            return new PasswordResetResultDTO("INVALID_CODE", "Codice non valido");
        }

        if (dto.getNewPassword().isBlank()) {
            return new PasswordResetResultDTO("BLANK_PASSWORD", "La nuova password non può essere vuota");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetCode(null); // invalida il codice
        userRepository.save(user);
        emailService.sendChangePassowrdConfirm(user.getEmail(), user.getName());

        return new PasswordResetResultDTO("SUCCESS", "Password aggiornata con successo");
    }

}
