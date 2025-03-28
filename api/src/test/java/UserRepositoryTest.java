import org.junit.jupiter.api.Test;
import org.shop.db.user.UserEntity;
import org.shop.db.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void repositoryTest() {
        // 간단한 CRUD 테스트
        UserEntity user = new UserEntity();
        user.setName("Test User");

        UserEntity savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull();

        Optional<UserEntity> foundUser = userRepository.findById(savedUser.getId());
        assertThat(foundUser).isPresent();
    }
}