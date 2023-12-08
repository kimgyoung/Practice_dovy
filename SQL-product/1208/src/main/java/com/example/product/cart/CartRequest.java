package com.example.product.cart;

import com.example.product.option.Option;
import com.example.product.user.User;
import lombok.Getter;
import lombok.Setter;

// 요청
public class CartRequest {
    @Setter
    @Getter          // 저장해 달라는 요청
    public static class SaveDto{
        private Long optionId;
        private Long quantity;

        // 인증 정보 때문에 유저 정보는 항상 들어가 있을 수 밖에 없음
        // 카트 객체에 옵션과 유저 정보 함께 저장
        public Cart toEntity(Option option , User user){
            return Cart.builder()
                    .option(option)
                    .user(user)
                    .quantity(quantity)
                    .price(option.getPrice() * quantity)
                    .build();
        }
    }

    // 카트 아이디와 수량 업데이트
    @Setter
    @Getter
    public static class UpdateDto{
        private Long cartId;
        private Long quantity;
    }

}
