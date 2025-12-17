package com.OpenHearing.Usermanagement.service;

import com.OpenHearing.Usermanagement.dto.UserDTO;
import com.OpenHearing.Usermanagement.entity.User;
import com.OpenHearing.Usermanagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser_Success() {
        UserDTO inputDto = new UserDTO();
        inputDto.setName("John Doe");
        inputDto.setEmail("john@test.com");
        inputDto.setPrimaryMobile("9876543210");
        inputDto.setAadhaar("123412341234");
        inputDto.setPan("ABCDE1234F");

        User mockSavedUser = new User();
        mockSavedUser.setId(1L);
        mockSavedUser.setName("John Doe");
        mockSavedUser.setEmail("john@test.com");
        mockSavedUser.setIsActive(true);

        when(userRepository.existsByEmail(inputDto.getEmail())).thenReturn(false);
        when(userRepository.existsByAadhaar(inputDto.getAadhaar())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockSavedUser);

        UserDTO result = userService.createUser(inputDto);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }
}