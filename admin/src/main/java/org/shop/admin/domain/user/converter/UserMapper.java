package org.shop.admin.domain.user.converter;


import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import org.shop.admin.domain.user.controller.model.UserRegisterRequest;
import org.shop.admin.domain.user.controller.model.UserResponse;
import org.shop.admin.domain.user.model.User;
import org.shop.db.user.UserEntity;


@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS  // ✅ 자동 null 체크
 )
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    User entityToDto(UserEntity userEntity);
    UserEntity registerRequestToEntity(UserRegisterRequest request);
    UserResponse entityToResponse(UserEntity userEntity);

}
