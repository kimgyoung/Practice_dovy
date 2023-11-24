package com.example.demo.dto;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class CommentDto {

    private Long id;

    private String writer;

    private String contents;

    private Long BoardId;


    public Comment toEntity(Board board){
        return Comment.builder()
                .id(id)
                .writer(writer)
                .contents(contents)
                .board(board)
                .build();
    }

    public static CommentDto toCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getWriter(),
                comment.getContents(),
                // 밑에 불러온 부분..? 수정?
                comment.getBoard().getId()
        );
    }



}
