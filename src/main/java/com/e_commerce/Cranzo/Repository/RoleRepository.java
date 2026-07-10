package com.e_commerce.Cranzo.Repository;

import com.e_commerce.Cranzo.Entity.Role;
import com.e_commerce.Cranzo.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

}
