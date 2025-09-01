package it.unisalento.pas2425.positionservice.restcontrollers;

import it.unisalento.pas2425.positionservice.domain.UserStatus;
import it.unisalento.pas2425.positionservice.dto.*;
import it.unisalento.pas2425.positionservice.repositories.UserRepository;
import it.unisalento.pas2425.positionservice.security.JwtUtilities;
import it.unisalento.pas2425.positionservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    // GET all users
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserListDTO> getAll() {
        List<UserDTO> userList = userService.getAllUsers();
        UserListDTO listDTO = new UserListDTO();
        listDTO.setUserList(userList);
        return ResponseEntity.ok(listDTO);
    }

    // GET user by ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        return userService.getUserDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT update user
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<RegistrationResultDTO> deleteUser(@PathVariable String id) {
        RegistrationResultDTO deleted = userService.deleteUser(id);
        return deleted.getCode() == RegistrationResultDTO.OK
                ? ResponseEntity.ok(deleted)
                : ResponseEntity.badRequest().body(deleted);
    }

    // POST login (auth)
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> createAuthenticationToken(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            AuthenticationResponseDTO result = userService.authenticate(authentication);

            return ResponseEntity.ok(result);

        } catch (BadCredentialsException ex) {
            // Email o password sbagliati, campi vuoti o altro
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponseDTO(AuthenticationResponseDTO.BAD_CREDENTIALS, "Email o password errati"));
        } catch (Exception ex) {
            // Catch generico per qualsiasi altro errore inaspettato
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDTO(AuthenticationResponseDTO.UNKNOWN_ERROR, "Errore interno"));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<UserListDTO> getUsersByStatus(@PathVariable String status) {
        try{
            UserListDTO users = userService.getUsersByStatus(status);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // se status non è valido
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<UserResultDTO> updateUserStatus(
            @PathVariable String id,
            @RequestParam("status") UserStatus status) {

        UserResultDTO result = userService.updateUserStatusOnly(id, status);

        return result.getCode() == UserResultDTO.OK
                ? ResponseEntity.ok(result)
                : ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/available/count")
    public ResponseEntity<Integer> countAvailableUsers() {
        int count = userService.countAvailableDrivers(); // ← solo i camionisti
        return ResponseEntity.ok(count);
    }


    @GetMapping("/{id}/push-token")
    public ResponseEntity<PushTokenResponseDTO> getPushToken(@PathVariable String id) {
        PushTokenResponseDTO response = new PushTokenResponseDTO();
        try {
            String token = userService.getPushTokenByUserId(id);
            response.setCode(PushTokenResponseDTO.OK);
            response.setMessage("Token trovato");
            response.setPushToken(token);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            response.setCode(PushTokenResponseDTO.ERROR);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PostMapping("/{id}/push-token")
    public ResponseEntity<PushTokenResponseDTO> savePushToken(@PathVariable String id, @RequestBody PushTokenRequestDTO requestBody) {
        PushTokenResponseDTO response = new PushTokenResponseDTO();

        try {
            userService.savePushToken(id, requestBody.getPushToken());
            response.setCode(PushTokenResponseDTO.OK);
            response.setMessage("Token salvato con successo");
            response.setPushToken(requestBody.getPushToken());
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            response.setCode(PushTokenResponseDTO.ERROR);
            response.setMessage("Utente non trovato con id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
    }

}



