package com.msj.myapp.Post.entity;

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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String title;
    private String userName;
    private Long createdTime;
    private long creatorId;
    private long commentCnt;
    private String latestComment;
}
