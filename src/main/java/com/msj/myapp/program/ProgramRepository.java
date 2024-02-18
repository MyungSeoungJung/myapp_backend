package com.msj.myapp.program;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Primary
public interface ProgramRepository extends JpaRepository<Program,Long> {
    Optional<Program> findByProgramTitle(String programTitle);

    List<Program> findByProgramGoal(String programGoal);

    Page<Program> findByProgramTitleContains(String programTitle, Pageable pageable);

    Page<Program> findByProgramGoalContains(String programGoal, Pageable pageable);
    @Query("select p from Program p left join fetch p.users where p.programGoal like '%근력증가%' ")
    Page<Program> findByMuscleBuild(String programGoal, Pageable pageable); // 근육 증가 필터링 메서드

    @Query("select p from Program p left join fetch p.users where p.programGoal like '%다이어트%' ")
    Page<Program> findByDietExercise(String programGoal, Pageable pageable); // 다이어트 필터링 메서드

    @Query("select p from Program p left join fetch p.users")
    List<Program> findAllProgram();  // 운동 프로그램 전체 찾는 메서드

    @Query("select p from Program p left join fetch p.users")
    Page<Program> findAllProgramPaging(Pageable pageable);  //운동 프로그램 페이징 메서드
    @Override
    Optional<Program> findById(Long aLong);

}
