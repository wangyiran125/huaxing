package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoritesRepository extends JpaRepository<Favorites, Long>{
	
	@Query("SELECT f FROM Favorites f WHERE f.goodsOwnerId = ?1")
	public Page<Favorites> findByGoodsOwnerId(Long goodsOwnerId, Pageable pageable);
}
