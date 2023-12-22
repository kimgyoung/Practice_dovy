package com.example.demo.comment;

import com.example.demo.board.Board;
import com.example.demo.user.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
//* @ToString
@Setter
@Getter
public class CommentDto {

    private Long id;

    private String writer;

    private String contents;

    private Long BoardId;

    private LocalDateTime createTime;

    private Long UserId;

    public Comment toEntity(Board board, User user){
        return Comment.builder()
                .id(id)
                .writer(writer)
                .contents(contents)
                .board(board)
                .createTime(LocalDateTime.now())
                .user(user)
                .build();
    }

    // Comment 객체를 CommentDto 객체로 변환 하는 메서드
    public static CommentDto fromEntity(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getWriter(),
                comment.getContents(),
                comment.getBoard().getId(),
                comment.getCreateTime(),
                comment.getUser().getId()
        );
    }
    // contents 필드만을 가진 생성자
    // 댓글 내용 변경 시 필요한 Dto
    public CommentDto(String contents) {
        this.contents = contents;
    }

}