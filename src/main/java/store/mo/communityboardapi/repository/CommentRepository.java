package store.mo.communityboardapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
