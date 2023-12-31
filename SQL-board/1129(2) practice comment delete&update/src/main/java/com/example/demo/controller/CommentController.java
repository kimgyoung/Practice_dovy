package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDto commentDto){

        CommentDto SavedCommentDto = CommentDto.fromEntity(commentService.save(commentDto));

        if (SavedCommentDto != null){
            return new ResponseEntity<>(SavedCommentDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long boardId) {
        List<CommentDto> commentList = commentService.getCommentsByBoardId(boardId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("/delete/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long commentId) {
        Comment deletedComment = commentService.deleteComment(commentId);
        CommentDto deletedCommentDto = CommentDto.fromEntity(deletedComment);
        return ResponseEntity.ok(deletedCommentDto);
    }
/*
    @PostMapping("/update/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestParam String contents){
        Comment updateComment = commentService.updateComment(commentId, contents);
        if (updateComment != null) {
            return ResponseEntity.ok(updateComment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

 */
    @PostMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestParam String contents){
        Comment updatedComment = commentService.updateComment(commentId, contents);

        if (updatedComment != null) {
            CommentDto updatedCommentDto = CommentDto.fromEntity(updatedComment);
            return ResponseEntity.ok(updatedCommentDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
