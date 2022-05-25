package com.don.usersservice.repository;

import com.don.usersservice.model.Role;
import com.don.usersservice.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
    Optional<Role> findByName(String roleName);

}
