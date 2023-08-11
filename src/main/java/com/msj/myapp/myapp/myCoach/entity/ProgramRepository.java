package com.msj.myapp.myapp.myCoach.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface ProgramRepository extends JpaRepository<Program,Long> {

}
