package com.msj.myapp.programComment;

import com.msj.myapp.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramCommentRepository  extends JpaRepository<ProgramComment, Long> {
    List<ProgramComment> findByProgram(Optional<Program> program);

    List<ProgramComment> findByUserId(long userId);


}
