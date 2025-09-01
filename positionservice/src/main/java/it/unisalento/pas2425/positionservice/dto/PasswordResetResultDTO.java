package it.unisalento.pas2425.positionservice.dto;

public class PasswordResetResultDTO {
    private String code;
    private String message;

    public PasswordResetResultDTO() {}

    public PasswordResetResultDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
