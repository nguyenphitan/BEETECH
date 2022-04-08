package com.nguyenphitan.BeetechAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenphitan.BeetechAPI.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByIdUser(Long idUser);
	List<Cart>  findByIdProduct(Long idProduct);
}
