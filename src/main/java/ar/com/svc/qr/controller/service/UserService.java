package ar.com.svc.qr.controller.service;

import ar.com.svc.qr.controller.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    UserDTO addUser(UserDTO user);

    int updateUser(Long id, UserDTO user);

    int deleteUser(Long id);

    UserDTO validateUser(UserDTO user);

}
