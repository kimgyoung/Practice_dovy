package com.example.product.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository <Item, Long>{

    // item에 오더가 없으면 에러 남
    List<Item> findByOrderId(Long id);
}
