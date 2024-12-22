package store.mo.communityboardapi.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import store.mo.communityboardapi.model.entity.Comment;
import store.mo.communityboardapi.model.entity.Post;
import store.mo.communityboardapi.model.entity.User;

import java.util.Optional;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void insertCommentsForExistingPosts() {
        // 글쓴이 조회 (DB에 이미 존재하는 User ID 1을 사용)
        Optional<User> authorOptional = userRepository.findById(1L);
        assertThat(authorOptional).isPresent();
        User author = authorOptional.get();

        // 기존 Post 데이터에 댓글 삽입 (ID 11 ~ 33)
        LongStream.rangeClosed(11, 33).forEach(postId -> {
            Optional<Post> postOptional = postRepository.findById(postId);
            assertThat(postOptional).isPresent(); // Post 존재 확인
            Post post = postOptional.get();

            Comment comment = new Comment();
            comment.setContent("Comment for Post ID " + postId);
            comment.setPost(post);
            comment.setAuthor(author);
            commentRepository.save(comment);
        });

        // 댓글 데이터 검증
        long commentCount = commentRepository.count();
        assertThat(commentCount).isEqualTo(23); // 11 ~ 33까지 총 23개
    }
}
