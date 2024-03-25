package com.github.yumyum.map.service.mapper;

import com.github.yumyum.map.repository.entity.InterestEntity;
import com.github.yumyum.map.web.dto.interested.InterestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InterestMapper {
    InterestMapper INSTANCE = Mappers.getMapper(InterestMapper.class);
    @Mapping(target="memberEntity.memberId", source="interestBody.memberId")
    @Mapping(target="restaurantEntity.rstrId", source="interestBody.rstrId")
    InterestEntity idAndInterestBodyToInterestEntity(Integer id, InterestBody interestBody);

//    InterestDTO interestEntityToInterestDTO(InterestEntity interestEntity);

}
