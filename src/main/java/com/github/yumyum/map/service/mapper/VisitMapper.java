package com.github.yumyum.map.service.mapper;

import com.github.yumyum.map.repository.entity.VisitEntity;
import com.github.yumyum.map.web.dto.visited.VisitBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VisitMapper {
    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);
    @Mapping(target="memberEntity.memberId", source="visitBody.memberId")
    @Mapping(target="restaurantEntity.rstrId", source="visitBody.rstrId")
    VisitEntity idAndVisitBodyToVisitEntity(Integer id, VisitBody visitBody);
}
