package com.example.demo.file;

import com.example.demo.board.BoardService;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class FileDownloadController {
    private final BoardService boardService;

    // "C:/Users/G/Desktop/portfolio_board/boardFile/";
    // "C:/Users/김가영/Desktop/portfolio_board/Board/boardFile/";
    @GetMapping("/download/{uuid}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid, @PathVariable String fileName){
        Path filePath = Paths.get("C:/Users/G/Desktop/portfolio_board/boardFile/" + uuid + fileName);

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists() || resource.isReadable()){
                return ResponseEntity.ok()
                        .header("Content-Disposition"
                                , "attachMent; filename=\""
                                + resource.getFilename()
                                + "\"").body(resource);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }



    }

    @GetMapping("file/delete/{boardId}")
    public String fileDelete(@PathVariable("boardId") Long boardId,
                             @AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUser().getId();
        boardService.deleteFile(boardId, userId);
        return "redirect:/";
    }
}
