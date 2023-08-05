package com.nguyenphitan.BeetechLogin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechLogin.domain.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
