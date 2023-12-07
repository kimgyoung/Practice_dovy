package com.example.product.cart;

import com.example.product.option.Option;
import com.example.product.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

// 응답
public class CartResponse {

    @Setter
    @Getter             // 업데이트에 대한 응답
    public static class UpdateDto{
        List<CartDto> cartDtoList;
        private Long totalPrice;

        public UpdateDto(List<Cart> cartDtoList) {
            this.cartDtoList = cartDtoList.stream()
                    .map(CartDto::new).collect(Collectors.toList());

            this.totalPrice = totalPrice;
        }

        public class CartDto{

            private Long cartId;

            private Long optionId;

            private String optionName;

            private Long quantity;

            private Long price;

            public CartDto(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }


    // 서로가 서로의 정보를 가지기 때문에 복잡
    // 데이터를 서로 확인 할 수 있게 끔 보관
    @Setter
    @Getter
    public static class FindAllDto{
        List<ProductDto> products;
        private Long totalPrice;

        // 카트로 받아서
        public FindAllDto(List<Cart> cartList){
            // product 형태로 반환 : 옵션의 제품 정보 들고와서 dto로 반환
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDto(cartList, product)).collect(Collectors.toList());

            this.totalPrice = cartList.stream() // 전체 수량별 금액, 합을 반환
                    .mapToLong(cart -> cart.getOption().getPrice() * cart.getQuantity())
                    .sum();

        }
        // 외부에서 안 쓸거라 FindAllDto 안에 만듦
        @Setter
        @Getter
        public static class ProductDto{
            private Long id;
            private String productName;
            List<CartDto> cartDtos;

            public ProductDto( List<Cart> cartList,Product product){
                this.id = product.getId();
                this.productName = product.getProductName();
                this.cartDtos = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDto::new).collect(Collectors.toList());
            }

            public class CartDto{
                private Long id;
                private OptionDto optionDto;
                private Long price;

                public CartDto(Cart cart) {
                    this.id = cart.getId();
                    this.optionDto = new OptionDto(cart.getOption());
                    this.price = cart.getPrice();
                }

                public class OptionDto{
                    private Long id;
                    private String optionName;
                    private Long price;

                    public OptionDto(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }
        }
    }
}
