package store.mo.communityboardapi.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Commit;
import store.mo.communityboardapi.model.entity.Post;
import store.mo.communityboardapi.model.entity.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("testCreatePost")
    public void testCratePost() {
        // given
        Optional<User> optionalUser = userRepository.findById(1L);
        assertThat(optionalUser).isPresent(); // 유저가 존재하는지 검증

        User user = optionalUser.get();

        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setAuthor(user);
            post.setTitle("hi" + i);
            post.setContent("nice to meet you" + i);

            postRepository.save(post);
        }
        // when
        List<Post> posts = postRepository.findAll();

        // then
        assertThat(posts).hasSize(10);
        assertThat(posts).allMatch(post -> post.getAuthor().equals(user));

    }

    @Test
    @DisplayName("n+1 문제")
    public void testSelectPostProblem() {
        List<Post> postAndComments = postRepository.findAll();
        for (Post postAndComment : postAndComments) {
            System.out.println("postAndComment.getContent() = " + postAndComment.getContent());
            System.out.println("postAndComment.getComments() = " + postAndComment.getComments().size());
        }
    }

    @Test
    @DisplayName("n+1 문제 해결 방법 1 : fetch join")
    public void testSelectPostSol1() {
        List<Post> postAndComments = postRepository.fetchPostsAndCommentsUsingJoin();
        for (Post postAndComment : postAndComments) {
            System.out.println("postAndComment.getContent() = " + postAndComment.getContent());
            System.out.println("postAndComment.getComments() = " + postAndComment.getComments().size());
        }
    }

    @Test
    @DisplayName("n+1 문제 해결 방법 2 : EntityGraph")
    public void testSelectPostSol2() {
//        List<Post> postAndComments = postRepository.fetchPostsAndCommentsUsingEntityGraph();
//        for (Post postAndComment : postAndComments) {
//            System.out.println("postAndComment.getContent() = " + postAndComment.getContent());
//            System.out.println("postAndComment.getComments() = " + postAndComment.getComments().size());
//        }
    }

}
