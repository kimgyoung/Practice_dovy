package com.example.product.cart;

import com.example.product.security.CustomUserDetails;
import com.example.product.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {
    private CartService cartService;

    // 카트에 상품 추가
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList (
            @RequestBody @Valid List<CartRequest.SaveDto> saveDtos,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ,Error error) {
         cartService.addCartList(saveDtos, customUserDetails.getUser());

         ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // 카트 전체 상품 확인
    @GetMapping("/carts")           // 인증 받은 것들만 접근 가능 - 매개 변수 customUserDetails로 전달 - 인증 X시 401에러
    public ResponseEntity<?> carts(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        CartResponse.FindAllDto findAllDto = cartService.findAll();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findAllDto);
        return ResponseEntity.ok(apiResult);
    }

    // 서비스에서 받아 온 값
    @GetMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDto> requestDTO,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    Error error) {

        CartResponse.UpdateDto updateDTO = cartService.update(requestDTO, customUserDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(updateDTO);
        return ResponseEntity.ok(apiResult);
    }
}
