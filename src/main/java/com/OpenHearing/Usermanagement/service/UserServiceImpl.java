package com.OpenHearing.Usermanagement.service;

import com.OpenHearing.Usermanagement.dto.UserDTO;
import com.OpenHearing.Usermanagement.entity.User;
import com.OpenHearing.Usermanagement.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        if (userRepository.existsByAadhaar(userDTO.getAadhaar())) {
            throw new RuntimeException("Aadhaar already registered");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setIsActive(true);
        User savedUser = userRepository.save(user);

        UserDTO response = new UserDTO();
        BeanUtils.copyProperties(savedUser, response);
        return response;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDTO.getName());
        user.setPrimaryMobile(userDTO.getPrimaryMobile());
        user.setSecondaryMobile(userDTO.getSecondaryMobile());
        user.setCurrentAddress(userDTO.getCurrentAddress());
        user.setPermanentAddress(userDTO.getPermanentAddress());
        user.setPlaceOfBirth(userDTO.getPlaceOfBirth());
        
        User updatedUser = userRepository.save(user);
        UserDTO response = new UserDTO();
        BeanUtils.copyProperties(updatedUser, response);
        return response;
    }
    
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Soft Delete: Just mark as inactive
        user.setIsActive(false); 
        userRepository.save(user);
    }

    // UPDATED: Now accepts 'keyword' for server-side search
    @Override
    public Page<UserDTO> getAllUsers(int page, int size, String keyword) {
        // Sort by ID descending (newest first)
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> users;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search Mode: Find by name OR email
            users = userRepository.searchUsers(keyword, pageRequest);
        } else {
            // Normal Mode: Get all active usersl
            users = userRepository.findByIsActiveTrue(pageRequest);
        }

        return users.map(u -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(u, dto);
            return dto;
        });
    }
}
