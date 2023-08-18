package com.msj.myapp.program;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Primary
public interface ProgramRepository extends JpaRepository<Program,Long> {
    Optional<Program> findByProgramTitle(String programTitle);

}
