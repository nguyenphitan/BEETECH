package com.nguyenphitan.BeetechAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechAPI.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByIdUser(Long idUser);
	List<Cart>  findByIdProduct(Long idProduct);
}
