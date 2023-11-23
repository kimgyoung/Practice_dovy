package com.example.demo.entity;

import com.example.demo.dto.BoardDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Board {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 이름
    @Column(length = 45)
    private String username;

    // 게시물 제목
    @Column(length = 100)
    private String title;

    // 내용
    @Column(length = 256)
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

    public void updateFromDto(BoardDto boardDto){
        // 모든 변경 사항을 셋팅
        this.title = boardDto.getTitle();;
        this.contents = boardDto.getContents();
        this.username = boardDto.getUsername();
    }
}
