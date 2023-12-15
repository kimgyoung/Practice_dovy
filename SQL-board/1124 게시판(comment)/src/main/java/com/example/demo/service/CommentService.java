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
    public Comment save(CommentDto commentDto) {
        Optional<Board> optionalBoard =
                boardRepository.findById(commentDto.getBoardId());

        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            /*
            System.out.println(board.getTitle());
            System.out.println(board.getUsername());
            System.out.println(board.getContents());
             */
            Comment comment = commentDto.toEntity(board);
            return commentRepository.save(comment);

        } else {
            return null;
        }
    }

/*
    public CommentDto findById(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            Comment comment = commentRepository.findById(id).get();
            return CommentDto.toCommentDto(comment);
        }
        return null;
    }


    @Transactional
    public Comment update(CommentDto commentDto){
        Optional<Comment> optionalComment =
                commentRepository.findById(commentDto.getId());

        if(optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            //comment.


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
