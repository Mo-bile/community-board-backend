package store.mo.communityboardapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import store.mo.communityboardapi.dto.PostRequestDto;
import store.mo.communityboardapi.dto.PostResponseDto;
import store.mo.communityboardapi.model.entity.Post;
import store.mo.communityboardapi.model.entity.User;
import store.mo.communityboardapi.repository.PostRepository;
import store.mo.communityboardapi.repository.UserRepository;
import store.mo.communityboardapi.service.impl.PostServiceImpl;

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
    private PostRequestDto mockPostRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("jaeYoung");
        mockUser.setEmail("jym2013@abc.com");

        mockPostRequestDto = new PostRequestDto("Test title", "mock test content");
    }

    @Test
    void createPost_Success() {
        // Given
        Post savedPost = new Post();
        savedPost.setId(1L);
        savedPost.setTitle(mockPostRequestDto.getTitle());
        savedPost.setContent(mockPostRequestDto.getContent());
        savedPost.setAuthor(mockUser);

        // stub 설정
        // userRepository.findById(1L)와 postRepository.save(any(Post.class))
        // repository layer 부분 호출시 반환할 값 지정
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // When
        PostResponseDto responseDto = postService.createPost(1L, mockPostRequestDto);

        // Then
        assertNotNull(responseDto);
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

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postService.createPost(1L, mockPostRequestDto);
        });

        assertEquals("User not found", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, never()).save(any(Post.class));
    }



}
