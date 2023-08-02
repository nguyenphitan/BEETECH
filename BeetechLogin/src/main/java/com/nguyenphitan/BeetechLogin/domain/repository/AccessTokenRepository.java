package com.nguyenphitan.BeetechLogin.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechLogin.domain.entity.AccessToken;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long>{

	Optional<AccessToken> findByToken(String token);
	
	@Modifying
	@Query(value = "DELETE FROM access_token WHERE user_id=?1", nativeQuery = true)
    int deleteByUserId(Long userId);
}
