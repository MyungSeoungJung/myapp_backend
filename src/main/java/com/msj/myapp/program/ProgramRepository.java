package com.msj.myapp.program;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Primary
public interface ProgramRepository extends JpaRepository<Program,Long> {
    Optional<Program> findByProgramTitle(String programTitle);

    List<Program> findByProgramGoal(String programGoal);


    Page<Program> findByProgramTitleContains(String programTitle, Pageable pageable);


    @Override
    Optional<Program> findById(Long aLong);


}
