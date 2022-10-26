package org.hbrs.project.wram.control.reviewer;

import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "user", source = "user")
    ReviewerDTO toDTO(Reviewer reviewer);
}