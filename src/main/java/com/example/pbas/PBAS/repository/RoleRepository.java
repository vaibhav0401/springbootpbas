package com.example.pbas.PBAS.repository;


import com.example.pbas.PBAS.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String permissionName);
}