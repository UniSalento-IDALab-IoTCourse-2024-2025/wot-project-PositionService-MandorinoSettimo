package it.unisalento.pas2425.positionservice.service;

import it.unisalento.pas2425.positionservice.domain.GenderType;
import it.unisalento.pas2425.positionservice.domain.Role;
import it.unisalento.pas2425.positionservice.domain.UserStatus;
import it.unisalento.pas2425.positionservice.domain.User;
import it.unisalento.pas2425.positionservice.dto.RegistrationResultDTO;
import it.unisalento.pas2425.positionservice.dto.UserDTO;
import it.unisalento.pas2425.positionservice.repositories.UserRepository;
import it.unisalento.pas2425.positionservice.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;



    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public RegistrationResultDTO registerUser(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank() ||
                userDTO.getPassword() == null || userDTO.getPassword().isBlank() ||
                userDTO.getName() == null || userDTO.getName().isBlank() ||
                userDTO.getSurname() == null || userDTO.getSurname().isBlank()) {
            return new RegistrationResultDTO(RegistrationResultDTO.MISSING_DATA, "Dati mancanti nella richiesta");
        }
        if (!userDTO.getEmail().matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) { //todo: sistemare che dopo @ ci possono essere più punti
            return new RegistrationResultDTO(RegistrationResultDTO.INVALID_EMAIL_FORMAT, "Indirizzo email non valido");
        }

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            return new RegistrationResultDTO(RegistrationResultDTO.EMAIL_ALREADY_EXISTS, "La mail è già associata ad un altro utente");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getRole() == null) {
            user.setRole(Role.USER);
        } else {
            user.setRole(userDTO.getRole());
        }

        if (userDTO.getGenderType() == null) {
            user.setGender(GenderType.UNSPECIFIED);
        } else {
            user.setGender(userDTO.getGenderType());
        }

        if (userDTO.getUserStatus() == null) {
            user.setUserStatus(UserStatus.AVAILABLE);
        } else {
            user.setUserStatus(userDTO.getUserStatus());
        }

        if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isBlank()) {
            String phone = userDTO.getPhoneNumber();
            if (!phone.matches("\\d+")) {
                return new RegistrationResultDTO(RegistrationResultDTO.INVALID_PHONE_NUMBER, "Il numero di telefono deve contenere solo cifre");
            }
            if (phone.length() > 10) {
                return new RegistrationResultDTO(RegistrationResultDTO.INVALID_PHONE_NUMBER, "Il numero di telefono deve contenere al massimo 10 cifre");
            }
            user.setPhoneNumber(phone);
        } else {
            user.setPhoneNumber(""); // Imposta un valore di default se il numero di telefono non è fornito
        }

        user = userRepository.save(user);
        emailService.sendRegistrationConfirmation(user.getEmail(), user.getName());

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());

        String jwt = jwtUtilities.generateToken(user.getEmail(), claims);;


        RegistrationResultDTO resultDTO = new RegistrationResultDTO(RegistrationResultDTO.OK, "Registrazione completata con successo");
        resultDTO.setUser(toDTO(user));
        return resultDTO;
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setGenderType(user.getGender());
        dto.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");
        dto.setUserStatus(user.getUserStatus() != null ? user.getUserStatus() : UserStatus.AVAILABLE);
        dto.setPushToken(user.getPushToken() != null ? user.getPushToken() : "");
        return dto;
    }
}
