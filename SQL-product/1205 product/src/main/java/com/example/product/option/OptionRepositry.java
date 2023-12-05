package com.example.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OptionRepositry extends JpaRepository<Option, Long> {
    // 이름이 쿼리문이 돼서 findById 이름이 겹치기 때문에 findByProductId로 변경
    List<Option> findByProductId(Long id);

}
