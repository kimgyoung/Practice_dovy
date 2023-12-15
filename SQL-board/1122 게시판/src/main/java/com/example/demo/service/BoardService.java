package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // paging 이 진행 되는 함수
    // paging 을 표현할 리스트
    // Pageable: 리스트의 수량 정보를 포함 해놓은 클래스,인터 페이스
    public Page<BoardDto> paging(Pageable pageable){

        // 페이지 시작 번호 세팅
        int page = pageable.getPageNumber() -1 ;

        // 페이지에 포함 될 게시물 개수
        int size = 5;

        // 전체 게시물 불러옴
        Page<Board> boards = boardRepository.findAll(
                // 정렬 처리 해서 가져 옴(자동)
                PageRequest.of(page, size));

        return boards.map(board -> new BoardDto(
                board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContents(),
                board.getCreateTime()
        ));
        // Dto 형태로 반환
    }

    public void save(BoardDto boardDto){
        boardDto.setCreateTime(LocalDateTime.now());
        boardRepository.save(boardDto.toEntity());
    }

}
