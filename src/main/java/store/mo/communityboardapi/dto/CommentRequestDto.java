package store.mo.communityboardapi.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequestDto {

    @NotBlank(message = "content is Required")
    private String content;

    public CommentRequestDto() {
    }

    public CommentRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
