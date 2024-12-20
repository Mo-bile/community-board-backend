package store.mo.communityboardapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.mo.communityboardapi.dto.PostRequestDto;
import store.mo.communityboardapi.dto.PostResponseDto;
import store.mo.communityboardapi.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 생성
    @PostMapping("/{userId}")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable Long userId, @RequestBody PostRequestDto postRequestDto) {
        Optional<PostResponseDto> response = postService.createPost(userId, postRequestDto);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 게시글 수정
    @PutMapping("/{postId}/{userId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @PathVariable Long userId, @RequestBody PostRequestDto postRequestDto) {
        Optional<PostResponseDto> response = postService.updatedPost(postId, userId, postRequestDto);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @PathVariable Long userId) {
        boolean isDeleted = postService.deletePost(postId, userId);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 모든 게시글 조회 (페이징 지원)
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
        List<PostResponseDto> posts = postService.getAllPosts(offset, limit);
        return ResponseEntity.ok(posts);
    }

    // 게시글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        Optional<PostResponseDto> response = postService.getPostById(postId);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
