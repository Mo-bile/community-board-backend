package store.mo.communityboardapi.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String authorUserName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 필드만으로 생성 -> 불변객체로 설계
    // 직렬화는 기본생성자 필요 없음


    public PostResponseDto(Long id, String title, String content, String authorUserName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorUserName = authorUserName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 불변객체 이므로 setter 필요 X
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
