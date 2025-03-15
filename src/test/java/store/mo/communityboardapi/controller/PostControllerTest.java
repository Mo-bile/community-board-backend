package store.mo.communityboardapi.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import store.mo.communityboardapi.dto.PostRequestDto;
import store.mo.communityboardapi.dto.PostResponseDto;
import store.mo.communityboardapi.service.PostService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @DisplayName("post 등록 선공")
    @Test
    void postSuccess() throws Exception{

        //given
        PostRequestDto requestDto = postRequest();
        PostResponseDto responseDto = postResponseDto();

        doReturn(Optional.of(responseDto)).when(postService)
                .createPost(any(Long.class),any(PostRequestDto.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/posts/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()));
    }

    private PostRequestDto postRequest() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("호랑나비")
                .content("한마리가")
                .build();
        return postRequestDto;
    }

    private PostResponseDto postResponseDto() {
        return PostResponseDto.builder()
                .title("호랑나비")
                .content("한마리가")
                .id(10L)
                .authorUserName("mo")
//                .createdAt(LocalDateTime.parse("2024-12-22 16:32:57.120853"))
                .build();
    }

}