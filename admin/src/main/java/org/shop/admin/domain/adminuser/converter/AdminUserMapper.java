package org.shop.admin.domain.adminuser.converter;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import org.shop.admin.domain.adminuser.controller.model.AdminUserRegisterRequest;
import org.shop.admin.domain.adminuser.controller.model.AdminUserResponse;
import org.shop.admin.domain.adminuser.model.AdminUser;
import org.shop.db.adminuser.AdminUserEntity;


@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS  // ✅ 자동 null 체크
)
public interface AdminUserMapper {
    AdminUserMapper INSTANCE = Mappers.getMapper(AdminUserMapper.class);


    AdminUser entityToDto(AdminUserEntity adminUserEntity);
    AdminUserEntity registerRequestToEntity(AdminUserRegisterRequest request);
    AdminUserResponse entityToResponse(AdminUserEntity adminUserEntity);

}
