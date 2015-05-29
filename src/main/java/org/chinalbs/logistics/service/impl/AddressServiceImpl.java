package org.chinalbs.logistics.service.impl;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Address;
import org.chinalbs.logistics.repository.AddressRepository;
import org.chinalbs.logistics.service.AddressService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.AddressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public ListSlice<Address> findListSlice(int from, int max) {
		Page<Address> page = addressRepository.findAll(new SimplePageable(from, max));
		return new ListSlice<Address>(page.getTotalElements(), page.getContent());
	}

	@Override
	public Address create(AddressInfo info) {
		Address address = makeAddress(new Address(), info);
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		address.setUserId(userId);
		address = addressRepository.save(address);
		return address;
	}

	@Override
	public Address findOneByAddressId(Long addressId) {
		return addressRepository.findOne(addressId);
	}

	@Override
	public Address update(Long addressId, AddressInfo info) {
		Address address = addressRepository.findOne(addressId);
		if (address != null) {
			return addressRepository.save(makeAddress(address, info));
		}
		return null;
	}

	@Override
	public void delete(Long AddressId) {
		addressRepository.delete(AddressId);
	}

	
	private Address makeAddress(Address address, AddressInfo info){
		address.setAddress(info.getAddress());
		address.setCityCode(info.getCityCode());
		address.setProvinceCode(info.getProvinceCode());
		return address;
	}

	@Override
	public List<Address> findByUserId(long userId) {
		return addressRepository.findByUserId(userId);
	}

	@Override
	public Address updateAddressFlag(Long addressId, int flag) {
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		Address addresse_old = addressRepository.findByUserIdAndFlag(userId, flag);
		if (addresse_old != null) {
			addresse_old.setFlag(0);
			addressRepository.save(addresse_old);
		}
		Address address_new = addressRepository.findOne(addressId);
		address_new.setFlag(flag);
		return addressRepository.save(address_new);
	}

	@Override
	public Address findByUserIdAndFlag(Long userId, int flag) {
		return addressRepository.findByUserIdAndFlag(userId, flag);
	}
	
}
