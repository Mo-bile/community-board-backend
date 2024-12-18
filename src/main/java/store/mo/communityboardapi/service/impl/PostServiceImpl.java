package store.mo.communityboardapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mo.communityboardapi.dto.PostRequestDto;
import store.mo.communityboardapi.dto.PostResponseDto;
import store.mo.communityboardapi.model.entity.Post;
import store.mo.communityboardapi.model.entity.User;
import store.mo.communityboardapi.repository.PostRepository;
import store.mo.communityboardapi.repository.UserRepository;
import store.mo.communityboardapi.service.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Optional<PostResponseDto> createPost(Long userId, PostRequestDto postRequestDto) {

        // 1. 사용자 조회
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        // 2. 사용자 정보 가져오기
        User user = userOptional.get();

        // 3. 게시글 생성 및 설정
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setAuthor(user);

        // 4. 게시글 저장
        Post savedPost = postRepository.save(post);

        // 5. ResponseDto 생성
        PostResponseDto responseDto = new PostResponseDto(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                user.getUsername(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );

        return Optional.of(responseDto);

    }

    @Override
    @Transactional
    public  Optional<PostResponseDto> updatedPost(Long postId, Long userId, PostRequestDto postRequestDto) {

        // 1. 기존 게시글 불러오기
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return Optional.empty();
        }

        Post post = postOptional.get();

        // 2. 게시글과 수정인이 동일한지 검증
        if (!post.getAuthor().getId().equals(userId)) {
            return Optional.empty();
        }

        // 3. 게시글 수정
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        Post updatedPost = postRepository.save(post);

        // 4. 변경내역 응답
        PostResponseDto responseDto = new PostResponseDto(
                updatedPost.getId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getAuthor().getUsername(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt()
        );

        return Optional.of(responseDto);
    }

    @Override
    @Transactional
    public boolean deletePost(Long postId, Long userId) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return false;
        }

        Post post = postOptional.get();

        if (!post.getAuthor().getId().equals(userId)) {
            return false;
        }

        postRepository.delete(post);
        return true;
    }

    @Override
    public List<PostResponseDto> getAllPosts(int offset, int limit) {

        return postRepository.findAll()
                .stream()
                .skip(offset)
                .limit(limit)
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor().getUsername(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PostResponseDto> getPostById(Long postId) {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return Optional.empty();
        }

        Post post = postOptional.get();

        PostResponseDto responseDto = new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );

        return Optional.of(responseDto);
    }
}
