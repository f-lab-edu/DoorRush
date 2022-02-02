package com.flab.doorrush.domain.user.dao;

import com.flab.doorrush.domain.user.domain.Address;
import com.flab.doorrush.domain.user.domain.UserAddress;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddressMapper {

  List<UserAddress> selectUserAddressAll(Long userSeq);

  void insertAddress(Address address);

  void insertUserAddress(UserAddress userAddressMapping);

  boolean isExistsDefaultAddress(Long userSeq);

  void updateUserAddress(Long userSeq);

  int deleteAddress(Long addressSeq);

  boolean isExistsAddress(Long addressSeq);

  Optional<Address> selectAddressBySeq(Long addressSeq);
}
