package org.chinalbs.logistics.service;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Favorites;
import org.chinalbs.logistics.vo.AddFavoritesInfo;
import org.chinalbs.logistics.vo.FavoritesInfo;

public interface FavoritesService {

	public ListSlice<FavoritesInfo> findListSlice(Long goodsOwnerId, int from, int max);
	public Favorites create(AddFavoritesInfo info);
	public void delete(Long favoritesId);
	public FavoritesInfo findOne(Long favoritesId);
}
