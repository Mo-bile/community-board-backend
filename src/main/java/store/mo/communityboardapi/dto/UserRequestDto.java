package store.mo.communityboardapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {

    // 유효성 검증을 위해 넣음 -> HTTP 에 담아서 Resp
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    // 문자열 길이 최소 6자 이상
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    //email 형식 검증
    @Email(message = "Invalid email format")
    private String email;

    // 기본생성자 req DTO의 경우 선언
    // 객체 직렬화/역직렬화 목적
    public UserRequestDto() {
    }

    public UserRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
