package store.mo.communityboardapi.service;

import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import store.mo.communityboardapi.dto.PostRequestDto;
import store.mo.communityboardapi.dto.PostResponseDto;
import store.mo.communityboardapi.model.entity.Post;
import store.mo.communityboardapi.model.entity.User;
import store.mo.communityboardapi.repository.PostRepository;
import store.mo.communityboardapi.repository.UserRepository;
import store.mo.communityboardapi.service.impl.PostServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    private User mockUser;
    private PostRequestDto mockCrePostRequestDto;
    private PostRequestDto mockUpPostRequestDto;
    private Post existingPost;
    private List<Post> mockPosts;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("jaeYoung");
        mockUser.setEmail("jym2013@abc.com");

        mockCrePostRequestDto = new PostRequestDto("Test title", "mock test content");

        // Mock PostRequestDto 설정
        mockUpPostRequestDto = new PostRequestDto();
        mockUpPostRequestDto.setTitle("Updated Title");
        mockUpPostRequestDto.setContent("Updated Content");

        // 기존 Post 설정
        existingPost = new Post();
        existingPost.setId(11L);
        existingPost.setTitle("Old Title");
        existingPost.setContent("Old Content");
        existingPost.setAuthor(mockUser);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("First Post");
        post1.setContent("Content of the first post");
        post1.setAuthor(mockUser);
        post1.setCreatedAt(LocalDateTime.now());
        post1.setUpdatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Second Post");
        post2.setContent("Content of the second post");
        post2.setAuthor(mockUser);
        post2.setCreatedAt(LocalDateTime.now());
        post2.setUpdatedAt(LocalDateTime.now());

        mockPosts = Arrays.asList(post1, post2);
    }

    @Test
    void createPost_Success() {
        // Given
        Post savedPost = new Post();
        savedPost.setId(1L);
        savedPost.setTitle(mockCrePostRequestDto.getTitle());
        savedPost.setContent(mockCrePostRequestDto.getContent());
        savedPost.setAuthor(mockUser);

        // stub 설정
        // userRepository.findById(1L)와 postRepository.save(any(Post.class))
        // repository layer 부분 호출시 반환할 값 지정
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // When
        Optional<PostResponseDto> responseDtoOptional = postService.createPost(1L, mockCrePostRequestDto);

        // Then

        assertTrue(responseDtoOptional.isPresent());

        PostResponseDto responseDto = responseDtoOptional.get();

        assertEquals(savedPost.getId(), responseDto.getId());
        assertEquals(savedPost.getTitle(), responseDto.getTitle());
        assertEquals(savedPost.getContent(), responseDto.getContent());
        assertEquals(mockUser.getUsername(), responseDto.getAuthorUserName());

        // verify
        // mock 객체 메서드 호출되었는지 확인용
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));

    }

    @Test
    void createPost_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<PostResponseDto> postOptional = postService.createPost(1L, mockUpPostRequestDto);

        // Then
        assertFalse(postOptional.isPresent());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void updatedPost_Success() {
        // given
        when(postRepository.findById(11L)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(existingPost);

        // when
        Optional<PostResponseDto> postResponseDto = postService.updatedPost(11L, 1L, mockUpPostRequestDto);

        // then
        assertTrue(postResponseDto.isPresent());

        PostResponseDto responseDto = postResponseDto.get();
        assertEquals(existingPost.getId(), responseDto.getId());
        assertEquals(mockUpPostRequestDto.getTitle(),responseDto.getTitle());
        assertEquals(mockUpPostRequestDto.getContent(), responseDto.getContent());
        assertEquals(mockUser.getUsername(), responseDto.getAuthorUserName());

        // verify interatcions
        verify(postRepository, times(1)).findById(11L);
        verify(postRepository, times(1)).save(any(Post.class));

    }

    @Test
    void updatedPost_PostNotFound() {
        // given
        when(postRepository.findById(11L)).thenReturn(Optional.empty());
        when(postRepository.save(any(Post.class))).thenReturn(existingPost);

        // when
        Optional<PostResponseDto> postResponseDto = postService.updatedPost(11L, 1L, mockUpPostRequestDto);

        // then
        assertFalse(postResponseDto.isPresent());

        // verify interatcions
        verify(postRepository, times(1)).findById(11L);
        verify(postRepository, times(0)).save(any(Post.class));

    }

    @Test
    void updatedPost_UserNotFound() {
        // given
        when(postRepository.findById(11L)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(existingPost);

        // when
        Optional<PostResponseDto> postResponseDto = postService.updatedPost(11L, 2L, mockUpPostRequestDto);

        // then
        assertFalse(postResponseDto.isPresent());

        // verify interatcions
        verify(postRepository, times(1)).findById(11L);
        verify(postRepository, times(0)).save(any(Post.class));
    }

    @Test
    void getAllPosts_Success() {
        // Given
        Page<Post> mockPage = new PageImpl<>(mockPosts);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        int offset = 0;
        int limit = 10;

        // When
        List<PostResponseDto> result = postService.getAllPosts(offset, limit);

        // Then
        assertEquals(2, result.size());
        assertEquals("First Post", result.get(0).getTitle());
        assertEquals("Second Post", result.get(1).getTitle());

        // Verify
        verify(postRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getAllPosts_EmptyResult() {
        // Given
        Page<Post> emptyPage = new PageImpl<>(List.of());
        when(postRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        int offset = 0;
        int limit = 10;

        // When
        List<PostResponseDto> result = postService.getAllPosts(offset, limit);

        // Then
        assertEquals(0, result.size());

        // Verify
        verify(postRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPostById_Success() {
        // Given
        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));

        // When
        Optional<PostResponseDto> result = postService.getPostById(1L);

        // Then
        assertTrue(result.isPresent());
        PostResponseDto responseDto = result.get();
        assertEquals(existingPost.getId(), responseDto.getId());
        assertEquals(existingPost.getTitle(), responseDto.getTitle());
        assertEquals(existingPost.getContent(), responseDto.getContent());
        assertEquals(existingPost.getAuthor().getUsername(), responseDto.getAuthorUserName());

        // Verify
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void getPostById_NotFound() {
        // Given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<PostResponseDto> result = postService.getPostById(1L);

        // Then
        assertFalse(result.isPresent());

        // Verify
        verify(postRepository, times(1)).findById(1L);
    }


}
