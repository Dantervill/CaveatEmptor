package com.senla.internship.caveatemptor.service;

import com.senla.internship.caveatemptor.dto.UserDto;
import com.senla.internship.caveatemptor.exceptions.UserNotFoundException;
import com.senla.internship.caveatemptor.model.User;
import com.senla.internship.caveatemptor.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(ModelMapper mapper,
                       UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public UserDto getById(Long id) {
        String msg = String.format("User with id %d not found", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(msg));
        return mapper.map(user, UserDto.class);
    }

    public void saveUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        userRepository.save(user);
    }
}
