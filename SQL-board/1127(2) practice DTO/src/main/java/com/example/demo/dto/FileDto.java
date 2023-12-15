package com.example.demo.dto;

import com.example.demo.entity.Board;
import com.example.demo.entity.File;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class FileDto {

    private String filePath;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private Long boardId;

    public File toEntity(Board board){
        return File.builder()
                .filePath(filePath)
                .fileName(fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .board(board)
                .build();
    }

    /*
    public static FileDto toFileDto(File file){
        return new FileDto(
                file.getFileName(),
                file.getFilePath(),
                file.getFileType(),
                file.getBoard().getId()
        );
    }

     */
}
