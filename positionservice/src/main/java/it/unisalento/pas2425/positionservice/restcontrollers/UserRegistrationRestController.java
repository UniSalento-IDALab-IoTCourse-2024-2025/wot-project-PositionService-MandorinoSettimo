package it.unisalento.pas2425.positionservice.restcontrollers;

import it.unisalento.pas2425.positionservice.dto.RegistrationResultDTO;
import it.unisalento.pas2425.positionservice.dto.UserDTO;
import it.unisalento.pas2425.positionservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class UserRegistrationRestController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResultDTO> register(@RequestBody UserDTO userDTO) {
        RegistrationResultDTO result = registrationService.registerUser(userDTO);

        if (result.getCode() == RegistrationResultDTO.OK) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
