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
            // 카트의 상품 옵션, 수량등 정보 저장
            // (인증 된)사용자가 장바구니에 추가 하려는 상품들의 정보를 HTTP 요청 본문에서 가져와서 유효성 검사 수행 후
            // 카트에 상품을 추가 하는 요청을 처리
            @RequestBody @Valid List<CartRequest.SaveDto> saveDtos, // 400 error
            @AuthenticationPrincipal CustomUserDetails customUserDetails // 유저 정보를 받아 와서 (인증 상태 곧 바로 확인 가능)
            ,Error error) {

        // 추가 하려는 상품 정보와 유저 정보를 받아서 카트에 추가
                                            // 유저 정보를 넘겨 줌
         cartService.addCartList(saveDtos, customUserDetails.getUser());

         // 저장 성공
         ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }
    // CustomUserDetails 가 없으면 확인 조차 할 수 없음
    // CustomUserDetails 유저 정보를 가져 와야 유저 정보를 받아올 수 있음, 쓸 수 있음


    // 카트 전체 상품 확인
    @GetMapping("/carts")           // 인증 받은 것들만 접근 가능 - 매개 변수 customUserDetails로 전달 - 인증 X시 401에러
    public ResponseEntity<?> carts(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        // 위의 정보가 틀리다면 밑의 함수들은 실행 조차 X 확인 하기 위해 System 출력 아무거나 해보는 것도 방법
        // 옵션, 제품 정보 담긴 카트 정보 모두 찾아서 값 줌
        CartResponse.FindAllDto findAllDto = cartService.findAll();

        // 담긴 카트 정보 뷰로 넘김
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findAllDto);
        return ResponseEntity.ok(apiResult);
    }

    // 서비스에서 받아 온 값
    // 카트 업데이트
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
}
