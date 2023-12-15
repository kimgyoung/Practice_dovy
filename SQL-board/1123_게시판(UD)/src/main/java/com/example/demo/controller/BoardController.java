package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    //private final BoardRepository boardRepository;

    // C
    @GetMapping("/create")
    public String create(){
        return "create";
    }

    // R
    // model <-> html
    @GetMapping(value = {"/paging", "/"})
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){

        Page<BoardDto> boards = boardService.paging(pageable);

        int blockLimit = 3;
        int startPage = (int)Math.ceil((double)pageable.getPageNumber() / blockLimit - 1) * blockLimit + 1;
        int endPage = (startPage+ blockLimit - 1) < boards.getTotalPages() ? (startPage + blockLimit -1) : boards.getTotalPages();

        model.addAttribute("boardList", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";
    }

    // 변경 전 데이터
    // 겟 이니까 보내기만 하는 거라서
    // 변경 하려면 변경 된 데이터를 들고 와야 하는 거
    // 업데이트는 원래 ㅍ ㅗ스트로 작성ㅇㄴ데
    // 데이터를 지정하는 역할
    // 인덱스로 보내주면

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){

        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);

        return "update";
    }

    // 변경 후 데이터
    // 찐 수정 -> post 주고 받음
    // html 데이터 -> 받아 옴
    @PostMapping("update")
    public String update(@ModelAttribute BoardDto boardDto){
        boardService.update(boardDto);
        return "redirect:/board/";
    }


    // 목록 중 한 줄 씩 불러 오기
    @GetMapping("/{id}")
    public String paging(@PathVariable Long id, Model model, @PageableDefault(page = 1) Pageable pageable){
        BoardDto boardDto = boardService.findById(id);

        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());

        /* 가져와 지는 지 확인 용
        System.out.println(board.get().getId());
        System.out.println(board.get().getTitle());
        System.out.println(board.get().getUsername());
        System.out.println(board.get().getContents());
        System.out.println(board.get().getCreateTime());
        System.out.println(board.get().getUpdateTime());
         */
        return "detail";
    }

    // U
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto){
        System.out.println(boardDto.getTitle() + " : " + boardDto.getContents());
        boardService.save(boardDto);
        return "redirect:/board/";
    }

    // D
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "home";
    }


}
