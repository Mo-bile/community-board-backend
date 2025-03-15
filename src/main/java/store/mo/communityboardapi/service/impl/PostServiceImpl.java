package store.mo.communityboardapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mo.communityboardapi.dto.PostRequestDto;
import store.mo.communityboardapi.dto.PostResponseDto;
import store.mo.communityboardapi.repository.PostRepository;
import store.mo.communityboardapi.repository.UserRepository;
import store.mo.communityboardapi.entity.Post;
import store.mo.communityboardapi.entity.User;
import store.mo.communityboardapi.service.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

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
            logger.warn("Unauthorized update attempt by User ID {}", userId);
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

        logger.info("Post with ID {} updated successfully by User ID {}"
        ,savedPost.getAuthor().getId(),savedPost.getTitle());

        // 5. ResponseDto 생성
//        PostResponseDto responseDto = new PostResponseDto(
//                savedPost.getId(),
//                savedPost.getTitle(),
//                savedPost.getContent(),
//                user.getUsername(),
//                savedPost.getCreatedAt(),
//                savedPost.getUpdatedAt()
//        );

        PostResponseDto responseDto = PostResponseDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .authorUserName(user.getUsername())
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();

        return Optional.of(responseDto);

    }

    @Override
    @Transactional
    public  Optional<PostResponseDto> updatedPost(Long postId, Long userId, PostRequestDto postRequestDto) {

        // 1. 기존 게시글 불러오기
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            logger.warn("Post with ID {} not found", postId);
            return Optional.empty();
        }

        Post post = postOptional.get();

        // 2. 게시글과 수정인이 동일한지 검증
        if (!post.getAuthor().getId().equals(userId)) {
            logger.warn("Unauthorized update attempt by User ID {} on Post ID {}", userId, postId);
            return Optional.empty();
        }

        // 3. 게시글 수정
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        Post updatedPost = postRepository.save(post);
        logger.info("Post with ID {} updated successfully by User ID {}", postId, userId);


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
            logger.warn("Post with ID {} not found", postId);
            return false;
        }

        Post post = postOptional.get();

        if (!post.getAuthor().getId().equals(userId)) {
            logger.warn("Unauthorized update attempt by User ID {} on Post ID {}", userId, postId);
            return false;
        }

        post.setStatus(false);
        postRepository.save(post);
        return true;
    }

    @Override
    public List<PostResponseDto> getAllPosts(int offset, int limit) {

        // Pageable 객체 생성 (offset은 페이지 번호로 계산)
        Pageable pageable = PageRequest.of(offset / limit, limit);
        logger.info("Fetching posts with offset {} and limit {}", offset, limit);

        // DB 에서 필요한 부분만 조회
        Page<Post> postPage = postRepository.findAllByStatusTrue(pageable);

        // 각 Post 의 comments 에 접근 (n+1 문제 발생우려 존재) -> Fetch join, @EntityGraph 이용
        postPage.forEach(post -> post.getComments().size());
        logger.info("pos",String.valueOf(postPage.getSize()));

        // 조회된 데이터로 반환
        List<PostResponseDto> postResponseDtos = postPage
                .stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor().getUsername(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        logger.info("Retrieved {} posts", postResponseDtos.size());
        return postResponseDtos;
    }

    @Override
    public Optional<PostResponseDto> getPostById(Long postId) {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            logger.warn("Post with ID {} not found", postId);
            return Optional.empty();
        }

        Post post = postOptional.get();
        logger.info("Post with Id {} is Found", postId);

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
