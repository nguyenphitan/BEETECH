package com.nguyenphitan.BeetechAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenphitan.BeetechAPI.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
