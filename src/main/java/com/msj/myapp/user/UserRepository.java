package com.msj.myapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByPhone(String phone);

    @Override
//    @Query("SELECT u FROM User u LEFT JOIN FETCH u.program WHERE u.id = :userId")
    Optional<User> findById(Long aLong); //@Param("userId")
}
