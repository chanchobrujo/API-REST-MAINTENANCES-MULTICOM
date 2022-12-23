package com.proyectoIntegrador.MultiCom.repository;

import com.proyectoIntegrador.MultiCom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user WHERE number_phone=?1 or email=?2", nativeQuery = true)
    Optional<User> findByVerifyEmailNumberCustomer(String numberPhone, String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
    boolean existsById(int id);
    boolean existsByEmail(String email);
    boolean existsByNumberPhone (String numberPhone);
}