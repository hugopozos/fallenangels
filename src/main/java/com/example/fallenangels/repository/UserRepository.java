package com.example.fallenangels.repository;

import com.example.fallenangels.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly=true)
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional <User> findByEmail(String email);

}
