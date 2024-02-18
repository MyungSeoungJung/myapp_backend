package com.msj.myapp.heart;

import com.msj.myapp.program.Program;
import com.msj.myapp.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Primary
public interface HeartRepository extends JpaRepository<Heart,Long> {

    Optional<Heart> findByUserAndProgram(User user, Program program);


}
