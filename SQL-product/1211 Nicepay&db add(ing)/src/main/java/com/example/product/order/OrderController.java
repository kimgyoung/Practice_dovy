package com.example.product.order;

import com.example.product.security.CustomUserDetails;
import com.example.product.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    // 주문 상세 내역
    // 결제 페이지

    // 주문 정보 저장 후 반환
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        // 인증된 사용자라면 주문 정보 저장
        OrderResponse.FindByIdDto findByIdDto = orderService.save(customUserDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(findByIdDto));
    }

    // 주문이 완료 되면 주문 결과 확인
    @GetMapping("/orders/{id}")
    // 특정 id의 주문 정보 찾아서 저장 후 반환
    public ResponseEntity<?> findById(@PathVariable Long id){
        OrderResponse.FindByIdDto findByIdDto = orderService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findByIdDto);
        return ResponseEntity.ok(apiResult);
    }

    // 주문 삭제
    @PostMapping("/orders/clear")
    public ResponseEntity<?> clear(){
        orderService.clear();
        ApiUtils.ApiResult<?> apiResult =ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }


}
