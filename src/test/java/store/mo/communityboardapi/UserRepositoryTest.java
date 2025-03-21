package store.mo.communityboardapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import store.mo.communityboardapi.entity.User;
import store.mo.communityboardapi.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest //슬라이스 테스트
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("testCreateUser")
    public void testCreateUser() {
        // given
        User user = new User();
        user.setUsername("mo_doe");
        user.setPassword("password456");
        user.setEmail("mo@example.com");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("mo_doe");
        assertThat(savedUser.getEmail()).isEqualTo("mo@example.com");
    }
}
