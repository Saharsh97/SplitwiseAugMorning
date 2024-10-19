package org.example.splitwiseaugmorning.repositories;

import org.example.splitwiseaugmorning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
}
