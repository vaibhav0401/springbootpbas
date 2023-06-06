package com.example.pbas.PBAS.repository;

import com.example.pbas.PBAS.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByName(String permissionName);
}