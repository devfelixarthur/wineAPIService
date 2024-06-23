package com.api.wiveService.WineService.repository;

import com.api.wiveService.WineService.domain.user.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByEmail(String login);

    User findById(Long id);

    void deleteById(Long id);

    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.status = 'Ativo' AND u.id <> :id")
    Long countEmailUsed(@Param("email") String email, @Param("id") Long id);


    @Query("SELECT u.id FROM User u WHERE u.id = :id")
    Long findUserById(@Param("id") Long id);

    @Query("SELECT COUNT(u) FROM User u")
    Long countAllUsers();

    @Query("SELECT u FROM User u")
    Page<User> findAllUsers(Pageable pageable);
}
