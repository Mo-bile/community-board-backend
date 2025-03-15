package store.mo.communityboardapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import store.mo.communityboardapi.entity.Post;
import store.mo.communityboardapi.entity.User;
import store.mo.communityboardapi.repository.PostRepository;
import store.mo.communityboardapi.repository.UserRepository;

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
            System.out.println(" --- 시작 지점 --- ");
            System.out.println("postAndComment.getContent() = " + postAndComment.getContent());
            System.out.println(" --- n + 1 쿼리 --- ");
            System.out.println("postAndComment.getComments() = " + postAndComment.getComments().size());
        }
    }

    @Test
    @DisplayName("n+1 문제 해결 방법 1 : Eager Loading으로 N+1 문제 해결")
    public void testSelectPostWithEagerLoading() {
        List<Post> postAndComments = postRepository.findAll(); // Post와 Comment를 한 번에 가져옴
        for (Post postAndComment : postAndComments) {
            System.out.println("postAndComment.getContent() = " + postAndComment.getContent());
            System.out.println(" --- Eager 로딩 확인 --- ");
            System.out.println("postAndComment.getComments() = " + postAndComment.getComments().size()); // 추가 쿼리 없음

        }
    }


//    @Test
//    @DisplayName("n+1 문제 해결 방법 2 : fetch join")
//    public void testSelectPostSol1() {
//        List<Post> postAndComments = postRepository.fetchPostsAndCommentsUsingJoin();
//        for (Post postAndComment : postAndComments) {
//            System.out.println("postAndComment.getContent() = " + postAndComment.getContent());
//            System.out.println("postAndComment.getComments() = " + postAndComment.getComments().size());
//        }
//    }

    @Test
    @DisplayName("n+1 문제 해결 방법 3 : EntityGraph")
    public void testSelectPostSol2() {
        List<Post> postAndComments = postRepository.findAllByStatusTrue();
        for (Post postAndComment : postAndComments) {
            System.out.println("postAndComment.getContent() = " + postAndComment.getContent());
            System.out.println("postAndComment.getComments() = " + postAndComment.getComments().size());
        }
    }

}


