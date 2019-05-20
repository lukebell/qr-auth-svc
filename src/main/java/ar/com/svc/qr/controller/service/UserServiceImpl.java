package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.controller.constant.UserRoles;
import ar.com.svc.qr.controller.dto.UserDTO;
import ar.com.svc.qr.controller.validator.Validator;
import ar.com.svc.qr.exception.ResourceAlreadyExists;
import ar.com.svc.qr.exception.ResourceNotFound;
import ar.com.svc.qr.model.entity.User;
import ar.com.svc.qr.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    Validator validator;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            LOGGER.error("Error: User not found");
            throw new ResourceNotFound();
        }
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getId(),
                user.getCreatedDate());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.error("Error: User not found");
            throw new ResourceNotFound();
        }
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getId(),
                user.getCreatedDate());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        userList.forEach(user -> userDTOList.add(new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getId(),
                user.getCreatedDate())
        ));

        return userDTOList;
    }

    @Override
    public synchronized UserDTO addUser(UserDTO userDTO) {
        if (userRepository.exists(userDTO.getUsername())) {
            LOGGER.error("Error: User already exists");
            throw new ResourceAlreadyExists();
        }

        UserRoles role = UserRoles.getUserRole(userDTO.getRole());
        if (role.equals(UserRoles.NON_ROLE)) {
            LOGGER.error("Error: User role not found");
            throw new NotFoundException("User Role not found");
        }

        User user = userRepository.create(new User(
                userDTO.getUsername(),
                userDTO.getFullName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                role), null);

        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getId(),
                user.getCreatedDate());
    }

    @Override
    public synchronized int updateUser(Long id, UserDTO userDTO) {

        if (!userRepository.exists(userDTO.getUsername())) {
            LOGGER.error("Error: User not found");
            throw new ResourceNotFound();
        }

        UserRoles role = UserRoles.getUserRole(userDTO.getRole());
        if (role.equals(UserRoles.NON_ROLE)) {
            LOGGER.error("Error: User role not found");
            throw new NotFoundException("User Role not found");
        }

        return userRepository.updateById(id, new User(
                userDTO.getFullName(),
                userDTO.getEmail(),
                role
        ));
    }

    @Override
    public int deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserDTO validateUser(UserDTO userDTO) {
        User user = userRepository.validate(new User(
                userDTO.getUsername(),
                userDTO.getPassword()
        ));

        return new UserDTO(user.getId(), user.getRole().getId());
    }
}
