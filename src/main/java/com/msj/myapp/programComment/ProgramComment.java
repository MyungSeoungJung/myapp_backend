package com.msj.myapp.programComment;

import com.msj.myapp.program.Program;
import com.msj.myapp.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    // 나중에 게시판 확장 대비 댓글 모듈화
    public ProgramComment createProgramComment(ProgramComment comment, Program program, User user) { //댓글 생성 모듈화
        comment.setUserId(user.getId());
        comment.setUserSex(user.getSex());
        comment.setUserName(user.getName());
        comment.setUserAge(user.getAge());
        comment.setProgram(program);
        return comment;
    }

    public Map<String, Object> createCommentResponse(ProgramComment comment) {  // 댓글 응답 모듈
        Map<String, Object> res = new HashMap<>();
        res.put("userId", comment.getUserId());
        res.put("content", comment.getContent());
        res.put("userName", comment.getUserName());
        res.put("userSex", comment.getUserSex());
        res.put("userAge", comment.getUserAge());
        return res;
    }
}
