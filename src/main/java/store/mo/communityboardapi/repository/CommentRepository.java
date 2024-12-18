package store.mo.communityboardapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.entity.Comment;
import store.mo.communityboardapi.entity.Post;
import store.mo.communityboardapi.entity.User;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
