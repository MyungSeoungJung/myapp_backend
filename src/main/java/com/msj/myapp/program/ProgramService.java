package com.msj.myapp.program;

import com.msj.myapp.programComment.ProgramComment;
import com.msj.myapp.programComment.ProgramCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramService {
    @Autowired
    ProgramRepository programRepository;

    @Autowired
    ProgramCommentRepository commentRepo;

    @Transactional
    public void createComment(ProgramComment comment) {
        commentRepo.save(comment);
    }
}
