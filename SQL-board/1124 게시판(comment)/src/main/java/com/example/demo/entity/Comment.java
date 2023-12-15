package com.example.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 비소유 지만 확인은 됨
    // 비소유지만 fk로 가지고 있음 보드를
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String writer;

    private String contents;

    @Builder
    public Comment(Long id, String writer, String contents, Board board){
        this.id = id;
        this.writer = writer;
        this.contents = contents;
        this.board =board;
    }


}
