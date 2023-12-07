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

        public Cart toEntity(Option option , User user){
            return Cart.builder()
                    .option(option)
                    .user(user)
                    .quantity(quantity)
                    .price(option.getPrice() * quantity)
                    .build();
        }
    }

    @Setter
    @Getter
    public static class UpdateDto{
        private Long cartId;
        private Long quantity;

    }


}
