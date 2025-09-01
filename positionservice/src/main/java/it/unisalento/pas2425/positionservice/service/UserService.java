package it.unisalento.pas2425.positionservice.service;

import it.unisalento.pas2425.positionservice.domain.Role;
import it.unisalento.pas2425.positionservice.domain.User;
import it.unisalento.pas2425.positionservice.domain.UserStatus;
import it.unisalento.pas2425.positionservice.dto.*;
import it.unisalento.pas2425.positionservice.repositories.UserRepository;
import it.unisalento.pas2425.positionservice.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() != Role.ADMIN) // 👈 escludi admin
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    // Trova utente per ID
    public Optional<UserDTO> getUserDTOById(String id) {
        return userRepository.findById(id).map(this::toDTO);
    }

    // Trova oggetto User per ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    //  Aggiorna utente
    public Optional<UserDTO> updateUser(String id, UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();

        if (isNotBlank(dto.getName())) {
            user.setName(dto.getName().trim());
        }

        if (isNotBlank(dto.getSurname())) {
            user.setSurname(dto.getSurname().trim());
        }

        if (dto.getGenderType() != null) {
            user.setGender(dto.getGenderType());
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        if (isNotBlank(dto.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword().trim()));
        }

        if (isNotBlank(dto.getPhoneNumber())) {
            String phone = dto.getPhoneNumber().trim();
            // ✅ Controllo: solo cifre e lunghezza 10
            if (!phone.matches("\\d{10}")) {
                throw new IllegalArgumentException("Il numero di telefono deve contenere esattamente 10 cifre");
            }
            user.setPhoneNumber(phone);
        }

        if (dto.getUserStatus() != null) {
            user.setUserStatus(dto.getUserStatus());
        }

        userRepository.save(user);
        return Optional.of(toDTO(user));
    }

    private boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // Elimina utente
    public RegistrationResultDTO deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            return new RegistrationResultDTO(RegistrationResultDTO.NOT_FOUND, "Utente non trovato");
        }

        userRepository.deleteById(id);
        return new RegistrationResultDTO(RegistrationResultDTO.OK, "Utente eliminato con successo");
    }

    public UserListDTO getUsersByStatus(String status) {
        UserStatus enumStatus = UserStatus.valueOf(status.toUpperCase());
        List<User> users = userRepository.findByUserStatus(enumStatus).stream()
                .filter(user -> user.getRole() != Role.ADMIN) // 👈 escludi admin
                .collect(Collectors.toList());

        List<UserDTO> userDTOs = users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        UserListDTO list = new UserListDTO();
        list.setUserList(userDTOs);
        return list;
    }

    // Mapping Domain → DTO
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

    public AuthenticationResponseDTO authenticate(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> user = userRepository.findByEmail(authentication.getName());

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.get().getId());
        claims.put("role", user.get().getRole());

        final String jwt = jwtUtilities.generateToken(user.get().getEmail(), claims);

        // AGGIUNTO userId nella risposta
        return new AuthenticationResponseDTO(jwt, AuthenticationResponseDTO.OK, user.get().getId(),  "Login avvenuto con successo");
    }

    public UserResultDTO updateUserStatusOnly(String id, UserStatus newStatus) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return new UserResultDTO(UserResultDTO.USER_NOT_FOUND, "Utente non trovato");
        }
        User user = userOpt.get();
      /*  // Consentito solo se lo stato attuale è AVAILABLE o ASSIGNED
        if (user.getUserStatus() != UserStatus.AVAILABLE && user.getUserStatus() != UserStatus.ASSIGNED && user.getUserStatus() != UserStatus.ON_ROUTE) {
            return new UserResultDTO(UserResultDTO.USER_NOT_AVAILABLE,
                    "Utente non in stato compatibile per aggiornamento (stato attuale: " + user.getUserStatus() + ")");
        }*/

        user.setUserStatus(newStatus);
        userRepository.save(user);
        return new UserResultDTO(UserResultDTO.OK, "Stato aggiornato con successo");
    }

    public int countAvailableDrivers() {
        return userRepository.countByUserStatusAndRole(UserStatus.AVAILABLE, Role.USER);
    }


    public String getPushTokenByUserId(String userId) {
        return userRepository.findById(userId)
                .map(User::getPushToken)
                .orElseThrow(() -> new NoSuchElementException("Utente non trovato con id: " + userId));
    }

    public void savePushToken(String userId, String pushToken ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utente non trovato con id: " + userId));
        user.setPushToken(pushToken);
        userRepository.save(user);
    }

}
