package it.unisalento.pas2425.positionservice.dto;

public class EmailResultDTO {
    private boolean success;
    private String message;
    private EmailStatus status;

    public EmailResultDTO() {
    }

    public enum EmailStatus {
        SENT,
        INVALID_EMAIL,
        SMTP_ERROR,
        UNKNOWN_ERROR
    }

    public EmailResultDTO(boolean success, String message, EmailStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public static EmailResultDTO success(String message) {
        return new EmailResultDTO(true, message, EmailStatus.SENT);
    }

    public static EmailResultDTO failure(String message, EmailStatus status) {
        return new EmailResultDTO(false, message, status);
    }
}
