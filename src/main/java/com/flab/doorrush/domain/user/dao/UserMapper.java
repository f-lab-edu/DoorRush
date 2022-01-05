package com.flab.doorrush.domain.user.dao;

import com.flab.doorrush.domain.user.domain.Address;
import com.flab.doorrush.domain.user.domain.User;
import com.flab.doorrush.domain.user.domain.UserAddressMapping;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  void insertUser(User user);

  Optional<User> selectUserById(String loginId);

  Optional<User> selectUserByUserSeq(Long userSeq);

  int updatePassword(User user);

  List<Address> selectUserAddressAll(Long userSeq);

  void insertAddress(Address address);

  void insertUserAddressMapping(UserAddressMapping userAddressMapping);

  boolean isExistsDefaultAddress(Long userSeq);

  void updateUserAddressMapping(Long userSeq);

  int deleteAddress(Long addressSeq);

  boolean selectAddressBySeq(Long addressSeq);
}

