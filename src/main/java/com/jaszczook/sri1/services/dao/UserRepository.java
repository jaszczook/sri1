package com.jaszczook.sri1.services.dao;

import com.jaszczook.sri1.services.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
