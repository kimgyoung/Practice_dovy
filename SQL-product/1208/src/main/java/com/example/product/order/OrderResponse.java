package com.example.product.order;

import com.example.product.order.item.Item;
import com.example.product.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    // 초기화 -> 생성 과정

    @Setter
    @Getter
    // 주문 ID로 조회한 결과를 담는 DTO
    public static class FindByIdDto{
        private Long id;
        private List<ProductDto> productDtos;
        private Long totalPrice;

        // 각 아이템에서 제품(Product)을 가져온 후 중복 제품을 제거. 각 제품에 대해 ProductDto를 생성 후 리스트로 만듦
        // 각 아이템의 총 가격을 계산, 모두 더하여 주문의 총 가격을 계산
        public FindByIdDto(Order order, List<Item> itemList) {
            this.id = order.getId();
            this.productDtos = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDto(itemList, product)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToLong(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }

        @Setter
        @Getter
        // 주문에 포함된 각 제품을 나타내는 DTO
        public class ProductDto{
            private String productName;
            private List<ItemDto> itemDtos;

            // 특정 제품에 대한 모든 아이템 정보를 한 번에 가져옴
            public ProductDto(List<Item> items, Product product) {
                this.productName = product.getProductName();
                this.itemDtos = items.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDto::new)
                        .collect(Collectors.toList());
            }

            @Setter
            @Getter
            // 주문에 포함된 각 아이템을 나타내는 DTO
            public class ItemDto{
                private Long id;
                private String optionName;
                private Long quantity;
                private Long price;

                public ItemDto(Item item) {
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }
}
