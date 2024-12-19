package store.mo.communityboardapi.dto;

import java.time.LocalDateTime;

public class CommentResponseDto{

    private Long id;
    private String content;
    private String authorUserName;
    private Long PostId;
    private LocalDateTime createdAt;

    public CommentResponseDto(Long id, String content, String authorUserName, Long postId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.authorUserName = authorUserName;
        PostId = postId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public Long getPostId() {
        return PostId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
