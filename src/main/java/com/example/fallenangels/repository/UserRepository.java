package com.example.fallenangels.repository;

import com.example.fallenangels.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly=true)
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Un objeto Optional que contiene el usuario encontrado, o vacío si no se encuentra ningún usuario con el correo electrónico especificado.
     */
    Optional<User> findByEmail(String email);

    List<User> findAll();

    Optional<User> findUserById(Integer id);


}
