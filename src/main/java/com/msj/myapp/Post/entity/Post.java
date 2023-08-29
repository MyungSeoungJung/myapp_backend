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

    // 댓글 총
    private long commentCnt;
    // 최근 댓글 내용
    private String latestComment;
}
