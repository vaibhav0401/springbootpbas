package com.example.pbas.PBAS.repository;

import com.example.pbas.PBAS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User , Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
}