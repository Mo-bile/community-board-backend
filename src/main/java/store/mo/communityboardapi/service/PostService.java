package store.mo.communityboardapi.service;

import store.mo.communityboardapi.dto.PostRequestDto;
import store.mo.communityboardapi.dto.PostResponseDto;

import java.util.List;
import java.util.Optional;

public interface PostService {

    // create
    Optional<PostResponseDto> createPost(Long userId, PostRequestDto postRequestDto);

    // update
    Optional<PostResponseDto> updatedPost(Long postId, Long userId, PostRequestDto postRequestDto);

    // delete
    boolean deletePost(Long postId, Long userId);

    // read
    List<PostResponseDto> getAllPosts(int offset, int limit);
    Optional<PostResponseDto> getPostById(Long postId);
}
