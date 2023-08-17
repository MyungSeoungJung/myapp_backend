package com.msj.myapp.myapp.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.msj.myapp.myapp.program.Program;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//User필드 그대로 데이터베이스에 table이 생김
public class User {
    //   이름,성별,나이 같은 인적사항 user에 저장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    @Column(unique = true)  //pk 키 넣어줘야 함.
    private String phone;
    private int height;
    private int weight;
    private String sex;
    private String userChoiceLevel;   //초급자 중급자 //상급자
    private Double activity; //활동량
    private String userChoiceGoal;    //다이어트 //근비대
    private int goalCal; //목표 칼로리 주 0.25감량 0.5감량 등
    @Column(length = 500)
    private String secret;
    private String programName;


    @ManyToOne
    @JoinColumn(name = "program_id")
    @JsonBackReference
    private Program program;

//    유저,프로그램 다대일 관계를 맺으면 서로 필드를 공유하기때문에
//    재귀함수가 발생할 수 있는데 toString() 메서드를 오버라이드해서 각자 클래스의 필드들을 분리해 놓아 재귀함수 방지

//    toString() =  객체의 내용을 문자열로 표현하는데 사용
//    객체의 내용을 문자열로 표현하려면 주로 해당 객체의 필드 값을 문자열로 조합하여 반환하는데,
//       이 때 객체의 필드 중에서 다른 객체를 참조하는 필드가 있으면, 기본적으로는 그 참조된 객체의 toString() 메서드가 호출

//    재귀함수가 발생되는 이유 =
//    서로 다대일 관계를 형성하고 있고, 각 클래스의 toString() 메서드에서 참조하고 있는 상대 클래스의 객체를 호출하게 되면,
//    서로 간의 toString() 메서드 호출이 계속해서 발생하여 무한한 호출이 발생해서  = 오버스택플로우(재귀함수) 발생
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", sex='" + sex + '\'' +
                ", userChoiceLevel='" + userChoiceLevel + '\'' +
                ", activity=" + activity +
                ", userChoiceGoal='" + userChoiceGoal + '\'' +
                ", goalCal=" + goalCal +
                '}';
    }
}
