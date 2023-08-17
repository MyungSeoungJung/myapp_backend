package com.msj.myapp.myapp.program;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.msj.myapp.myapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Program {
//    키,몸무게,운동목적 같은 신체사항은 profile에 저장

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  //Id는 고유한 값이라 정수여야만 함

    private String rate;    //별점
    private String programLevel;   //프로그램이 초급자용인지 고급자용인지
    private String programGoal;  //다이어트 프로그램인지

    @Column(columnDefinition = "LONGTEXT")
    private String img;

    private String programIntro; //프로그램 소개글

    @Column(unique = true)
    private String programTitle;  // 프로그램 제목

    @JsonManagedReference
    @OneToMany(mappedBy = "program")
    private List<User> users;

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", rate='" + rate + '\'' +
                ", programLevel='" + programLevel + '\'' +
                ", programGoal='" + programGoal + '\'' +
                ", img='" + img + '\'' +
                ", programIntro='" + programIntro + '\'' +
                ", programTitle='" + programTitle + '\'' +
                '}';
    }
}
