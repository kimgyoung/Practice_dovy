package com.example.product.order;

import com.example.product.cart.Cart;
import com.example.product.cart.CartRepository;
import com.example.product.error.exception.Exception404;
import com.example.product.error.exception.Exception500;
import com.example.product.order.item.Item;
import com.example.product.order.item.ItemRepository;
import com.example.product.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    // 사용자의 장바구니에 있는 각 아이템을 주문 아이템으로 변환하고, 이를 주문 정보에 연결
    // 사용자의 주문 정보 저장
    @Transactional
    public OrderResponse.FindByIdDto save(User user) {
        // user id 를 가지고 있는 카트들은 다 검색
        // 장바구니 조회
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());
        if(cartList.size() == 0){
            throw new Exception404("장바구니에 상품 내역이 존재 하지 않습니다.");
        }
        // 주문 정보 생성 후 저장 (order객체 생성)
        Order order = orderRepository.save(Order.builder().user(user).build());

        // 주문에 포함될 아이템들 저장할 리스트 생성
        List<Item> itemList = new ArrayList<>();
        // 카트 리스트의 정보를 아이템으로 저장 후 아이템 리스트로 반환
        // 사용자의 cartList에 있는 각각의 아이템(Cart)에 대해 반복
        for(Cart cart : cartList){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getOption().getPrice() * cart.getQuantity())
                    .productName(cart.getOption().getProduct().getProductName())
                    .build();
            // 카트의 아이템 정보로 새로운 Item 객체를 생성 후 itemList에 추가
            itemList.add(item);
        }

        // 여기 있는 정보로 결제를 할 건 데, 결제를 한건 아니지만 결제를 시도 하는 중?
        // 아이템 리스트 모두 저장
        try {
            itemRepository.saveAll(itemList);
        }catch (Exception e){
            // 저장 하고 있는데 문제 생기면 무조건 500 error
            throw new Exception500("결제 실패");
        }
        // 주문 정보와 아이템 리스트를 가진 새 dto 생성(반환)
        return new OrderResponse.FindByIdDto(order, itemList);
    }

    // 주문 id로 주문 내역(정보) 찾기
    public OrderResponse.FindByIdDto findById(Long id) {
        Order order= orderRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 주문 내역을 찾을 수 없습니다.: " + id)
        );

        // 주문 id로 아이템 리스트 전체 내역을 조회
        List <Item> itemList = itemRepository.findByOrderId(id);
        // 주문 정보와 아이템 리스트를 가진 새 객체 생성
        return new OrderResponse.FindByIdDto(order, itemList);
    }

    // 주문 내역 삭제
    @Transactional
    public void clear(){
        try {
            itemRepository.deleteAll();
        }catch (Exception e){
            throw new Exception500("아이템 삭제 오류: " + e.getMessage());
        }
    }
}

