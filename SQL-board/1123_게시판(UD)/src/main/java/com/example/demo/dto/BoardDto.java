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

    // DTO는 DB에 들어가는 게 아니라서 콜롬 값 안 써줘도 됩니다.
    private Long id;

    // 제목
    private String title;

    // 내용
    private String contents;

    // 작성자 이름
    private String username;

    // 최초 작성 시간, 최초 값만 저장, 안 바뀜
    private LocalDateTime createTime;

    // 최근 수정 시간, 수정 했으면 수정한 시간이 들어감 = 계속 바뀔 수 있음
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


