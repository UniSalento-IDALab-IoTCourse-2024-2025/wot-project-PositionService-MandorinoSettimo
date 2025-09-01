package it.unisalento.pas2425.positionservice.restcontrollers;

import it.unisalento.pas2425.positionservice.dto.PasswordResetDTO;
import it.unisalento.pas2425.positionservice.dto.PasswordResetResultDTO;
import it.unisalento.pas2425.positionservice.dto.PasswordUpdateDTO;
import it.unisalento.pas2425.positionservice.dto.PasswordUpdateResultDTO;
import it.unisalento.pas2425.positionservice.service.PasswordService;
import it.unisalento.pas2425.positionservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pwd")
public class PasswordRestController {

    private final UserService userService;
    private final PasswordService passwordService;

    public PasswordRestController(UserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    //se autenticato
    @PutMapping("/change")
    public ResponseEntity<PasswordUpdateResultDTO> changePassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        PasswordUpdateResultDTO result = passwordService.updatePassword(passwordUpdateDTO);

        if (result.getCode() == PasswordUpdateResultDTO.OK) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    //se non autenticato
    @PostMapping("/reset")
    public ResponseEntity<PasswordResetResultDTO> resetPassword(@RequestBody PasswordResetDTO dto) {
        PasswordResetResultDTO result = passwordService.handlePasswordReset(dto);
        return ResponseEntity.ok(result);
    }
}





