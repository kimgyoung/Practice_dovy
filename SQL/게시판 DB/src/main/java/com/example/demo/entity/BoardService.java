package com.example.demo.entity;

import com.example.demo.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void save(BoardDto boardDto){
        boardRepository.save(boardDto.toEntity());
    }

}
