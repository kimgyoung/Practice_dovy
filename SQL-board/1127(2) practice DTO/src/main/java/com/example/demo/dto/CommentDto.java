package com.example.demo.dto;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class CommentDto {

    private LocalDateTime createTime;
    private Long id;

    private String writer;

    private String contents;

    private Long BoardId;

    public CommentDto(Comment savedComment) {
        this.id = savedComment.getId();
        this.writer = savedComment.getWriter();
        this.contents = savedComment.getContents();
        this.createTime = savedComment.getCreateTime();
        this.BoardId = savedComment.getBoard().getId();
    }


    public Comment toEntity(Board board){
        return Comment.builder()
                .id(id)
                .writer(writer)
                .contents(contents)
                .createTime(LocalDateTime.now())
                .board(board)
                .build();
    }

    /*
    // Comment 객체를 CommentDto 객체로 변환하는 메서드
    public static CommentDto fromEntity(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getWriter(),
                comment.getContents(),
                comment.getCreateTime(LocalDateTime.now()),
                comment.getBoard().getId()
        );
    }

     */

}
