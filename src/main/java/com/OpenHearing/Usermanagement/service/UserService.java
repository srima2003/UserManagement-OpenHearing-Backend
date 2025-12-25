package com.OpenHearing.Usermanagement.service;

import com.OpenHearing.Usermanagement.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    Page<UserDTO> getAllUsers(int page, int size, String keyword);
 // Add this line inside the interface
    void deleteUser(Long id);
}
