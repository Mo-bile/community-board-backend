package store.mo.communityboardapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    public PostRequestDto() {
    }

    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private PostRequestDto(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
    }

    // ✅ 빌더 클래스 추가
    public static class Builder {
        private String title;
        private String content;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public PostRequestDto build() {
            return new PostRequestDto(this);
        }
    }

    // ✅ 빌더 인스턴스 반환 메서드 추가
    public static Builder builder() {
        return new Builder();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
