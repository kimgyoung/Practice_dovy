package com.example.product.cart;

import com.example.product.security.CustomUserDetails;
import com.example.product.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartService cartService;

    // 카트에 상품 추가
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList (
            // (인증 된)사용자가 장바구니에 추가 하려는 상품들의 정보를 HTTP 요청 본문에서 가져와서 유효성 검사 수행 후
            // 카트에 상품을 추가
            @RequestBody @Valid List<CartRequest.SaveDto> saveDtos, // 400 error
            @AuthenticationPrincipal CustomUserDetails customUserDetails // 유저 정보를 받아 와서 (인증 상태 곧 바로 확인 가능)
            ,Error error) {

         cartService.addCartList(saveDtos, customUserDetails.getUser());
         ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // 카트 전체 상품 확인
    @GetMapping("/carts")           // 인증 받은 것들만 접근 가능 - 매개 변수 customUserDetails로 전달 - 인증 X시 401에러
    public ResponseEntity<?> carts(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        CartResponse.FindAllDto findAllDto = cartService.findAll();
        // 담긴 카트 정보 뷰로 넘김
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findAllDto);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/carts/update")    // 카드에 담겨 있는 상품들의 처리에 관한 것
    // (인증 된)사용자가 변경하려는 카트 리스트를 받아서 유효성 검사 후 넘겨 줌
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDto> requestDTO,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    Error error) {
        // 변경된 카트 정보를 받아서 저장
        CartResponse.UpdateDto updateDTO = cartService.update(requestDTO, customUserDetails.getUser());

        // 변경된 정보를 뷰로 넘겨줌
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(updateDTO);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/carts/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails ){
        cartService.delete(id, customUserDetails.getUser());

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/carts/deleteAll")
    public ResponseEntity<?> deleteAll(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        cartService.deleteAll(customUserDetails.getUser());

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

}
