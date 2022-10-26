package org.hbrs.project.wram.control.manager;

import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "user", source = "user")
    ManagerDTO toDTO(Manager manager);
}
