package com.acme.leads.auth.mapper;

import org.mapstruct.Mapper;

import java.util.List;

import com.acme.leads.auth.dto.UserDTO;
import com.acme.leads.auth.dto.UserDetailsDTO;
import com.acme.leads.auth.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDetailsDTO, Long> {
    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);

    List<UserDTO> userToUserDTOList(List<User> users);

    List<User> userDTOtoUserList(List<UserDTO> userDTOList);
}