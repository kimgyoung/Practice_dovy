package com.example.demo.dto;

import com.example.demo.entity.Board;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 복사 생성자
//@ToString
public class BoardDto {

    private Long id;
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Board toEntity(){
        return Board.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .username(username)
                .createTime(createTime)
                .updateTime(LocalDateTime.now())
                .build();
    }

    public static BoardDto toBoardDto(Board board){
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getUsername(),
                board.getCreateTime(),
                board.getUpdateTime()
        );
    }
}


