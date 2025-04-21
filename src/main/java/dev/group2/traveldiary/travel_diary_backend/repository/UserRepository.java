package dev.group2.traveldiary.travel_diary_backend.repository;
import dev.group2.traveldiary.travel_diary_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUserId(Long id);
    boolean existsUserByUserId(Long userId);

    boolean existsUserByUsernameAndEmail(String username, String email);

    boolean existsUserByUsernameOrEmail(String username, String email);
}
