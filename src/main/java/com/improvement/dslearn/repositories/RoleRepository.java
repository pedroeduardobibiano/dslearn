package com.improvement.dslearn.repositories;

import com.improvement.dslearn.entities.Role;
import com.improvement.dslearn.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
