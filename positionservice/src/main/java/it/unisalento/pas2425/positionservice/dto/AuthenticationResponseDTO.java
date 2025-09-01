package it.unisalento.pas2425.positionservice.dto;

public class AuthenticationResponseDTO {
    public static final int OK = 200;
    public static final int BAD_CREDENTIALS = 400;
    public static final int UNKNOWN_ERROR = 401;
    private int code;
    private String message;
    private String userId;
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponseDTO(String jwt, int code, String userId, String message) {
        this.jwt = jwt;
        this.userId = userId;
        this.code = code;
        this.message = message;
    }

    public AuthenticationResponseDTO( int code, String message) {
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
