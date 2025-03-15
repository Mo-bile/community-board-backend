package store.mo.communityboardapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Getter
//@AllArgsConstructor
//@Builder
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String authorUserName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 필드만으로 생성 -> 불변객체로 설계
    // 직렬화는 기본생성자 필요 없음

//    @Builder
    public PostResponseDto(Long id, String title, String content, String authorUserName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorUserName = authorUserName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PostResponseDto(Builder builder) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorUserName = authorUserName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static class Builder{
        private Long id;
        private String title;
        private String content;
        private String authorUserName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder authorUserName(String authorUserName) {
            this.authorUserName = authorUserName;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PostResponseDto build() {
            return new PostResponseDto(this);
        }
    }

    public static Builder builder() {
        return new Builder();
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
