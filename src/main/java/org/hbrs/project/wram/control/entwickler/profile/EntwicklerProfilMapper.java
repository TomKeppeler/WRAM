/**
 * @outhor Salah & Tom
 * @vision 1.0
 * @Zuletzt bearbeitet: 17.11.22 by Salah
 *
 */
package org.hbrs.project.wram.control.entwickler.profile;

import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfil;
import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfilDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntwicklerProfilMapper {
    /**
     *
     * @param entwicklerProfile wir zu einem EntwicklerProfilDTO gemappt
     * @return EntwicklerProfilDTO
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "entwickler", source = "entwickler")
    @Mapping(target = "kundenprojekt", source = "kundenprojekt")
    EntwicklerProfilDTO toDTO(EntwicklerProfil entwicklerProfile);
}
