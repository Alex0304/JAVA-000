/*
package com.weekwork.converter;


import com.weekwork.entity.Address;
import com.weekwork.entity.Person;
import com.weekwork.entity.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source ="person.userName",target = "userName"),
            @Mapping(source ="person.phone",target = "phone"),
            @Mapping(source = "address.country",target = "country"),
            @Mapping(source = "address.citycode",target = "city"),
            @Mapping(source = "address.address",target = "address"),
    })
    UserDTO fromPersonDTO(Person person, Address address);
}
*/
