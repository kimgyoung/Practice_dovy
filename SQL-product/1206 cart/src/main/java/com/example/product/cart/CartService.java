package com.example.product.cart;

import com.example.product.error.exception.Exception400;
import com.example.product.error.exception.Exception404;
import com.example.product.error.exception.Exception500;
import com.example.product.option.Option;
import com.example.product.option.OptionRepositry;
import com.example.product.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private CartRepository cartRepository;
    private OptionRepositry optionRepositry;

    public CartResponse.FindAllDto findAll() {
        List<Cart> cartList = cartRepository.findAll();
        return new CartResponse.FindAllDto(cartList);

    }

    // 동일한 상품 예외처리
    @Transactional
    public void addCartList(List<CartRequest.SaveDto> saveDtos, User user) {
        // set 컨테이너 = 동일한 데이터를 묶어줌
        Set<Long> optionsId = new HashSet<>();

        for(CartRequest.SaveDto cart: saveDtos) {
            if (!optionsId.add(cart.getOptionId())) {
                throw new Exception400("이미 동일한 상품 옵션이 있습니다." + cart.getOptionId());
            }
        }

        //원래 선언 안되는 데 {} 쓰면 사용 가능
        List<Cart> cartList = saveDtos.stream().map(cartDto -> {
            Option option = optionRepositry.findById(cartDto.getOptionId()).orElseThrow(
                    () -> new Exception404("해당 상품 옵션을 찾을 수 없습니다." + cartDto.getOptionId()));
            return cartDto.toEntity(option, user);
        }).collect(Collectors.toList());

        cartList.forEach( cart -> {
            try {
                cartRepository.save(cart);
            }catch (Exception e){
                throw new Exception500("카트에 담던 중 오류가 발생 했습니다." + e.getMessage());
            }
        });

    }


    @Transactional                          // 응답, 결과 값이 아니라 요청사항이라 리퀘스트..
    public CartResponse.UpdateDto update(List<CartRequest.UpdateDto> requestDTO, User user) {

        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

        // List<CartResponse.UpdateDto> 리스트에 있는 카트 id만 받아옴(PK)
        List<Long> cartIds = cartList.stream().map(cart -> cart.getId()).collect(Collectors.toList());
        List<Long> requestIds = requestDTO.stream().map(cartDtoList-> cartDtoList.getCartId()).collect(Collectors.toList());

        if(cartIds.size() == 0){
            throw new Exception404("주문 가능한 상품이 없습니다.");
        }


        // distinct 동일한 거 제거 해 줌
        // 1 2 3 4 5 = 5개 (사이즈는 5)
        // 1 1 3 3 4 = 3개 (사이즈는 5)
        // 그래서 두 개 그 다르다는 건 동일한게 들어있다는 거니까

        if(requestIds.stream().distinct().count() != requestIds.size()){
            throw new Exception400("동일한 카트 아이디를 주문 할 수 없습니다. ");

        }

        // 리스트 돌면서 카트 아이디 비교
        for(Long requestId: requestIds){
            // 요청받은 id와 같지않다면, 포함하지 않았다면
            if(!cartIds.contains(requestId)){
                throw new Exception400("카트에 없는 상품은 주문할 수 없습니다." + requestId);
            }
        }

        for(CartRequest.UpdateDto updateDto : requestDTO){
            for(Cart cart: cartList){
                if(cart.getId() == updateDto.getCartId()){
                    cart.update(updateDto.getQuantity(), cart.getPrice() * cart.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDto(cartList);
    }
}
