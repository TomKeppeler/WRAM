/**
 * @outhor Salah & Tom
 * @vision 1.0
 * @Zuletzt bearbeitet: 17.11.22 by Salah
 */
package org.hbrs.project.wram.control.user;

import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     *
     * @param user wird zu einem UserDTO gemappt
     * @return UserDTO
     */
    @Mappings(value = {
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "verified", source = "verified")
    })
    UserDTO toDTO(User user);
}
