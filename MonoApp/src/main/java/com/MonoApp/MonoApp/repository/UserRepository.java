
package com.MonoApp.MonoApp.repository;
import com.MonoApp.MonoApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
Optional<User> findByMail(String mail);
boolean existsByMail(String mail);
Optional<User> findByUsername(String username);
}