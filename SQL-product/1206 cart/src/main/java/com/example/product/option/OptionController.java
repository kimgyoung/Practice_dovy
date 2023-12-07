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
    public ResponseEntity<?> findById(@PathVariable Long id){
        List<OptionResponse.FindByProductIdDto> optionResponses =
                optionService.findByProductId(id);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    // 옵션 전체 상품 검색
    @GetMapping("/options")
    public ResponseEntity<?> findAll(){
        List<OptionResponse.FindAllDto> optionResponses =
                optionService.findAll();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    // product id를 검색 해서 상품을 가져 올 수 있음
    // 상품 안에 옵션이 있는 거니까
    // 어떤 상품 인지 확인 할 수 있다.?

    @PostMapping("/options/save")
    public ResponseEntity<?> save(@RequestBody OptionResponse.CreateDto createDto){
        OptionResponse.CreateDto savedOption = optionService.save(createDto);
        // 제품 확인용

        return ResponseEntity.ok(savedOption);
    }

    @PostMapping("/options/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OptionResponse.UpdateDto updateDto){
        Option updateOption = optionService.update(id, updateDto);
        OptionResponse.UpdateDto updateDto1 = new OptionResponse.UpdateDto(updateOption);
        return ResponseEntity.ok(updateDto1);
    }

    @PostMapping("/options/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        optionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
