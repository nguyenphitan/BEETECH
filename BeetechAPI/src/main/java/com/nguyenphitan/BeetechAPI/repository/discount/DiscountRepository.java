package com.nguyenphitan.BeetechAPI.repository.discount;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
