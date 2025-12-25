package com.OpenHearing.Usermanagement.repository;

import com.OpenHearing.Usermanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 1. Get all active users with pagination
    Page<User> findByIsActiveTrue(Pageable pageable);

    // 2. Get a single user only if they are active
    Optional<User> findByIdAndIsActiveTrue(Long id);

    // 3. Validation checks
    boolean existsByEmail(String email);
    boolean existsByAadhaar(String aadhaar);

    // 4. NEW: Custom Search Query
    // Searches for 'keyword' in Name OR Email, but only returns active users.
    // LOWER(...) makes it case-insensitive (e.g., "john" finds "John")
    @Query("SELECT u FROM User u WHERE " +
           "(LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "u.isActive = true")
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
}
