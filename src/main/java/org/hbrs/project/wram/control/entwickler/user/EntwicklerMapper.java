/**
 * @outhor Salah & Tom
 * @vision 1.0
 * @Zuletzt bearbeitet: 17.11.22 by Salah
 *
 */
package org.hbrs.project.wram.control.entwickler.user;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntwicklerMapper {

    /**
     *
     * @param entwickler wir zu einem EntwicklerDTO gemappt
     * @return EntwicklerDTO
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "user", source = "user")
    EntwicklerDTO toDTO(Entwickler entwickler);
}
