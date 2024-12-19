package store.mo.communityboardapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
