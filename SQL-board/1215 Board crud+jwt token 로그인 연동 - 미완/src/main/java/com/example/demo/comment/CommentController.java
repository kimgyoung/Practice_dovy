package com.example.demo.comment;

import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDto commentDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails){
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDetails.getUser().getId();
        commentDto.setUserId(userId);
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
    public ResponseEntity<CommentDto> deleteComment
            (@PathVariable Long commentId,
             @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();

        if(.getUser().getId().equals(userId)){
            Comment deletedComment = commentService.deleteComment(commentId);
            CommentDto deletedCommentDto = CommentDto.fromEntity(deletedComment);
            return ResponseEntity.ok(deletedCommentDto);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment
            (@PathVariable Long commentId,
             @RequestParam String contents,
             @AuthenticationPrincipal CustomUserDetails userDetails){

        Comment updatedComment = commentService.updateComment(commentId, contents);

        if (updatedComment != null) {
            CommentDto updatedCommentDto = CommentDto.fromEntity(updatedComment);
            return ResponseEntity.ok(updatedCommentDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
