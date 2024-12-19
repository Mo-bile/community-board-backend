package store.mo.communityboardapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
