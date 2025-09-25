package org.shop.common.api.page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS  // ✅ 자동 null 체크
)
public interface PageMapper {
    PageMapper INSTANCE= Mappers.getMapper(PageMapper.class);


    @Mapping(target = "currentPage", source = "number")  // 0-based → 1-based
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "hasNext", expression = "java(page.hasNext())")
    @Mapping(target = "hasPrevious", expression = "java(page.hasPrevious())")
    PageInfo toPageInfo(Page<?> page);


}
