package it.unisalento.pas2425.positionservice.dto;

public class PasswordResetDTO {
    private String email;
    private String code;         // opzionale: presente solo nella fase di conferma
    private String newPassword;  // opzionale: presente solo nella fase di conferma

    public PasswordResetDTO() {}

    public PasswordResetDTO(String email, String code, String newPassword) {
        this.email = email;
        this.code = code;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
