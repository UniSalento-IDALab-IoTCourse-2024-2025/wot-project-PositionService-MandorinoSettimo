package it.unisalento.pas2425.positionservice.dto;

public class RegistrationResultDTO {

    public static final int OK = 200;
    public static final int EMAIL_ALREADY_EXISTS = 401;
    public static final int MISSING_DATA = 402;
    public static final int INVALID_EMAIL_FORMAT = 403;
    public static final int NOT_FOUND = 404;
    public static final int INVALID_PHONE_NUMBER = 405;
    public static final int INTERNAL_ERROR = 500;

    private int code;
    private String message;
    private UserDTO user;

    public RegistrationResultDTO() {}

    public RegistrationResultDTO(int code, String message) {
        this.code = code;

        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
