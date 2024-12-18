package store.mo.communityboardapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.entity.Post;
import store.mo.communityboardapi.entity.User;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<User> findByAuthorUsername(String username);
    Optional<User> findByAuthorId(Long id);
}
