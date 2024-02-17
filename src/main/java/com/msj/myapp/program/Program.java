package com.msj.myapp.program;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.msj.myapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Component
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String rate;    //별점
    private String programLevel;   //프로그램이 초급자용인지 고급자용인지
    private String programGoal;  //프로그램 목적

    @Column(columnDefinition = "LONGTEXT")
    private String img;

    private String programIntro; //프로그램 소개글

    @Column(unique = true)
    private String programTitle;  // 프로그램 제목

    private String coachName;

//  @JsonManagedReference는 일반적으로 다수의 관계를 가지는 엔티티에 사용
    @JsonManagedReference  // 객체를 직렬화할 때 해당 엔티티가 참조하는 다른 엔티티의 정보를 무시하고, 역참조가 발생하지 않도록 합니다.
    @OneToMany(mappedBy = "program",fetch = FetchType.LAZY)
    private List<User> users;
//    순환 참조 A -> B를 참조 ,   B -> A를 참조 계속 참조가 돌면서 재귀함수가 발생

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
                ", coachName='" + coachName + '\'' +

                '}';
    }

    public Map<String,Object> createProgramResponse (Program program){  //운동 프로그램 응답 모듈화
        Map<String, Object> res = new HashMap<>();
        res.put("programTitle",program.getProgramTitle());
        res.put("programGoal",program.getProgramGoal());
        res.put("programLevel",program.getProgramLevel());
        res.put("programIntro",program.getProgramIntro());
        res.put("programImg",program.getImg());
        res.put("programRate",program.getRate());
        res.put("coachName",program.getCoachName());
        return res;
    }
}
