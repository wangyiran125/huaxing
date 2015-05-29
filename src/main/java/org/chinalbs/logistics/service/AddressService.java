package org.chinalbs.logistics.service;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Address;
import org.chinalbs.logistics.vo.AddressInfo;

public interface AddressService {

	public ListSlice<Address> findListSlice(int from, int max);
	public Address create(AddressInfo info);
	public Address findOneByAddressId(Long AddressId);
	public Address update(Long addressId, AddressInfo info);
	public Address updateAddressFlag(Long addressId, int flag);
	public void delete(Long AddressId);
	public List<Address> findByUserId(long userId);
	public Address findByUserIdAndFlag(Long userId, int flag);
}
