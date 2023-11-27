package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.FileDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.File;
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

    //@Autowired
    private final BoardRepository boardRepository;
    //@Autowired
    private final FileRepository fileRepository;

    // 고정시킴 = 불러올 때도 여기서 불러오면 됨
    private final String filePath = "C:/Users/G/Desktop/demo/boardFile/";

    // 가져온 데이터 DB에 저장
    @Transactional
    public void save(BoardDto boardDto, MultipartFile[] files) throws IOException {
        boardDto.setCreateTime(LocalDateTime.now());
        boardRepository.save(boardDto.toEntity());

        // ※ 파일 정보 저장
        // 파일 자체가 세터가 없으니까 파일 파일= 뉴파일, 세이브(파일) 말고
        // 디티오 & 디티오.투엔티티

        //FileDto file = new FileDto();
        for(MultipartFile file : files) {
            createFilePath(file);
            FileDto fileDto = new FileDto();
            fileDto.setFileName(file.getOriginalFilename());

            fileRepository.save(fileDto.toEntity());
        }

        //fileRepository.save(file.toEntity());
    }

    // 하나의 싱글톤?
    // 보드 서비스. 찍었을 때 보여지지 않았으면 좋겠어 -> private
    private String createFilePath(MultipartFile file) throws IOException {
        // 내가 어디다 저장을 할 건지에 대한 경로
        // 학원 에서는 /G/
        // 집 에서는 본인 PC 이름

        Path uploadPath = Paths.get(filePath);

        // 만약 경로가 없다면, 경로 생성
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        // 파일명 추출
        String originalFileName = file.getOriginalFilename();
        System.out.println(originalFileName);

        // 확장자 추출
        String formatType = originalFileName.substring(
                originalFileName.lastIndexOf(".")); // . 뒤의 모든 문자열 추출
        System.out.println(formatType);

        //originalFileName - lastIndexOf 빼줘야 파일명만(확장자 제외)

        // 파일 이름만 남김
        originalFileName = originalFileName.substring(
                0, originalFileName.lastIndexOf(".")
        );
        System.out.println(originalFileName);

        // UUID 생성
        String uuid = UUID.randomUUID().toString();

        Path path = uploadPath.resolve(
                uuid + originalFileName + formatType
        );

        Files.copy(file.getInputStream(),
                path,
                StandardCopyOption.REPLACE_EXISTING
                );

        // ※ 위의 작업들 따로 분리 && 보드랑 연결도 시켜야 함

        return "";
    }


    // paging 이 진행 되는 함수
    // paging 을 표현할 리스트
    // Pageable: 리스트의 수량 정보를 포함 해놓은 클래스,인터 페이스

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
        // Dto 형태로 반환
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
