package org.shop.admin.resolver;

import lombok.RequiredArgsConstructor;
import org.shop.admin.common.annotation.UserSession;
import org.shop.admin.domain.adminUser.converter.AdminUserMapper;
import org.shop.admin.domain.adminUser.model.AdminUser;
import org.shop.admin.domain.adminUser.service.AdminUserService;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final AdminUserService adminUserService;
    private final AdminUserMapper adminUserMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크 , 어노테이션 체크

        //1. 어노테이션이 있는지 체크
        var annotation = parameter.hasParameterAnnotation(UserSession.class);

        //2. 파라미터의 타입 체크
        var parameterType = parameter.getParameterType().equals(AdminUser.class);

        return (annotation && parameterType);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // support parameter 에서 true 반환시 여기 실행

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        var adminUserEntity = adminUserService.getAdminUserWithThrow(UUID.fromString(userId.toString()));

        // 사용자 정보 셋팅
        return adminUserMapper.entityToDto(adminUserEntity);
    }

}
