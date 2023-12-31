package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public Comment save(CommentDto commentDto) {
        Optional<Board> optionalBoard =
                boardRepository.findById(commentDto.getBoardId());

        if(optionalBoard.isPresent()){
            Board foundBoard  = optionalBoard.get();
            Comment comment = commentDto.toEntity(foundBoard);
            return commentRepository.save(comment);
        } else {
            return null;
        }
    }

    public List<CommentDto> getCommentsByBoardId(Long boardId) {
        List<Comment> commentList = commentRepository.findByBoardId(boardId);
        return commentList.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Comment deleteComment(Long commentId) {
        Comment deletedComment = commentRepository.findById(commentId).orElse(null);
        if (deletedComment != null) {
            commentRepository.deleteById(commentId);
        }
        return deletedComment;
    }

    public void findById(Long id) {
        if(commentRepository.findById(id).isPresent()){
            Comment comment = commentRepository.findById(id).get();
        }
    }

}
