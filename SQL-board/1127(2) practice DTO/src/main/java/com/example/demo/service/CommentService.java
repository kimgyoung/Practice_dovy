package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public CommentDto save(CommentDto commentDto) {
        Optional<Board> optionalBoard =
                boardRepository.findById(commentDto.getBoardId());

        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            Comment comment = commentDto.toEntity(board);
            Comment savedComment = commentRepository.save(comment);
            return new CommentDto(savedComment);

        } else {
            return null;
        }
    }

    public void findById(Long id) {
        if(commentRepository.findById(id).isPresent()){
            Comment comment = commentRepository.findById(id).get();
        }
    }

    /*
    @Transactional
    public Comment update(Long id, CommentDto commentDto){
        Optional<Comment> optionalComment =
                commentRepository.findById(commentDto.getId());

        if(optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            comment.updateCommentDto(commentDto.getContents(), commentDto.getWriter());
            return null;
        } else {
            return null;
        }
    }

     */

    @Transactional
    public void delete(Long id){
        commentRepository.deleteById(id);
    }

}
