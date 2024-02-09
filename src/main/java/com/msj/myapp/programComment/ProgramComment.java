package com.msj.myapp.programComment;

import com.msj.myapp.program.Program;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProgramComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Program program;
    private long userId;
    private String content;
    private String userName;
    private String userSex;
    private int userAge;
}
