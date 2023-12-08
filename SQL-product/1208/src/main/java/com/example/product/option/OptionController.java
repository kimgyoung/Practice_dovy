package com.example.product.option;

import com.example.product.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OptionController {
    private final OptionService optionService;

    /**
    * @param id
    * ProductId
    * @return
    * OptionResponse.FindByProductIdDto 리스트로 반환
    * */

    // 옵션 개별 상품 검색
    @GetMapping("/products/{id}/options")
    // 제품 id를 받아 해당 상품의 옵션 리스트 반환
    public ResponseEntity<?> findById(@PathVariable Long id){
        List<OptionResponse.FindByProductIdDto> optionResponses =
                optionService.findByProductId(id);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    // 옵션 전체 상품 검색
    @GetMapping("/options")
    // 모든 옵션을 리스트로 반환
    public ResponseEntity<?> findAll(){
        List<OptionResponse.FindAllDto> optionResponses =
                optionService.findAll();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    // 새로운 옵션 값 저장 후 반환
    @PostMapping("/options/save")
    public ResponseEntity<?> save(@RequestBody OptionResponse.CreateDto createDto){
        OptionResponse.CreateDto savedOption = optionService.save(createDto);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(savedOption);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/options/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OptionResponse.UpdateDto updateDto){
        Option updateOption = optionService.update(id, updateDto);
        OptionResponse.UpdateDto updateDto1 = new OptionResponse.UpdateDto(updateOption);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(updateDto1);
        return ResponseEntity.ok(apiResult);
    }

    // 옵션 삭제
    @PostMapping("/options/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        optionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
