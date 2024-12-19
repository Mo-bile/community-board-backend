package store.mo.communityboardapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.model.entity.Post;

// Auto-Configuration 과 Component Scanning 덕분에 Bean등록 됨
public interface PostRepository extends JpaRepository<Post, Long> {
}
