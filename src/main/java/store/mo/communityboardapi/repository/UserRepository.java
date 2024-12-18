package store.mo.communityboardapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mo.communityboardapi.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
