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
    private final CartRepository cartRepository;
    private final OptionRepositry optionRepositry;

    // 카트 리스트 다 찾아와서 FindAllDto로 반환
    public CartResponse.FindAllDto findAll() {
        List<Cart> cartList = cartRepository.findAll();
        return new CartResponse.FindAllDto(cartList);
    }

    // 사용자가 요청한 상품들(saveDtos)을 DB에 저장할 수 있는 형태(Cart 객체)로 변환하고, 이를 리스트로 수집하는 역할
    // 동일한 상품 예외 처리
    @Transactional          // 카트에 추가 하려는 상품들 정보, 유저 정보
    public void addCartList(List<CartRequest.SaveDto> saveDtos, User user) {
        // set 컨테이너 = 동일한 데이터를 묶어줌
        // 동일한 상품 옵션을 장바구니에 중복으로 담는 것을 방지
        Set<Long> optionsId = new HashSet<>();

        // 추가 하려는 상품 정보들 cart에
        for(CartRequest.SaveDto cart: saveDtos) {
            // cart.getOptionId()가 이미 optionsId Set에 존재하여 추가 되지 않은 경우
            if (!optionsId.add(cart.getOptionId())) {
                throw new Exception400("이미 동일한 상품 옵션이 있습니다." + cart.getOptionId());
            }
        }

        // 장바구니에 추가 하려는 상품들의 정보(cartDto)를 Cart 객체로 변환하고, 이를 리스트로 수집
        List<Cart> cartList = saveDtos.stream().map(cartDto -> {
            // cartDto에서 상품 옵션 ID를 가져와, 해당 ID로 상품 옵션을 찾기
            Option option = optionRepositry.findById(cartDto.getOptionId()).orElseThrow(
                    () -> new Exception404("해당 상품 옵션을 찾을 수 없습니다." + cartDto.getOptionId()));
            // cartDto를 Cart 객체로 변환. toEntity 메소드는 상품 옵션과 사용자 정보를 인자로 받아 Cart 객체를 생성
            return cartDto.toEntity(option, user);
        }).collect(Collectors.toList()); //saveDtos 리스트가 Cart 객체의 리스트인 cartList로 변환 됨

        // 장바구니에 담긴 각 상품(Cart 객체)
        // DB로 데이터가 넘어 가는 과정 (톰캣 -> (MySQL) DB로 갈 때의 문제)
        cartList.forEach( cart -> {
            try {
                cartRepository.save(cart);
            }catch (Exception e){
                throw new Exception500("카트에 담던 중 오류가 발생 했습니다." + e.getMessage());
            }
        });
    }

    //사용자가 요청한 상품들의 수량을 업데이트 후 새로운 장바구니 정보를 반환
    @Transactional                          // 응답, 결과 값이 아니라 요청사항이라 리퀘스트..
    public CartResponse.UpdateDto update(List<CartRequest.UpdateDto> requestDTO, User user) {

        // 유저 아이디 값으로 모든 카트 찾기
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

        // List<CartResponse.UpdateDto> 리스트에 있는 카트 id만 받아옴(PK)
        // 옵션 정보를 알 수 있으니까 (옵션이 카트 pk를 들고 있지 == 옵션 입장에서의 FK)

        // 카트에 담긴 상품들의 ID를 리스트로
        List<Long> cartIds = cartList.stream().map(cart -> cart.getId()).collect(Collectors.toList());
        // 카트 Id랑 수량 들고 와서  pk를 골라 내라
        // 사용자가 요청한 상품들의 ID를 리스트로
        List<Long> requestIds = requestDTO.stream().map(cartDtoList-> cartDtoList.getCartId()).collect(Collectors.toList());

        // * 예외 처리

        // 리스트 사이즈가 0 인거면 ex) 결제 페이지로 넘어 갔지만 결제할 상품이 없는 것
        if(cartIds.size() == 0){
            throw new Exception404("주문 가능한 상품이 없습니다.");
        }

        // distinct 동일한 거 제거 해 줌
        // 1 2 3 4 5 = 5개 (사이즈는 5)
        // 1 1 3 3 4 = 3개 (사이즈는 5)
        // 그래서 두 개 그 다르다는 건 동일한 게 들어 있다는 거니까
        if(requestIds.stream().distinct().count() != requestIds.size()){
            throw new Exception400("동일한 카트 아이디를 주문 할 수 없습니다. ");
        }

        // 리스트 돌면서 카트 아이디 비교
        for(Long requestId: requestIds){
            // 요청 받은 (상품 카트) id와 같지 않다면, 포함 하지 않았다면
            if(!cartIds.contains(requestId)){
                throw new Exception400("카트에 없는 상품은 주문할 수 없습니다." + requestId);
            }
        }

        // 모든 카트 리스트를 돌면서 동일한 것만
        // 장바구니에 있는 상품의 ID와 요청한 상품의 ID가 같다면, 해당 상품의 수량을 업데이트
        for(CartRequest.UpdateDto updateDto : requestDTO){
            for(Cart cart: cartList){
                if(cart.getId() == updateDto.getCartId()){
                    cart.update(updateDto.getQuantity(), cart.getPrice() * cart.getQuantity());
                }
            }
        }
        // 업데이트 된 카트리스트 반환
        return new CartResponse.UpdateDto(cartList);
    }
}
