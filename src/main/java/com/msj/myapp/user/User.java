package com.msj.myapp.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.msj.myapp.Post.entity.Post;
import com.msj.myapp.program.Program;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
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
    private String userChoiceLevel;
    private Double activity; //활동량
    private String userChoiceGoal;
    private int goalCal;
    @Column(length = 500)
    private String secret;
    private String programName;

//   프로그램
    @ManyToOne
    @JoinColumn(name = "program_id")
    @JsonBackReference
    private Program program;

//    포스트
    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Post> post;
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
