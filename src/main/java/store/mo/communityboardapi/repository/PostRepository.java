package store.mo.communityboardapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.entity.Post;

import java.util.List;
import java.util.Optional;

// Auto-Configuration 과 Component Scanning 덕분에 Bean등록 됨
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByStatusTrue(Pageable pageable);
    Optional<Post> findByIdAndStatusTrue(Long postId);

//    @Query("SELECT p FROM POST p JOIN FETCH p.comments WHERE p.status = true")
//    List<Post> fetchPostsAndCommentsUsingJoin();

    @EntityGraph(attributePaths = {"comments"})
    List<Post> findAllByStatusTrue();

}
