package com.example.demo.dto;

import com.example.demo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 복사 생성자
public class BoardDto {

    // 제목
    @Column(length = 50)
    private String title;

    // 내용
    @Column(length = 50)
    private String contents;


    private String username;


    public Board toEntity(){
        return Board.builder()
                .title(title)
                .contents(contents)
                .build();
    }

}
