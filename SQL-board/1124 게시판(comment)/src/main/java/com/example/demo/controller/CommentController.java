package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDto commentDto){
        System.out.println(commentDto);
        Comment comment = commentService.save(commentDto);

        if (comment != null){
            return new ResponseEntity<>(commentDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(commentDto, HttpStatus.NOT_FOUND);
        }

    }

    /*
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        CommentDto commentDto = commentService.findById(id);
        model.addAttribute("comment", commentDto);
        return "update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute CommentDto commentDto){
        commentService.update(commentDto);
        return "redirect:/board/";
    }

     */


    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        commentService.delete(id);
        return "redirect:/board/";
    }

}
