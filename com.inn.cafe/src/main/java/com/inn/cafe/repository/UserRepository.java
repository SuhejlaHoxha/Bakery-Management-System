package com.inn.cafe.repository;

import com.inn.cafe.model.User;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(@Param("email") String email);

    @Query(name = "User.getAllUser")
    List<UserWrapper> getAllUser();

    @Modifying
    @Transactional
    @Query(name = "User.updateStatus")
    void updateStatus(@Param("id") Integer id, @Param("status") String status);

    @Query(name = "User.getAllAdmin")
    List<String> getAllAdmin();
}