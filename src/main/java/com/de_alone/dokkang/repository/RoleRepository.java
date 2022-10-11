package com.de_alone.dokkang.repository;

import com.de_alone.dokkang.models.ERole;
import com.de_alone.dokkang.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
