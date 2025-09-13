package org.shop.api.domain.user.converter;


import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import org.shop.api.domain.user.controller.model.UserRegisterRequest;
import org.shop.api.domain.user.controller.model.UserResponse;
import org.shop.db.user.UserEntity;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS  // ✅ 자동 null 체크
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserEntity registerRequestToEntity(UserRegisterRequest request);
    UserResponse entityToResponse(UserEntity userEntity);

}
