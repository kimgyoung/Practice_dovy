package com.example.demo.comment;

import com.example.demo.comment.CommentDto;
import com.example.demo.board.Board;
import com.example.demo.comment.Comment;
import com.example.demo.board.BoardRepository;
import com.example.demo.comment.CommentRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment save(CommentDto commentDto) {
        Board foundBoard = boardRepository.findById(commentDto.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 게시글이 존재하지 않습니다: " + commentDto.getBoardId()));
        User foundUser = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 사용자가 존재하지 않습니다: " + commentDto.getUserId()));

        Comment comment = commentDto.toEntity(foundBoard, foundUser);
        return commentRepository.save(comment);
    }

    public List<CommentDto> getCommentsByBoardId(Long boardId) {
        List<Comment> commentList = commentRepository.findByBoardId(boardId);
        return commentList.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Comment deleteComment(Long commentId, Long userId) {
        Comment deletedComment = commentRepository.findById(commentId).orElse(null);
        if (deletedComment == null || !deletedComment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException();
        }
        commentRepository.deleteById(commentId);
        return deletedComment;
    }

    @Transactional
    public Comment updateComment(Long commentId, String contents) {
        Optional<Comment> optionalComment =
                commentRepository.findById(commentId);
        if(optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            CommentDto commentDto = new CommentDto(contents);
            comment.updateComment(commentDto);
            return comment;
        }
        return null;
    }
}
