package com.msj.myapp.myapp.myCoach.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Primary
public interface ProgramRepository extends JpaRepository<Program,Long> {

}
