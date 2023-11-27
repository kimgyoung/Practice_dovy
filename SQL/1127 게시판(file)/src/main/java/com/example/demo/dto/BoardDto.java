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
    private String contents;
    private String username;
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

    // 리포지토리에서 다시 들고 왔을 때 다시 (엔티티를)Dto로 만들어 줘야 하는 데
    // 여기서 만들어 놓으면 변환 하기가 수월 해짐
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


