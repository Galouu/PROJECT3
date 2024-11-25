package com.rental_app.service;

import com.rental_app.dto.UserDTO;
import com.rental_app.model.User;
import com.rental_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO createUser(String email, String name, String rawPassword) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return convertToDTO(userRepository.save(user));
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::convertToDTO);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private UserDTO convertToDTO(User user) {
      return new UserDTO(
              user.getId(),
              user.getEmail(),
              user.getName(),
              user.getPassword(),
              user.getCreatedAt(),
              user.getUpdatedAt()
      );
  }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        return user;
    }
}
