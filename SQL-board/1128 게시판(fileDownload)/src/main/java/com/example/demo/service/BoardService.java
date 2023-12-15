package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.FileDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardFile;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    // 고정시킴 = 불러올 때도 여기서 불러오면 됨
    private final String filePath = "C:/Users/G/Desktop/demo/boardFile/";

    // 가져온 데이터 DB에 저장
    @Transactional
    public void save(BoardDto boardDto, MultipartFile[] files) throws IOException {
        boardDto.setCreateTime(LocalDateTime.now());
        //boardRepository.save(boardDto.toEntity());

        // 내가 어디다 저장을 할 건지에 대한 경로
        // 학원 에서는 /G/ || 집 에서는 본인 PC 이름
        Path uploadPath = Paths.get(filePath);
        // 만약 경로가 없다면, 경로 생성
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        // 게시글 DB에 저장 후 pk를 받아 옴
        Long id = boardRepository.save(boardDto.toEntity()).getId();
        Board board = boardRepository.findById(id).get();


        // 파일 정보 저장
        // * 파일 있는지 없는지도 예외 처리 해주기
        // 파일이 있을 때만 들어옴
        // 파일이 NULL이면 들어오면 안됨
        for(MultipartFile file : files) {
            // 파일명 추출
            String originalFileName = file.getOriginalFilename();

            // 확장자 추출
            String formatType = originalFileName.substring(
                    originalFileName.lastIndexOf("."));

            // UUID 생성
            String uuid = UUID.randomUUID().toString();

            // 경로 지정
            // C:/Users/G/Desktop/demo/boardFile/{uuid + originalFileName}
            String path = filePath + uuid + originalFileName;

            // 경로에 파일을 저장. (NO DB)
            file.transferTo(new File(path));

            BoardFile boardFile = BoardFile.builder()
                    .filePath(filePath)
                    .fileName(originalFileName)
                    .fileType(formatType)
                    .uuid(uuid)
                    .fileSize(file.getSize())
                    .board(board)
                    .build();

            fileRepository.save(boardFile);
        }
    }

    // 페이지 리스트를 가져와서 보여 줌
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
                board.getCreateTime(),
                board.getUpdateTime()
        ));
    }

    public BoardDto findById(Long id) {
        // 예외 처리 생략
        // if(boardRepository.findById(id).isPresent()){}
        Board board = boardRepository.findById(id).get();
        return BoardDto.toBoardDto(board);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(BoardDto boardDto) {
        // 예외 처리 생략
        // if(boardRepository.findById(id).isPresent()){}
        Optional<Board> boardOptional = boardRepository.findById(boardDto.getId());
        Board board = boardOptional.get();

        board.updateFromDto(boardDto);

        boardRepository.save(board);

    }
}
