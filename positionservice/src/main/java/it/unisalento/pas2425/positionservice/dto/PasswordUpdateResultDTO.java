package it.unisalento.pas2425.positionservice.dto;

public class PasswordUpdateResultDTO {
    public static final int OK = 200;
    public static final int USER_NOT_FOUND = 400;
    public static final int PASSWORD_ERROR = 401;
    public static final int SAME_PASSWORD = 402;
    public static final int BLANK_SPACE = 403;

    private int code;
    private String message;



    public PasswordUpdateResultDTO(int code, String message) {
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
}
