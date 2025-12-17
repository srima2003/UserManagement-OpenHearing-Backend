package com.OpenHearing.Usermanagement.repository;

import com.OpenHearing.Usermanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByIsActiveTrue(Pageable pageable);
    Optional<User> findByIdAndIsActiveTrue(Long id);
    boolean existsByEmail(String email);
    boolean existsByAadhaar(String aadhaar);
}