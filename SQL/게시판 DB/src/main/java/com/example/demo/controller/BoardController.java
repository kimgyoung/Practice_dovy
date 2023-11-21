package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String home(){
        return "createBoard";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto){

        System.out.println(boardDto.getTitle() + " : " + boardDto.getContents());

        boardService.save(boardDto);

        return "";
    }
}
