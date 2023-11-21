package com.example.demo.entity;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
public class Board {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 이름
    @Column(length = 45, nullable = false)
    private String username;

    // 게시물 제목
    @Column(length = 100, nullable = false)
    private String title;

    // 내용
    @Column(length = 256, nullable = false)
    private String contents;

    // 최초 작성 시간
    private LocalDateTime createTime;

    // 최근 수정 시간
    private LocalDateTime updateTime;

    @Builder
    public Board(Long id, String username, String title, String contents, LocalDateTime createTime, LocalDateTime updateTime){
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

}
