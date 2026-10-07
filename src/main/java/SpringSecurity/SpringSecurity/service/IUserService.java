package SpringSecurity.SpringSecurity.service;

import SpringSecurity.SpringSecurity.dto.SaveUserDTO;
import SpringSecurity.SpringSecurity.persistance.entity.security.User;

import java.util.Optional;


public interface IUserService {
    User createOneCustomer(SaveUserDTO saveUserDTO);

    Optional<User> findByUsername(String username);
}
