package org.chinalbs.logistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Favorites;
import org.chinalbs.logistics.domain.TruckOwner;
import org.chinalbs.logistics.repository.FavoritesRepository;
import org.chinalbs.logistics.repository.TruckOwnerRepository;
import org.chinalbs.logistics.service.FavoritesService;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.AddFavoritesInfo;
import org.chinalbs.logistics.vo.FavoritesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FavoritesServiceImpl implements FavoritesService {

	@Autowired
	private FavoritesRepository favoritesRepository;
	
	@Autowired TruckOwnerRepository truckOwnerRepository;
	
	@Override
	public ListSlice<FavoritesInfo> findListSlice(Long goodsOwnerId, int from, int max) {
		Page<Favorites> page = favoritesRepository.findByGoodsOwnerId(goodsOwnerId, new SimplePageable(from, max));
		List<FavoritesInfo> favoritesInfos = new ArrayList<FavoritesInfo>();
		TruckOwner truckOwner = null;
		if (page != null && page.getSize() > 0) {
			for (Favorites favorites : page) {
				truckOwner = truckOwnerRepository.findOne(favorites.getTruckOwnerId());
				favoritesInfos.add(makeFavoritesInfo(favorites.getId(), truckOwner));	
			}
			return new ListSlice<FavoritesInfo>(page.getTotalElements(), favoritesInfos);
		}
		return null;
	}

	@Override
	public Favorites create(AddFavoritesInfo info) {
		Favorites favorites = new Favorites();
		favorites.setGoodsOwnerId(info.getGoodsOwnerId());
		favorites.setTruckOwnerId(info.getTruckOwnerId());
		return favoritesRepository.save(favorites);
	}

	@Override
	public FavoritesInfo findOne(Long favoritesId) {
		Favorites favorites = favoritesRepository.findOne(favoritesId);
		if (favorites != null) {
			TruckOwner truckOwner = truckOwnerRepository.findOne(favorites.getTruckOwnerId());
			return makeFavoritesInfo(favoritesId, truckOwner);
		}
		return null;
	}

	@Override
	public void delete(Long favoritesId) {
		favoritesRepository.delete(favoritesId);
	}
	
	private FavoritesInfo makeFavoritesInfo(Long favoritesId, TruckOwner truckOwner){
		FavoritesInfo info = new FavoritesInfo();
		info.setFavoritesId(favoritesId);
		info.setTruckOwnerId(truckOwner.getId());
		info.setName(truckOwner.getName());
		info.setIdCard(truckOwner.getIdCard());
		info.setMobile(truckOwner.getMobile());
		info.setPhone(truckOwner.getPhone());
		info.setQq(truckOwner.getQq());
		info.setEmail(truckOwner.getQq());
		info.setAvatar(truckOwner.getAvatar());
		info.setTruckLabel(truckOwner.getTruckLabel());
		info.setVipLevel(truckOwner.getVipLevel());
		return info;
	}

}
