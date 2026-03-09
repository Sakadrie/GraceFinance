package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.security.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(LocalDateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    Page<User> findAllByIdNotNull(Pageable pageable);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "authorities")
    Page<User> findAllWithAuthoritiesByIdNotNull(Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO jhi_user_authority VALUES(:userId, :authority)", nativeQuery = true)
    void saveUserAuthority(@Param("userId") Long userId, @Param("authority") String authority);

    @Modifying
    @Query(value = "DELETE FROM jhi_user_authority", nativeQuery = true)
    void deleteAllUserAuthorities();

    @Modifying
    @Query(value = "DELETE FROM jhi_user_authority WHERE user_id = :userId", nativeQuery = true)
    void deleteUserAuthorities(@Param("userId") Long userId);

    boolean existsById(Long id);
}
