package com.example.product.product;

import com.example.product.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    // RestController이기 때문에 에러 적어 줘야 함

    // 전체 상품 확인
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page){
        List<ProductResponse.FindAllDto> productDtos = productService.findAll(page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productDtos);
        return ResponseEntity.ok(apiResult);
    }

    // 개별 상품 확인
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        ProductResponse.FindByIdDto productDtos = productService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productDtos);
        return ResponseEntity.ok(apiResult);
    }

    // (정보 받은)상품 DB 저장
    @PostMapping("/products/save")
    public ResponseEntity<?> save (@RequestBody ProductResponse.CreateDto createDto){
        ProductResponse.CreateDto savedProduct = productService.save(createDto);
        return ResponseEntity.ok(savedProduct);
    }

    // 등록 된 상품 정보 수정, 변경
    // 상품이 있는지 확인 후 수정?
    @PostMapping("/products/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductResponse.UpdateDto updateDto){
        Product updatedProduct = productService.update(id, updateDto);
        ProductResponse.UpdateDto responseBody = new ProductResponse.UpdateDto(updatedProduct);
        return ResponseEntity.ok(responseBody);
    }

    // 등록 된 상품 삭제
    @PostMapping("/products/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}
